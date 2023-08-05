package gestores;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import entidades.Camino;
import entidades.CantidadProductoOrden;
import entidades.OrdenDeProvision;
import entidades.Producto;
import entidades.Sucursal;
import main.Administrador;

public class GestorOrdenDeProvision {
	
	
	private ArrayList<OrdenDeProvision> ordenes= new ArrayList<OrdenDeProvision>();
	private static GestorOrdenDeProvision _INSTANCE;
	
	public static GestorOrdenDeProvision getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorOrdenDeProvision();
		}
		return _INSTANCE;
	}

	
	public GestorOrdenDeProvision() {
		super();
	}




	public OrdenDeProvision CrearOrdenDeProvision(Sucursal sucursal, Date fecha,Time tiempoMax, HashMap<Integer, Integer> lista) throws ClassNotFoundException, SQLException {
		OrdenDeProvision orden = new OrdenDeProvision(sucursal,fecha,tiempoMax,lista);
		ordenes.add(orden);
		Administrador.getInstance().insertarOrden(orden);
		return orden;
	}


	public ArrayList<OrdenDeProvision> getOrdenes() {
		return ordenes;
	}


	public void setOrdenes(ArrayList<OrdenDeProvision> ordenes) {
		this.ordenes = ordenes;
	}
	
	public OrdenDeProvision buscarOrdenXID (int idOrden) {
			for (OrdenDeProvision unaOrden: ordenes) {
				if (unaOrden.getIdOrden() == idOrden) {
					return unaOrden;
				}
			}
			return null;
	}
		
	public void llenarListaProductos(int idOrden, int idProducto, int cantidad) {
		buscarOrdenXID(idOrden).getProductos().put(idProducto, cantidad);
	}
	
	public void actualizarOrden (int idOrden, String camino) throws ClassNotFoundException, SQLException {
		OrdenDeProvision orden = buscarOrdenXID(idOrden);
		Camino cami = GestorCamino.getInstance().buscarCaminoPorNombre(camino);
		Sucursal sucu;
		
		if (cami.getInicio().getNombre().equals(orden.getSucursal().getNombre())){
			sucu = cami.getFin();
		}
		else {
			sucu=cami.getInicio();
		}
		orden.setProveedor(sucu);
		orden.setEstado("EN CURSO");
        orden.setCamino(cami);	
    
        Administrador.getInstance().actualizarOrden(sucu,cami,"EN CURSO", orden.getIdOrden());
	}
		
		
	}


