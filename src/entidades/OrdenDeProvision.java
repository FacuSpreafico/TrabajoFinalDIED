package entidades;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

public class OrdenDeProvision {
    private int idOrden;
    private Sucursal sucursal;
    private Date fecha;
    private Time horaMaxima;
    private String estado;
    private Camino camino;
    private Sucursal proveedor;
    private HashMap<Integer, Integer> productosOrden = new HashMap<Integer,Integer>();
    
    public Sucursal getProveedor() {
		return proveedor;
	}

	public void setProveedor(Sucursal proveedor) {
		this.proveedor = proveedor;
	}

    public Camino getCamino() {
		return camino;
	}

	public void setCamino(Camino camino) {
		this.camino = camino;
	}

	public OrdenDeProvision() {
    	
    	
    }

	public OrdenDeProvision(Sucursal sucursal, Date fecha,Time tiempoMax, HashMap<Integer, Integer> productos) {
		super();
		this.sucursal = sucursal;
		this.fecha = fecha;
		this.productosOrden.putAll(productos);
		this.horaMaxima= tiempoMax;
		this.estado = "Pendiente";
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Time getHoraMaxima() {
		return horaMaxima;
	}

	public void setHoraMaxima(Time horaMaxima) {
		this.horaMaxima = horaMaxima;
	}

	public HashMap<Integer, Integer> getProductos() {
		return productosOrden;
	}

	public void setProductos(HashMap<Integer, Integer> productos) {
		this.productosOrden = productos;
	}

	public int getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(int idOrden) {
		this.idOrden = idOrden;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
    
    
}
