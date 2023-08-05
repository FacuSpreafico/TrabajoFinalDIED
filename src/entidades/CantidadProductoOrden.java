package entidades;



public class CantidadProductoOrden {

	
	private int orden;
	private int producto;
	private int cantidad;

	public CantidadProductoOrden() {
		super();
	}

	public CantidadProductoOrden(int orden, int producto, int cantidad) {
		super();
		this.orden = orden;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
}
