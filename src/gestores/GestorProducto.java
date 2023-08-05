package gestores;

import java.util.ArrayList;



import entidades.Producto;
import entidades.Sucursal;


public class GestorProducto {

	
	public ArrayList<Producto> productos= new ArrayList<>();
	private static GestorProducto _INSTANCE;
	
	public static GestorProducto getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorProducto();
		}
		return _INSTANCE;
	}
	
public Producto buscarProducto (String nombre) {
		
		for (Producto unProdu: productos) {
			if (unProdu.getNombre().equals(nombre)) {
				return unProdu;
			}
		}
		return null;
	}

public ArrayList<Producto> getProductos() {
	return productos;
}

public void setProductos(ArrayList<Producto> productos) {
	this.productos = productos;
}

public void registrarProductol(String nombre, double precio, String descripcion, double peso) {
	
	Producto prod = new Producto();
	prod.setNombre(nombre);
	prod.setPrecio(precio);
	prod.setDescripcion(descripcion);
	prod.setPeso(peso);
	
	productos.add(prod);
}

public Producto buscarProductoXID (int idProducto) {
	for (Producto unProducto: productos) {
		if (unProducto.getIdProducto() == idProducto) {
			return unProducto;
		}
	}
	return null;
}

public String[] getNombresProductos() {
    String[] nombres = new String[productos.size()];
    for (int i = 0; i < productos.size(); i++) {
        nombres[i] = productos.get(i).getNombre();
    }
    return nombres;
}
	
}
