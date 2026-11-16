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

public class Ejercicio2_JDBC_Aules {

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
			vdorsal = sc.next();
			
			//Operaciones BD. 
			sql = "SELECT e.netapa, c.nombre "
				    + "FROM ciclista c JOIN etapa e ON c.dorsal = e.dorsal "
				    + "WHERE c.dorsal = ?";
			
			//Crear el PrepareStatement.
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, vdorsal);
			
			rs = stmt.executeQuery();
			
			//Recuperar la información en el resultset.
			//Para acceder a un resultset siempre se usa un metodo propio, como un first o un last. Todo esto dentro de una condición siempre.
			boolean encontrado = false;
			if (rs.next()) {
			    encontrado = true;
			    int numEtapa = rs.getInt("netapa");
			    String nombreCiclista = rs.getString("nombre");
			    System.out.println("Etapa: " + numEtapa + " - Ciclista: " + nombreCiclista);
			} 
			
			if (!encontrado) {
			    System.out.println("Ese ciclista no existe o no ha ganado ninguna etapa.");
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
