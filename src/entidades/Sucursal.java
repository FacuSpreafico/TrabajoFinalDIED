package entidades;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class Sucursal {
    private int idSucursal;
    private String nombre;
    private int capacidad;
    private String estado;
    private Time horarioApertura;
    private Time horarioCierre;
    private HashMap<Producto,Integer> productos = new HashMap<Producto,Integer>();

    public HashMap<Producto, Integer> getProductos() {
		return productos;
	}

	public void setProductos(HashMap<Producto, Integer> productos) {
		this.productos = productos;
	}

	public Sucursal(String nombre, int capacidad, String estado, Time horarioApertura, Time horarioCierre) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
    }

	public Sucursal() {
		
	}

	public int getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Time getHorarioApertura() {
		return horarioApertura;
	}

	public void setHorarioApertura(Time horarioApertura) {
		this.horarioApertura = horarioApertura;
	}

	public Time getHorarioCierre() {
		return horarioCierre;
	}

	public void setHorarioCierre(Time horarioCierre) {
		this.horarioCierre = horarioCierre;
	}

	/*
	public void eliminarProducto(String nombre) {
		for (Producto unProdu: productos) {
			if (unProdu.getNombre().equals(nombre)) {
				productos.remove(unProdu);
			}
		}
	}
	*/
}
