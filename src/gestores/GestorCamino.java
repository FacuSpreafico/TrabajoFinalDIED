package gestores;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.JRootPane;

import entidades.Camino;
import entidades.Sucursal;
import excepciones.CampoCaminoNoIngresadoException;
import excepciones.NoExisteSucursalException;
import excepciones.NombreCaminoExistenteException;
import excepciones.SucursalesIgualesException;
import main.Administrador;

public class GestorCamino {
	
	private static GestorCamino _INSTANCE;
    private ArrayList<Camino> caminos;

    public GestorCamino() {
        caminos = new ArrayList<Camino>();
    }
    
    public static GestorCamino getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorCamino();
		}
		return _INSTANCE;
	}
    
    public ArrayList<Camino> getCaminos() {
		return caminos;
	}

    public void registrarCamino (String nombre, String tiempo, Sucursal inicio, Sucursal fin, String estado, JRootPane rootPane) throws ClassNotFoundException, SQLException, CampoCaminoNoIngresadoException, SucursalesIgualesException, NombreCaminoExistenteException{
    	verificarIngresados(nombre, tiempo, inicio, fin, estado);
    	verificarNombreCamino(nombre);
    	verificarSucursalesDistintas(inicio, fin);
    	
		Camino camino = new Camino();
		camino.setNombre(nombre);
		camino.setTiempoRecorrido(Time.valueOf(tiempo));
		camino.setEstado(estado);
		camino.setInicio(inicio);
        camino.setFin(fin);
		camino.setCapacidad(fin.getCapacidad());
        
        caminos.add(camino);
        Administrador.getInstance().insertarCamino(camino);
        
	}
    
    public void editarCamino(Camino c, String nombre, Time tiempo, Sucursal origen, Sucursal destino) throws ClassNotFoundException, SQLException {
    	
    	c.setNombre(nombre);
    	c.setTiempoRecorrido(tiempo);
    	c.setInicio(origen);
    	c.setFin(destino);
    	
    	Administrador.getInstance().modificarCamino(c);
    	
    }
    
    public void verificarIngresados(String nombre, String tiempo, Sucursal inicio, Sucursal fin, String estado) throws CampoCaminoNoIngresadoException{
    	if(nombre == null || tiempo == "00:00:00" || inicio == null || fin == null || estado == "No seleccionado") {
    		throw(new CampoCaminoNoIngresadoException());
    	}
    }
    
    public void verificarSucursalesDistintas(Sucursal inicio, Sucursal fin) throws SucursalesIgualesException {
    	if(inicio.equals(fin))
    		throw(new SucursalesIgualesException());
    }
    
    public void verificarSucursalExiste() throws NoExisteSucursalException {
    	int cantSucursales = GestorSucursal.getInstance().getSucursales().size();
    	if(cantSucursales == 0)
    		throw(new NoExisteSucursalException());
    }

    public void verificarNombreCamino(String nombre) throws NombreCaminoExistenteException{
    	if(buscarCaminoPorNombre(nombre) != null)
    		throw(new NombreCaminoExistenteException());
    }
public Camino buscarCaminoPorNombre (String nombre) {
		
		for (Camino unCamino: caminos) {
			if (unCamino.getNombre().equals(nombre)) {
				return unCamino;
			}
		}
		return null;
	}
public Camino buscarCaminoXID (int idcamino) {
	
	for (Camino unCamino: caminos) {
		if (unCamino.getIdCamino() == idcamino) {
			return unCamino;
		}
	}
	return null;
}
public String[] getNombresCaminos(String sucursalFin) {
    String[] nombres = new String[caminos.size()];
    for (int i = 0; i < caminos.size(); i++) {
    	if (caminos.get(i).getInicio().getNombre().equals(sucursalFin) || caminos.get(i).getFin().getNombre().equals(sucursalFin)){
        nombres[i] = caminos.get(i).getNombre();
    }
   }
	return nombres;

}
}