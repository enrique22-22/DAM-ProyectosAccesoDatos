package EjemplosJDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejemplo1_Transacciones_JDBC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Variables para conectar a la BD.
				Connection con = null;
				PreparedStatement stmt = null;
				Integer result;
				String sql;
				ResultSet rs = null;
				// -------------------------------
				
				Integer vid;
				String vnombre, vnomeq;

				//Interfaz datos de entrada.
				Scanner sc = new Scanner(System.in);
				
				System.out.println("-- DATOS DEL EQUIPO --");
				
				System.out.print("Introduce el Nombre del equipo --> ");
				vnomeq = sc.next();
				
				System.out.println();
				System.out.println("-- DATOS DEL REPRESENTANTE --");
				
				System.out.print("Introduce el ID --> ");
				vid = sc.nextInt();
				vnombre = sc.nextLine();
				
				System.out.print("Introduce el Nombre --> ");
				vnombre = sc.next();
				
				
				
				try {
					
					//Estas 4 líneas son la conexión a la BD. ---------------------------------
					Properties propiedades = new Properties();
					propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
					
					DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
					con = ds.getConnection();
					System.out.println("Conectado");
					// -------------------------------------------------------------------------

					con.setAutoCommit(false);
					
					sql = "INSERT INTO equipo (nomeq) VALUES (?)";
					
					stmt = con.prepareStatement(sql);
					stmt.setObject(1, vnomeq);
					stmt.executeUpdate();
					
					sql = "INSERT INTO representante VALUES (?,?,?)";
					stmt = con.prepareStatement(sql);
					stmt.setInt(1, vid);
					stmt.setString(2,vnombre);
					stmt.setString(3, vnomeq);
					
					stmt.executeUpdate();
					
					con.commit();
					
					con.setAutoCommit(true);
					
					
					
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
