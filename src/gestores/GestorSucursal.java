package gestores;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import entidades.Sucursal;
import excepciones.NombreSucursalExistenteException;
import main.Administrador;

public class GestorSucursal {

	private ArrayList<Sucursal> sucursales= new ArrayList<>();
    
	private static GestorSucursal _INSTANCE;
	
	
	public static GestorSucursal getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new GestorSucursal();
		}
		return _INSTANCE;
	}
	
	public ArrayList<Sucursal> getSucursales() {
		return sucursales;
	}

	public void setSucursales(ArrayList<Sucursal> sucursales) {
		this.sucursales = sucursales;
	} 
	
	public Sucursal buscarSucursal (String nombre) {
		for (Sucursal unaSucursal: sucursales) {
			if (unaSucursal.getNombre().equals(nombre)) {
				return unaSucursal;
			}
		}
		return null;
	}
	
	public Sucursal buscarSucursalXID (int idSucursal) {
		for (Sucursal unaSucursal: sucursales) {
			if (unaSucursal.getIdSucursal() == idSucursal) {
				return unaSucursal;
			}
		}
		return null;
	}
	
       public int retornaID (String nombre) {
		
		for (Sucursal unaSucursal: sucursales) {
			if (unaSucursal.getNombre().equals(nombre)) {
				return unaSucursal.getIdSucursal();
			}
		}
		return 0;
	}
	
	public void registrarSucursal (String nombre, int capacidad, String estado, Time horarioApertura, Time horarioCierre, JRootPane rootPane) throws NombreSucursalExistenteException, ClassNotFoundException, SQLException{
		this.compararNombreSucursal(nombre);
	
		Sucursal sucursal = new Sucursal();
		sucursal.setNombre(nombre);
		sucursal.setCapacidad(capacidad);
		sucursal.setEstado(estado);
		sucursal.setHorarioApertura(horarioApertura);
        sucursal.setHorarioCierre(horarioCierre);	
        
        sucursales.add(sucursal);
        Administrador.getInstance().insertarSucursal(sucursal); 
	}
	
	public void compararNombreSucursal (String nombre) throws NombreSucursalExistenteException {
		 for (Sucursal sucursalExistente : sucursales) {
		        if (sucursalExistente.getNombre().equalsIgnoreCase(nombre)) {
		        	throw new NombreSucursalExistenteException();
		        }
		    }
	}
	
	

	public List<String> listaSucursales() {
        List<String> res = new ArrayList<String>();
        for(Sucursal s: sucursales)
            res.add(s.getNombre());
        return res;
    }
	
	public String[] getNombresSucursales() {
	    int contador = 0;
	    for (int i = 0; i < sucursales.size(); i++) {
	        if (sucursales.get(i).getEstado().equals("Operativa")) {
	            contador++;
	        }
	    }
	    
	    String[] nombres = new String[contador];
	    int indiceNombres = 0;
	    
	    for (int i = 0; i < sucursales.size(); i++) {
	        if (sucursales.get(i).getEstado().equals("Operativa")) {
	            nombres[indiceNombres] = sucursales.get(i).getNombre();
	            indiceNombres++;
	        }
	    }
	    return nombres;
	}
}
	

