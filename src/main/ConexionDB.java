package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

	private static ConexionDB _INSTANCE;
	private Connection conn;
	
	public static ConexionDB getInstance() {
		if (_INSTANCE == null) {
			_INSTANCE = new ConexionDB();
		}
		return _INSTANCE;
	}

	
	public Connection getConn() throws SQLException {
		if (conn==null || conn.isClosed()) {
			this.crearConexion();
		}
		return this.conn;
	}
	
	private void crearConexion() {
		String url = "jdbc:postgresql://localhost:5432/TPDied";
		String usuario = "postgres";
		String clave = "root";
				
		try {
			Class.forName("org.postgresql.Driver");
			this.conn = DriverManager.getConnection(url,usuario,clave);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
