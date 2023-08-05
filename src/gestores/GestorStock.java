package gestores;

import java.sql.SQLException;
import java.util.ArrayList;

import entidades.Producto;
import entidades.Stock;
import entidades.Sucursal;
import main.Administrador;

public class GestorStock {

	private ArrayList<Stock> stocks= new ArrayList<Stock>();
	private static GestorStock _INSTANCE;
	
	public static GestorStock getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorStock();
		}
		return _INSTANCE;
	}

	public GestorStock() {
		super();
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	
	public void editarStock(String nombreSucu, String nombreProd,Integer nuevoStock) throws ClassNotFoundException, SQLException {
		for(Stock unStock: stocks) {
			if(unStock.getProd().getNombre().equals(nombreProd) && unStock.getSucu().getNombre().equals(nombreSucu) ) {
				unStock.setStock(nuevoStock);
				Administrador.getInstance().cambiarStock(unStock);
			}
		}
	}
	
	public void insertarStock(Sucursal sucursal, Producto producto, int stock) throws ClassNotFoundException, SQLException {
		Stock st = new Stock();
	    st.setSucu(sucursal);
	    st.setProd(producto);
	    st.setStock(stock);
		Administrador.getInstance().ingresarStockNuevo(sucursal.getIdSucursal(), producto.getIdProducto(), stock);
	    stocks.add(st);
	}
	
	public void eliminarStock(int idSucursal, int idProducto, int stock) throws ClassNotFoundException, SQLException {
		Stock st = new Stock();
	    st.setProd(GestorProducto.getInstance().buscarProductoXID(idProducto));
	    st.setSucu(GestorSucursal.getInstance().buscarSucursalXID(idSucursal));
	    st.setStock(stock);
		Administrador.getInstance().eliminarStock(st);
	    stocks.remove(st);
	}
	
	
}