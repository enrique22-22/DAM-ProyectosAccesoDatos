package Ejemplo1JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conectar_BD {
	
	static String DRIVER = "com.mysql.jdbc.Driver";
	static String URL = "jdbc:mysql://localhost:3306/diinventarioexamen";
	static String USR = "root";
	static String PWD = "mysql";
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = null;
		System.out.println("Registrado");
		
		try {
			con = DriverManager.getConnection(URL, USR, PWD);
			System.out.println("Conectado");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				
				if (con != null && !con.isClosed()) {
					con.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
