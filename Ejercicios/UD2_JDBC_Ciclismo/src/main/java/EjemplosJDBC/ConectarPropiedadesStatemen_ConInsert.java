package EjemplosJDBC;

import java.sql.Connection;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


public class ConectarPropiedadesStatemen_ConInsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		Integer vid;
		String vnombre, vnomeq;
		
		
		try {
			
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
			
			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el ID --> ");
			vid = sc.nextInt();
			
			System.out.print("Introduce el Nombre --> ");
			vnombre = sc.next();
			
			System.out.print("Introduce el Nombre del equipo --> ");
			vnomeq = sc.next();
			
			
			sql = "INSERT INTO representante VALUES (?,?,?)";
			
			stmt = con.prepareStatement(sql);
			
			stmt.setObject(1, vid);
			stmt.setString(2, vnombre);
			stmt.setString(3, vnomeq);
			
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
