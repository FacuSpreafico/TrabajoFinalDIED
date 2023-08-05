package entidades;


import java.sql.Time;

public class Camino {
    private int idCamino;
    private String nombre;
    private Time tiempoRecorrido;
    private Sucursal Inicio;
    private Sucursal Fin;
    private String estado;
    private int capacidad;

    public Camino() {

    }

    public int getIdCamino() {
        return idCamino;
    }

    public String getNombre() {
        return nombre;
    }

    public Time getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public Sucursal getInicio() {
        return Inicio;
    }

    public Sucursal getFin() {
        return Fin;
    }

    public String getEstado() {
        return estado;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setIdCamino(int idCamino) {
        this.idCamino = idCamino;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempoRecorrido(Time tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public void setInicio(Sucursal inicio) {
        Inicio = inicio;
    }

    public void setFin(Sucursal fin) {
        Fin = fin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }






}