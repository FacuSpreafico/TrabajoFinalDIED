package entidades;

import java.sql.Time;

public class Arista {
    private String  nombre;
    private Vertice verticeOrigen;
    private Vertice verticeDestino;
    private Time tiempo;

    
    
    public Arista(Time t,String n,Vertice verticeOrigen, Vertice verticeDestino) {
        this.nombre = n;
        this.verticeOrigen = verticeOrigen;
        this.verticeDestino = verticeDestino;
        this.tiempo = t;
    }

    public Time getTiempo() {
		return tiempo;
	}

	public void setTiempo(Time tiempo) {
		this.tiempo = tiempo;
	}

	public void setVerticeOrigen(Vertice verticeOrigen) {
		this.verticeOrigen = verticeOrigen;
	}

	public void setVerticeDestino(Vertice verticeDestino) {
		this.verticeDestino = verticeDestino;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Vertice getVerticeOrigen() {
        return verticeOrigen;
    }

    public Vertice getVerticeDestino() {
        return verticeDestino;
    }
}