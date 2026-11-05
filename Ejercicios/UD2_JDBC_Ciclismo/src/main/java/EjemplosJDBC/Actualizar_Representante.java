package EjemplosJDBC;

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

public class Actualizar_Representante {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		Integer vid;
		String vnomeq;
		
		
		try {
			
			//Estas 4 líneas son la conexión a la BD. ---------------------------------
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
			
			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------
			
			//Modificar el equipo de un representante.
			//Al usuario le pides el ID del representante y el nuevo equipo.
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el ID --> ");
			vid = sc.nextInt();
			
			System.out.print("Introduce el Nombre del equipo --> ");
			vnomeq = sc.next();
			
			sql = "update representante set nom_eq = ? where id = ?";
			
			stmt = con.prepareStatement(sql);
			
			stmt.setObject(2, vid);
			stmt.setString(1, vnomeq);
			
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
