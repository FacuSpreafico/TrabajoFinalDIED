package entidades;

public class Vertice {
    private String etiqueta;
    private int x;
    private int y;

    public Vertice(String etiqueta, int x, int y) {
        this.etiqueta = etiqueta;
        this.x = x;
        this.y = y;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}