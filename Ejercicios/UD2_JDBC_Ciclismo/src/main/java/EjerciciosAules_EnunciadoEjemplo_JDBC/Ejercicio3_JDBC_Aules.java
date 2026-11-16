package EjerciciosAules_EnunciadoEjemplo_JDBC;

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

public class Ejercicio3_JDBC_Aules {

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			//Variables para conectar a la BD.
			Connection con = null;
			PreparedStatement stmt = null;
			Integer result;
			String sql;
			ResultSet rs = null;
			// -------------------------------
			
			String vnomeq;
			
			
			try {
				
				//Estas 4 líneas son la conexión a la BD. ---------------------------------
				Properties propiedades = new Properties();
				propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
				
				DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
				con = ds.getConnection();
				System.out.println("Conectado");
				// -------------------------------------------------------------------------
				
				//Interfaz datos de entrada.
				Scanner sc = new Scanner(System.in);
				
				System.out.print("Introduce el nombre del equipo --> ");
				vnomeq = sc.next();
				
				sql = "SELECT e.salida " + " FROM ciclista c JOIN etapa e ON c.dorsal = e.dorsal " + " WHERE c.nomeq = ?";
				
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setString(1, vnomeq);
				rs = stmt.executeQuery();
				
				boolean hayEtapas = false;
				while(rs.next()) {
					hayEtapas = true;
					System.out.println("Ciudad de salida: " + rs.getString("salida"));
				}
				
				if (!hayEtapas) {
				    System.out.println("No se han encontrado etapas ganadas por ese equipo.");
				}
				
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
