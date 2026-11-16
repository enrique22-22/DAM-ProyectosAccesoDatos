package EjerciciosAules_EnunciadoEjemplo_JDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejercicio1_JDBC_Aules {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		Integer vpremio;
		String vcolor, vtipo, vcodigo;
		
		
		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
			
			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el Código--> ");
			vcodigo = sc.next();
			
			System.out.print("Introduce el Tipo --> ");
			vtipo = sc.next();
			
			System.out.print("Introduce el Color --> ");
			vcolor = sc.next();
			
			System.out.print("Introduce el Premio --> ");
			vpremio = sc.nextInt();
			
			
			sql = "INSERT INTO maillot VALUES (?,?,?,?)";
			
			stmt = con.prepareStatement(sql);
			
			stmt.setObject(1, vcodigo);
			stmt.setString(2, vtipo);
			stmt.setString(3, vcolor);
			stmt.setInt(4, vpremio);
			
			stmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
