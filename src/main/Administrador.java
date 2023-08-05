package main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JTextField;

import entidades.Camino;
import entidades.CantidadProductoOrden;
import entidades.OrdenDeProvision;
import entidades.Producto;
import entidades.Stock;
import entidades.Sucursal;
import gestores.GestorCamino;
import gestores.GestorCantidadProductoOrden;
import gestores.GestorOrdenDeProvision;
import gestores.GestorProducto;
import gestores.GestorStock;
import gestores.GestorSucursal;

public class Administrador {

	private static Administrador _INSTANCE;
	
	public static Administrador getInstance() throws ClassNotFoundException, SQLException {
		if (_INSTANCE == null) {
			_INSTANCE = new Administrador();
		}
		return _INSTANCE;
	}
	
	public Administrador() throws ClassNotFoundException, SQLException {
	ConexionDB.getInstance().getConn();
	}
	
	
	public void insertarSucursal (Sucursal unaSucursal) throws SQLException {
	Statement st = ConexionDB.getInstance().getConn().createStatement();
	st.executeUpdate("INSERT INTO sucursal (nombre,capacidad,estado,horarioApertura,horarioCierre) VALUES ('"+unaSucursal.getNombre()+"',"+unaSucursal.getCapacidad()+",'"+unaSucursal.getEstado()+"','"+unaSucursal.getHorarioApertura().toString()+"','"+unaSucursal.getHorarioCierre().toString()+"')");
	st.close();
	}
	
	public void insertarOrden (OrdenDeProvision orden) throws SQLException {
		Statement st = ConexionDB.getInstance().getConn().createStatement();    
	    String sql = "INSERT INTO ordendeprovision (fechaemision, proveedor, destino, horamaxima,estado) VALUES ('"
	            + orden.getFecha() + "', default, " + orden.getSucursal().getIdSucursal() + ", '"
	            + orden.getHoraMaxima() + "','"+orden.getEstado()+"')";
	    
	    st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

	    ResultSet rs = st.getGeneratedKeys();
	    if (rs.next()) {
	        int idOrdenGenerado = rs.getInt(1);
	        orden.setIdOrden(idOrdenGenerado);
	    }

	    rs.close();
	    st.close();
		}
	
	public void insertarCantidadProductoOrden (CantidadProductoOrden CPOrden) throws SQLException {
		Statement st = ConexionDB.getInstance().getConn().createStatement();
		st.executeUpdate("INSERT INTO cantidadproductoorden (idorden, idproducto, cantidad) VALUES ("+CPOrden.getOrden()+","+CPOrden.getProducto()+","+CPOrden.getCantidad()+")");
		st.close();
		}
	
	public void insertarCamino(Camino unCamino) throws SQLException {
        Statement st = ConexionDB.getInstance().getConn().createStatement();
        st.executeUpdate("INSERT INTO camino (nombre,sucursalInicio,sucursalFin,estado,tiempo,capacidad) VALUES ('"
                + unCamino.getNombre() + "'," + unCamino.getInicio().getIdSucursal() + ","
                + unCamino.getFin().getIdSucursal() + ",'" + unCamino.getEstado() + "','"
                + unCamino.getTiempoRecorrido() + "'," + unCamino.getCapacidad() + ")");
        st.close();
    }
	
	public void modificarCamino(Camino unCamino) throws SQLException {
        PreparedStatement ps = null;

        String updateSucursal = "UPDATE camino SET nombre = ?,sucursalinicio =?,sucursalfin =?,estado=?,tiempo=?,capacidad=? WHERE idCamino= ?";
        ps = ConexionDB.getInstance().getConn().prepareStatement(updateSucursal);
        ps.setString(1,unCamino.getNombre());
        ps.setInt(2,unCamino.getInicio().getIdSucursal());
        ps.setInt(3, unCamino.getFin().getIdSucursal());
        ps.setString(4, unCamino.getEstado());
        ps.setTime(5, unCamino.getTiempoRecorrido());
        ps.setInt(6, unCamino.getFin().getCapacidad());
        ps.setInt(7, unCamino.getIdCamino());
        ps.executeUpdate();
        ps.close();
    }
	
	
	

	public Integer contarDatosSucursal () throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM sucursal"); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}
	
	public Integer contarDatosOrdenes () throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM ordendeprovision"); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}
	
	public String[][] cargarDatosOrdenes (Integer contador) throws SQLException{
		 Statement st = ConexionDB.getInstance().getConn().createStatement(); //ahora vamos a  hacer lo mismo solo que esta vez no obtendremos el numero de filas en la tabla
        ResultSet rs = st.executeQuery("SELECT * FROM ordendeprovision"); //ahora obtendremos los datos de la tabla para mostrarlos en el jtable
        String[][] M_datos;
        int cont = 0; //el contador nos ayudara para movernos en las filas de la matriz mientras que los numeros fijos (0,1,2,3) nos moveran por las 4 columnas que seran el id, nombre, etc
        M_datos = new String[contador][6]; //definimos el tamaño de la matriz 
        while (rs.next()) { //el while nos ayudara a recorrer los datos obtenidos en la consulta anterior y asignarlos a la matriz  
            M_datos[cont][0] = rs.getString("idOrden");    //agregamos los datos a la table
            M_datos[cont][1] = rs.getString("fechaEmision");
            int idSucursal = Integer.parseInt(rs.getString("destino"));
            M_datos[cont][2] = GestorSucursal.getInstance().buscarSucursalXID(idSucursal).getNombre();
            M_datos[cont][3] = rs.getString("horaMaxima");
            M_datos[cont][4] = rs.getString("estado");
            if (rs.getString("idcamino")!=null) {
            int idCamino = Integer.parseInt(rs.getString("idcamino"));
            M_datos[cont][5] = GestorCamino.getInstance().buscarCaminoXID(idCamino).getNombre();
            }
            
            cont = cont + 1; //avanzamos una posicion del contador para que pase a la siguiente fila
        }
		return M_datos;
	}
	
	public String[][] cargarDatosSucursal (Integer contador) throws SQLException{
		 Statement st = ConexionDB.getInstance().getConn().createStatement(); //ahora vamos a  hacer lo mismo solo que esta vez no obtendremos el numero de filas en la tabla
         ResultSet rs = st.executeQuery("SELECT * FROM sucursal"); //ahora obtendremos los datos de la tabla para mostrarlos en el jtable
         String[][] M_datos;
         int cont = 0; //el contador nos ayudara para movernos en las filas de la matriz mientras que los numeros fijos (0,1,2,3) nos moveran por las 4 columnas que seran el id, nombre, etc
         M_datos = new String[contador][6]; //definimos el tamaño de la matriz 
         while (rs.next()) { //el while nos ayudara a recorrer los datos obtenidos en la consulta anterior y asignarlos a la matriz  
             M_datos[cont][0] = rs.getString("idSucursal");    //agregamos los datos a la table
             M_datos[cont][1] = rs.getString("nombre");
             M_datos[cont][2] = rs.getString("capacidad");
             M_datos[cont][3] = rs.getString("estado");
             M_datos[cont][4] = rs.getString("horarioApertura");
             M_datos[cont][5] = rs.getString("horarioCierre");
             cont = cont + 1; //avanzamos una posicion del contador para que pase a la siguiente fila
         }
		return M_datos;
	}
	
	public String[][] contarDatosQuerySucursal (JTextField textField) throws SQLException {
		int valor = 0;
        int cont = 0;
        String M_datos[][];
        String aux = "" + textField.getText();
	
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); 
        ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM sucursal WHERE LOWER(nombre) LIKE'" + aux.toLowerCase() + "%'");
        if (rs.next()) {
            valor = rs.getInt(1); 
        }
        
            M_datos = new String[valor][6];
            rs = st_cont.executeQuery("SELECT * FROM sucursal WHERE LOWER(nombre) LIKE'" + aux.toLowerCase() + "%'"); 
            while (rs.next()) {
            	 M_datos[cont][0] = rs.getString("idSucursal");
                 M_datos[cont][1] = rs.getString("nombre");
                 M_datos[cont][2] = rs.getString("capacidad");
                 M_datos[cont][3] = rs.getString("estado");
                 M_datos[cont][4] = rs.getString("horarioApertura");
                 M_datos[cont][5] = rs.getString("horarioCierre");
                cont = cont + 1;
            }
            return M_datos;
	}
	
	
	public String[][] cargarDatosProducto (Integer contador) throws SQLException{
		Statement st = ConexionDB.getInstance().getConn().createStatement(); 
        ResultSet rs = st.executeQuery("SELECT * FROM producto"); 
        String[][] M_datos;
        int cont = 0;
        M_datos = new String[contador][6]; 
        while (rs.next()) { 
            M_datos[cont][0] = rs.getString("idProducto");   
            M_datos[cont][1] = rs.getString("precio");
            M_datos[cont][2] = rs.getString("nombre");
            M_datos[cont][3] = rs.getString("descripcion");
            M_datos[cont][4] = rs.getString("peso");
            cont = cont + 1; 
        }
		return M_datos;
	}
	
	public Integer contarDatosProducto () throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM producto"); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}

	
	public Integer contarDatosProductos (Sucursal sucu) throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(stock.idproducto) FROM stock WHERE idSucursal ="+ sucu.getIdSucursal()); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}
	
	public String[][] cargarDatosProductos (Integer contador, Sucursal sucu) throws SQLException{
		
		try {
		Statement st = ConexionDB.getInstance().getConn().createStatement(); //ahora vamos a  hacer lo mismo solo que esta vez no obtendremos el numero de filas en la tabla
        ResultSet rs = st.executeQuery("SELECT p.idproducto, p.nombre, p.peso, p.precio, s.stock FROM producto as p  JOIN stock as s ON (s.idproducto = p.idproducto) WHERE idSucursal =" + sucu.getIdSucursal()); //ahora obtendremos los datos de la tabla para mostrarlos en el jtable
        String[][] M_datos;
        int cont = 0; //el contador nos ayudara para movernos en las filas de la matriz mientras que los numeros fijos (0,1,2,3) nos moveran por las 4 columnas que seran el id, nombre, etc
        M_datos = new String[contador][6]; //definimos el tamaño de la matriz 
        while (rs.next()) { //el while nos ayudara a recorrer los datos obtenidos en la consulta anterior y asignarlos a la matriz  
            M_datos[cont][0] = rs.getString("idProducto");    //agregamos los datos a la table
            M_datos[cont][1] = rs.getString("nombre");
            M_datos[cont][2] = rs.getString("peso");
            M_datos[cont][3] = rs.getString("precio");
            M_datos[cont][4] = rs.getString("stock");
            cont = cont + 1; //avanzamos una posicion del contador para que pase a la siguiente fila
        }
		return M_datos;
		}
		catch(SQLException e) {
			return null;	
		}
		
	}
	
	public String[][] contarDatosQueryProductos(JTextField textField) throws SQLException {
		int valor = 0;
        int cont = 0;
        String M_datos[][];
        String aux = "" + textField.getText();
	
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); 
        ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM producto WHERE LOWER(nombre) LIKE'" + aux.toLowerCase() + "%'");
        if (rs.next()) {
            valor = rs.getInt(1); 
        }
        
            M_datos = new String[valor][5];
            rs = st_cont.executeQuery("SELECT * FROM producto WHERE LOWER(nombre) LIKE'" + aux.toLowerCase() + "%'"); 
            while (rs.next()) {
                M_datos[cont][0] = rs.getString("idProducto");  
                M_datos[cont][1] = rs.getString("precio");
                M_datos[cont][2] = rs.getString("nombre");
                M_datos[cont][3] = rs.getString("descripcion");
                M_datos[cont][4] = rs.getString("peso");
                cont = cont + 1;
            }
            return M_datos;
	}

	
	public Integer contarDatosCamino () throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM camino"); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}
	
	public String[][] cargarDatosCamino  (Integer contador) throws SQLException{
		 Statement st = ConexionDB.getInstance().getConn().createStatement(); //ahora vamos a  hacer lo mismo solo que esta vez no obtendremos el numero de filas en la tabla
         ResultSet rs = st.executeQuery("SELECT * FROM camino"); //ahora obtendremos los datos de la tabla para mostrarlos en el jtable
         String[][] M_datos;
         int cont = 0; //el contador nos ayudara para movernos en las filas de la matriz mientras que los numeros fijos (0,1,2,3) nos moveran por las 4 columnas que seran el id, nombre, etc
         M_datos = new String[contador][6]; //definimos el tamaño de la matriz 
         while (rs.next()) { //el while nos ayudara a recorrer los datos obtenidos en la consulta anterior y asignarlos a la matriz  
            M_datos[cont][0] = rs.getString("idCamino");    //agregamos los datos a la table
            M_datos[cont][1] = rs.getString("nombre");
            M_datos[cont][2] = rs.getString("capacidad");
            M_datos[cont][3] = rs.getString("estado");
            int idSucursalInicio = Integer.parseInt(rs.getString("sucursalinicio"));
            M_datos[cont][4] = GestorSucursal.getInstance().buscarSucursalXID(idSucursalInicio).getNombre();
            int idSucursalFin = Integer.parseInt(rs.getString("sucursalfin"));
            M_datos[cont][5] = GestorSucursal.getInstance().buscarSucursalXID(idSucursalFin).getNombre();
           
             cont = cont + 1; //avanzamos una posicion del contador para que pase a la siguiente fila
         }
		return M_datos;
	}
	
	public String[][] contarDatosQueryCamino  (JTextField textField) throws SQLException {
		int valor = 0;
        int cont = 0;
        String M_datos[][];
        String aux = "" + textField.getText();
	
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //hacemos lo mismo que con el metodo mostrar, buscamos el numero de filas dela tabla
        ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM camino WHERE nombre LIKE'" + textField.getText() + "%'");//solo que esta ves usamos like
        if (rs.next()) {// like nos ayudara a buscar nombres que tengan similitudes con lo que estamos escribiendo en el texfield
            valor = rs.getInt(1); //una vez que obtenimos el numero de filas continuamos a sacar  el valor que buscamos
        }
       
            M_datos = new String[valor][6];
            rs = st_cont.executeQuery("SELECT * FROM camino WHERE nombre LIKE'" + textField.getText() + "%'"); //aqui es donde buscaremos a a la persona en especifico o las personas
            while (rs.next()) {
            	 M_datos[cont][0] = rs.getString("idCamino");    //agregamos los datos a la table
                 M_datos[cont][1] = rs.getString("nombre");
                 M_datos[cont][2] = rs.getString("capacidad");
                 M_datos[cont][3] = rs.getString("estado");
                 M_datos[cont][4] = rs.getString("sucursalinicio");
                 M_datos[cont][5] = rs.getString("sucursalfin");
                 cont = cont + 1;
            }
            return M_datos;
	}
	
	public String[][] contarDatosQueryProducto  (JTextField textField, Sucursal sucu) throws SQLException {
		int valor = 0;
        int cont = 0;
        String M_datos[][];
        String aux = "" + textField.getText().toString();
    
  
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); 
        ResultSet rs = st_cont.executeQuery("SELECT COUNT(*) FROM producto as p JOIN stock as s ON (s.idproducto = p.idproducto) WHERE LOWER(p.nombre) LIKE'" + aux.toLowerCase() + "%'");
        if (rs.next()) {
            valor = rs.getInt(1); 
        }
        String query = "SELECT s.idProducto, p.nombre, p.peso, p.precio, s.stock "
                + "FROM producto as p "
                + "JOIN stock as s ON (s.idproducto = p.idproducto) "
                + "JOIN sucursal as sucu ON (sucu.idSucursal = s.idSucursal) "
                + "WHERE sucu.nombre = '" + sucu.getNombre().toString() + "' "
                + "AND LOWER(p.nombre) LIKE '" + aux.toLowerCase() + "%'";
            
          M_datos = new String[valor][6];
          rs = st_cont.executeQuery(query);
            
            while (rs.next()) {
            	 M_datos[cont][0] = rs.getString("idProducto");   
                 M_datos[cont][1] = rs.getString("nombre");
                 M_datos[cont][2] = rs.getString("peso");
                 M_datos[cont][3] = rs.getString("precio");
                 M_datos[cont][4] = rs.getString("stock");
                 cont = cont + 1;
            }
            rs.close();
            return M_datos;
	}
	
	
	public String[][] cargarDatosOrden (Integer contador) throws SQLException{
		Statement st = ConexionDB.getInstance().getConn().createStatement(); 
        ResultSet rs = st.executeQuery("SELECT * FROM orden"); 
        String[][] M_datos;
        int cont = 0;
        M_datos = new String[contador][6]; 
        while (rs.next()) { 
            M_datos[cont][0] = rs.getString("idProducto");   
            M_datos[cont][1] = rs.getString("precio");
            M_datos[cont][2] = rs.getString("nombre");
            M_datos[cont][3] = rs.getString("descripcion");
            M_datos[cont][4] = rs.getString("peso");
            cont = cont + 1; 
        }
		return M_datos;
	}
	
	public Integer contarDatosOrden () throws SQLException {
		Integer contador = 0;
		Statement st_cont = ConexionDB.getInstance().getConn().createStatement(); //el statement nos ayuda a procesar una sentencia sql 
        ResultSet rs_cont = st_cont.executeQuery("SELECT COUNT(*) FROM producto"); // asignamos los datos obtenidos de la consulta al result set
         if (rs_cont.next()) {
            contador = rs_cont.getInt(1);
	}
         return contador;
    
	}

	
	
	
	
	
	
	public void instanciarDatos () throws SQLException {
		String consultaSucursales = "SELECT * FROM sucursal";
		String consultaProductos = "SELECT * FROM producto";
		String consultaStock = "SELECT * FROM stock";
		String consultaCantidadProductoOrden = "SELECT * FROM cantidadproductoorden";
		String consultaOrdenDeProvision = "SELECT * FROM ordendeprovision";
		String consultaCaminos = "SELECT * FROM camino";
		Statement statement = ConexionDB.getInstance().getConn().createStatement();
        ResultSet resultSetSucursales = statement.executeQuery(consultaSucursales);
		
        while (resultSetSucursales.next()) {
        	Sucursal sucursal = new Sucursal();
        	sucursal.setIdSucursal(resultSetSucursales.getInt(1)); 
        	sucursal.setNombre(resultSetSucursales.getString(2));
        	int capacidad = Integer.parseInt(resultSetSucursales.getString(3));
        	sucursal.setCapacidad(capacidad);
        	sucursal.setEstado(resultSetSucursales.getString(4));
        	Time horarioAbre = resultSetSucursales.getTime(5);
        	Time horarioCierra = resultSetSucursales.getTime(6);
        	sucursal.setHorarioApertura(horarioAbre);
        	sucursal.setHorarioCierre(horarioCierra);
            GestorSucursal.getInstance().getSucursales().add(sucursal);
        }
    
        
        
        ResultSet resultSetProductos = statement.executeQuery(consultaProductos);
        
        while (resultSetProductos.next()) {
        	Producto producto = new Producto();
        	producto.setIdProducto(resultSetProductos.getInt(1));
        	producto.setPrecio(resultSetProductos.getInt(2));
        	producto.setNombre(resultSetProductos.getString(3));
        	producto.setDescripcion(resultSetProductos.getString(4));
        	producto.setPeso(resultSetProductos.getDouble(5));
        	GestorProducto.getInstance().getProductos().add(producto);
        }
        
       ResultSet resultSetStock = statement.executeQuery(consultaStock);
        
        while (resultSetStock.next()) {
        	Stock stock = new Stock();
        	Sucursal sucursal = new Sucursal();
        	Producto producto = new Producto();
        	sucursal = GestorSucursal.getInstance().buscarSucursalXID(resultSetStock.getInt(1));
        	producto = GestorProducto.getInstance().buscarProductoXID(resultSetStock.getInt(2));
        	stock.setSucu(sucursal);
        	stock.setProd(producto);
        	stock.setStock(resultSetStock.getInt(3));
        	GestorStock.getInstance().getStocks().add(stock);
        }
        
        
        ResultSet resultSetOrdenProvision = statement.executeQuery(consultaOrdenDeProvision);
        
        while (resultSetOrdenProvision.next()) {
        	OrdenDeProvision orden = new OrdenDeProvision();
        	orden.setIdOrden(resultSetOrdenProvision.getInt(1));
        	orden.setFecha(resultSetOrdenProvision.getDate(2));
        	orden.setSucursal(GestorSucursal.getInstance().buscarSucursalXID(resultSetOrdenProvision.getInt(4)));
        	orden.setHoraMaxima(resultSetOrdenProvision.getTime(5));
        	orden.setEstado(resultSetOrdenProvision.getString(6));
        	if (resultSetOrdenProvision.getString(7) != null) {
        	int idCamino = Integer.parseInt(resultSetOrdenProvision.getString(7));
        	orden.setCamino(GestorCamino.getInstance().buscarCaminoXID(idCamino));
        	}
        	GestorOrdenDeProvision.getInstance().getOrdenes().add(orden);
        }
        
        ResultSet resultSetPO = statement.executeQuery(consultaCantidadProductoOrden);
        
        while (resultSetPO.next()) {
        	CantidadProductoOrden CPO = new CantidadProductoOrden();
        	CPO.setOrden(resultSetPO.getInt(1));
        	CPO.setProducto(resultSetPO.getInt(2));
        	CPO.setCantidad(resultSetPO.getInt(3));
        	GestorCantidadProductoOrden.getInstance().getCantidades().add(CPO);
        	GestorOrdenDeProvision.getInstance().llenarListaProductos(resultSetPO.getInt(1),resultSetPO.getInt(2),resultSetPO.getInt(3));
        }
        
        ResultSet resultSet = statement.executeQuery(consultaCaminos);
        while (resultSet.next()) {
            Camino camino = new Camino();
            camino.setIdCamino(resultSet.getInt(1)); 
            camino.setNombre(resultSet.getString(2));
            camino.setInicio(GestorSucursal.getInstance().buscarSucursalXID(resultSet.getInt(3)));
            camino.setFin(GestorSucursal.getInstance().buscarSucursalXID(resultSet.getInt(4)));
            camino.setEstado(resultSet.getString(5));
            camino.setTiempoRecorrido(resultSet.getTime(6));
            camino.setCapacidad(resultSet.getInt(7));

            GestorCamino.getInstance().getCaminos().add(camino);
        }
        
        statement.close();
	}
	public void reemplazarDatosSucursal (Sucursal unaSucursal) throws SQLException {
		PreparedStatement ps = null;

			int idSucursal = unaSucursal.getIdSucursal();
			String nombre = unaSucursal.getNombre();
			int capacidad = unaSucursal.getCapacidad();
			String estado = unaSucursal.getEstado();
			Time horarioApertura = unaSucursal.getHorarioApertura();
			Time horarioCierre = unaSucursal.getHorarioCierre();
			String updateSucursal = "UPDATE sucursal SET nombre = ?,capacidad =?,estado =?,horarioApertura=?,horarioCierre=? WHERE idSucursal= ?";
			ps = ConexionDB.getInstance().getConn().prepareStatement(updateSucursal);
	        ps.setString(1,nombre);
	        ps.setInt(2,capacidad);
	        ps.setString(3, estado);
	        ps.setTime(4, horarioApertura);
	        ps.setTime(5, horarioCierre);
	        ps.setInt(6, idSucursal);
	        ps.executeUpdate();
		
		}
	
	public void reemplazarDatosProducto (Double precio, String nombre, String descripcion, Double peso, int idProducto) throws SQLException {
		PreparedStatement ps = null;

			String updateProducto = "UPDATE producto SET precio= ?,nombre=?,descripcion=?, peso=? WHERE idproducto= ?";
			ps = ConexionDB.getInstance().getConn().prepareStatement(updateProducto);
	        ps.setDouble(1,precio);
	        ps.setString(2,nombre);
	        ps.setString(3, descripcion);
	        ps.setDouble(4, peso);
	        ps.setInt(5,idProducto);
	        ps.executeUpdate();
	      
		}
	
	    
	public void eliminarDatosSucursal (Sucursal unaSucursal) throws SQLException {
		PreparedStatement ps = null;
	    String delete = "DELETE FROM sucursal WHERE nombre = ?";
	    ps = ConexionDB.getInstance().getConn().prepareStatement(delete);
	    ps.setString(1, unaSucursal.getNombre());
	    ps.executeUpdate();
	    ps.close();
	}
	
	public void cambiarStock (Stock stock) throws SQLException {
		PreparedStatement ps = null;
		String updateStock = "UPDATE stock SET stock = ? WHERE idSucursal= ? AND idProducto = ?";
		ps = ConexionDB.getInstance().getConn().prepareStatement(updateStock);
		ps.setInt(1, stock.getStock());
		ps.setInt(2, stock.getSucu().getIdSucursal());
		ps.setInt(3, stock.getProd().getIdProducto());
		ps.executeUpdate();
		ps.close();
	}
	
	public void registrarProducto (Producto producto) throws SQLException {
		Statement st = ConexionDB.getInstance().getConn().createStatement();
		String registrarProd = "INSERT INTO producto VALUES (default,"+producto.getPrecio()+", '"+producto.getNombre()+"','"+producto.getDescripcion()+"',"+producto.getPeso()+")";
	
	    st.executeUpdate(registrarProd, Statement.RETURN_GENERATED_KEYS);

	    ResultSet rs = st.getGeneratedKeys();
	    if (rs.next()) {
	        int idProd = rs.getInt(1);
	        producto.setIdProducto(idProd);
	    }
	    st.close();
	}
	
	public void ingresarStockNuevo (int idSucursal, int idProducto, int stock) throws SQLException {
		PreparedStatement ps = null;
		String ingresarStock = "INSERT INTO stock VALUES (?,?,?)";
		ps = ConexionDB.getInstance().getConn().prepareStatement(ingresarStock);
		ps.setInt(1, idSucursal);
		ps.setInt(2, idProducto);
		ps.setInt(3, stock);
		ps.executeUpdate();
		ps.close();
	}
	
	public void eliminarStock (Stock stock) throws SQLException {
		PreparedStatement ps = null;
	    String delete = "DELETE FROM stock WHERE idSucursal = ? AND idProducto = ?";
	    ps = ConexionDB.getInstance().getConn().prepareStatement(delete);
	    ps.setInt(1, stock.getSucu().getIdSucursal());
	    ps.setInt(2, stock.getProd().getIdProducto());
	    ps.executeUpdate();
	    ps.close();
	}
	
	public void eliminarDatosCamino (Camino unCamino) throws SQLException {
        PreparedStatement ps = null;
        String delete = "DELETE FROM camino WHERE nombre = ?";
        ps = ConexionDB.getInstance().getConn().prepareStatement(delete);
        ps.setString(1, unCamino.getNombre());
        ps.executeUpdate();
        ps.close();
    }
	
	public void actualizarOrden (Sucursal unaSucursal, Camino unCamino, String estado, int idOrden) throws SQLException {
        PreparedStatement ps = null;
        String update = "UPDATE ordendeprovision SET proveedor = ?, idcamino= ?, estado = ? WHERE idOrden = ?";
        ps = ConexionDB.getInstance().getConn().prepareStatement(update);
        ps.setInt(1, unaSucursal.getIdSucursal());
        ps.setInt(2, unCamino.getIdCamino());
        ps.setString(3, estado);
        ps.setInt(4, idOrden);
        ps.executeUpdate();
        ps.close();
    }
	
	
	}

	
	
	


