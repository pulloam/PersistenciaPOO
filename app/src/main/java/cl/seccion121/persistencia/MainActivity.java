package cl.seccion121.persistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etRut, etCorreo;
    private Spinner spnTipo;
    private Button btnGrabar, btnEliminar, btnRetroceder, btnAvanzar;
    private TextView tvPaginacion;

    //Spinner
    private String[] tipoCliente;
    private ArrayAdapter adapterTipo;

    //Clientes
    private ArrayList<Cliente> losClientes;

    private int indiceActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poblar();
        referencias();
        eventos();

        obtenerDatosCliente();
    }



    private void poblar() {
        tipoCliente = new String[] {"Selecione tipo de cliente", "Bueno", "Regular", "Malo"};

        losClientes = new ArrayList<Cliente>();
        losClientes.add(new Cliente("1-9", "aaaa@aaa.cl", "Bueno"));
        losClientes.add(new Cliente("2-0", "bbbb@aaa.cl", "Malo"));
        losClientes.add(new Cliente("3-1", "cccc@aaa.cl", "Regular"));
        losClientes.add(new Cliente("4-2", "eeee@aaa.cl", "Bueno"));
    }

    private void obtenerDatosCliente(){
        if(indiceActual >= 0 && indiceActual < losClientes.size()) {
            Cliente cli = losClientes.get(indiceActual);
            etRut.setText(cli.getRut());
            etCorreo.setText(cli.getCorreo());

            if(cli.getTipo().equals("Bueno")) spnTipo.setSelection(1);

            if(cli.getTipo().equals("Regular")) spnTipo.setSelection(2);

            if(cli.getTipo().equals("Malo")) spnTipo.setSelection(3);

            tvPaginacion.setText((indiceActual + 1) + " de " + losClientes.size());
        }
    }

    private void limpiarPantalla(){
        etRut.setText(""); etCorreo.setText(""); spnTipo.setSelection(0);
        etRut.setError(null); etCorreo.setError(null);
        tvPaginacion.setText("" + losClientes.size());
        indiceActual = -1;
    }

    private void grabarCliente(){
        String rut, correo, tipo = "";
        boolean rutOK = true;

        rut = etRut.getText().toString();
        correo = etCorreo.getText().toString();
        tipo = spnTipo.getSelectedItem().toString();

        for(Cliente c : losClientes){
            if(c.getRut().equals(rut)) {
                rutOK = false;
                break;
            }
        }

        if(rut.isEmpty() || correo.isEmpty() || spnTipo.getSelectedItemPosition() == 0){
            etRut.setError("Tiene errores de validaci??n");
        }else {
            if(rutOK) {
                Cliente cli = new Cliente(rut, correo, tipo);
                losClientes.add(cli);

                grabarBaseDatos(cli);

                tvPaginacion.setText((indiceActual + 1) + " de " + losClientes.size());
                Toast.makeText(MainActivity.this, "Grabado exitosamente", Toast.LENGTH_SHORT).show();
                limpiarPantalla();
            }else{
                etRut.setError("Rut ya est?? ingresado");
            }
        }
    }

    public void grabarBaseDatos(Cliente cli){
        try{
            AdministradorBaseDatos adbd = new AdministradorBaseDatos(this, "BDAplicacion", null, 1);
            SQLiteDatabase miBD = adbd.getWritableDatabase();

            //Forma android
            ContentValues reg = new ContentValues();
            reg.put("rut", cli.getRut());
            reg.put("correo", cli.getCorreo());
            reg.put("edad", 35);

            miBD.insert("clientes", null, reg);

            //Forma cl??sica - nombre dado por Luis Madriaga
           /* String[] parametros = {cli.getRut(), cli.getCorreo(), "34"};
            miBD.execSQL(
                    "insert into clientes (rut, correo, edad) values(?,?,?)"
            , parametros);*/


            miBD.close();
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }

        consultaSQL();

    }

    private void consultaSQL(){
        AdministradorBaseDatos adbd = new AdministradorBaseDatos(this, "BDAplicacion", null, 1);
        SQLiteDatabase miBD = adbd.getWritableDatabase();
        try {
            Cursor c = miBD.rawQuery("Select * from clientes order by rut desc", null);
            if(c.moveToFirst()){
                Log.d("TAG_","Registros recuperados " + c.getCount());
                do{
                    Log.d("TAG_", "Rut " + c.getString(0) +
                            ", correo " + c.getString(1) +
                            ", edad " + c.getInt(2));
                }while(c.moveToNext());
            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }finally {
            miBD.close();
        }
    }


    private void eliminarCliente(){
        if(indiceActual >= 0 && indiceActual < losClientes.size()) {
            losClientes.remove(indiceActual);
            limpiarPantalla();
        }
    }

    private void referencias() {
        etRut = findViewById(R.id.etRut);
        etCorreo = findViewById(R.id.etCorreo);
        spnTipo = findViewById(R.id.spnTipo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRetroceder = findViewById(R.id.btnRetrocede);
        tvPaginacion = findViewById(R.id.tvPag);
        btnAvanzar = findViewById(R.id.btnAvanzar);

        adapterTipo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoCliente);
        spnTipo.setAdapter(adapterTipo);

    }

    private void eventos() {
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabarCliente();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCliente();
            }
        });

        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indiceActual = indiceActual - 1;

                if(indiceActual == -1)
                    indiceActual = losClientes.size() - 1;

                obtenerDatosCliente();
            }
        });

        btnAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indiceActual = indiceActual + 1;

                if(indiceActual == losClientes.size()) {
                    //btnAvanzar.setEnabled(false);
                    //btnAvanzar.setVisibility(View.INVISIBLE);
                    indiceActual = 0;
                }

                obtenerDatosCliente();
            }
        });
    }
}