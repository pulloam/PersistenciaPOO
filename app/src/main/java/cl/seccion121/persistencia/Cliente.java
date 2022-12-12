package cl.seccion121.persistencia;

import java.util.ArrayList;

public class Cliente {
    private String rut;
    private String correo;
    private String tipo;

    //Relaciones de clases
    private ArrayList<Factura> lasFacturas = new ArrayList<>();

    public boolean eliminarFactura(int correlativo){
        for(int x = 0; x < lasFacturas.size(); x = x + 1){
            if(lasFacturas.get(x).getFolio() == correlativo){
                lasFacturas.remove(x);
                return true;
            }
        }
        return false;
    }

    public boolean agregarFactura(Factura f){

        for(int x = 0; x < lasFacturas.size(); x = x + 1){
            if(lasFacturas.get(x).getFolio() == f.getFolio() || f.getNeto() >= 1000000){
                return false;
            }
        }

        lasFacturas.add(f);

        return true;
    }

    public int obtenerTotalVentas(){
        int suma = 0;
        for(int x = 0; x < lasFacturas.size(); x = x + 1){
            suma = suma + lasFacturas.get(x).getNeto();
        }

        return suma;

    }

    public Factura[] obtenerFacturas(){
        Factura[] facturas = new Factura[lasFacturas.size()];
        for(int x = 0; x < lasFacturas.size(); x = x + 1){
            facturas[x] = lasFacturas.get(x);
        }


        return facturas;

    }

    //region Constructores

    public Cliente(){}

    public Cliente(String rut, String correo, String tipo) {
        this.rut = rut;
        this.correo = correo;
        this.tipo = tipo;
    }
    //endregion

    //region Get y Set

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    //endregion
}
