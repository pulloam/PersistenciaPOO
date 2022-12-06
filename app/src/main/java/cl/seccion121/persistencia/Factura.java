package cl.seccion121.persistencia;

public class Factura {
    private int folio;
    private String fecha;
    private int neto;
    private int iva;
    private int total;

    public Factura(int folio, String fecha, int neto) {
        this.folio = folio;
        this.fecha = fecha;
        this.neto = neto;
    }



    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNeto() {
        return neto;
    }

    public void setNeto(int neto) {
        this.neto = neto;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
