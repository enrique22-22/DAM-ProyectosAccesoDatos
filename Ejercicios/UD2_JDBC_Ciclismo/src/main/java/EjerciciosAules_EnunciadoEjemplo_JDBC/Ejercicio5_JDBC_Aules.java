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

public class Ejercicio5_JDBC_Aules {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			//Variables para conectar a la BD.
			Connection con = null;
			PreparedStatement stmt = null;
			Integer result;
			String sql;
			ResultSet rs = null;
			// -------------------------------
			
			String vdorsal;
			
			
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
				
				System.out.print("Introduce el dorsal --> ");
				vdorsal= sc.next();
				
				sql = "SELECT COUNT(*) FROM etapa WHERE dorsal = ?";
				
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setString(1, vdorsal);
				rs = stmt.executeQuery();
				
				if (rs.first() && rs.getInt(1) > 0) {
				    System.out.println("El ciclista con dorsal " + vdorsal + " SÍ ha ganado alguna etapa (total: " + rs.getInt(1) + ").");
				} else {
				    System.out.println("El ciclista con dorsal " + vdorsal + " NO ha ganado ninguna etapa.");
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
