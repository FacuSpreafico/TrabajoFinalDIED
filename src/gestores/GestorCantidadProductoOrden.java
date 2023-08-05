package gestores;

import java.sql.SQLException;
import java.util.ArrayList;

import entidades.CantidadProductoOrden;
import entidades.OrdenDeProvision;
import main.Administrador;


public class GestorCantidadProductoOrden {
	
	
	private ArrayList<CantidadProductoOrden> cantidades= new ArrayList<CantidadProductoOrden>();
    private static GestorCantidadProductoOrden _INSTANCE;
	
	public static GestorCantidadProductoOrden getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorCantidadProductoOrden();
		}
		return _INSTANCE;
	}

	public GestorCantidadProductoOrden() {
		super();
	}
	
	public void registrar(OrdenDeProvision orden, int idProducto, int cantidad) throws ClassNotFoundException, SQLException {
		CantidadProductoOrden CPOrden = new CantidadProductoOrden(orden.getIdOrden(),idProducto,cantidad);
		cantidades.add(CPOrden);
		Administrador.getInstance().insertarCantidadProductoOrden(CPOrden);
	}

	public ArrayList<CantidadProductoOrden> getCantidades() {
		return cantidades;
	}

	public void setCantidades(ArrayList<CantidadProductoOrden> cantidades) {
		this.cantidades = cantidades;
	}


}
