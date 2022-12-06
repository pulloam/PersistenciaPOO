package cl.seccion121.persistencia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cliente cli = new Cliente();
        cli.setRut("1-9");

        if(cli.agregarFactura(new Factura(1234,"01/01/2000",1000))){
            Log.d("TAG_", "Factura Agregada");
        }

        if(cli.agregarFactura(new Factura(1234,"01/01/2000",1000))){
            Log.d("TAG_", "Factura Agregada");
        }else{
            Log.e("TAG_", "Factura error");
        }

        for(int x = 1; x <= 30; x = x + 1){
            cli.agregarFactura(new Factura(5000 + x,"01/01/2000",1000));

            if(cli.agregarFactura(new Factura(1234,"01/01/2000",1000000))){
                Log.d("TAG_", "Factura Agregada");
            }else{
                Log.e("TAG_", "Factura no se pudo agregar, tiene errores en la tabla");
            }
        }

        Factura[] lasFacturasCli = cli.obtenerFacturas();

        cli.eliminarFactura(5020);

        Log.d("TAG_", "Suma de venta " + cli.obtenerTotalVentas());

        Log.d("TAG_", "");

    }
}