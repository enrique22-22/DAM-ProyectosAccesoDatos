package EjemplosJDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class Ejemplo4_AccederResulsetPorFila {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Variables para conectar a la BD.
				Connection con = null;
				PreparedStatement stmt = null;
				Integer result;
				String sql;
				ResultSet rs = null;
				// -------------------------------
				

				try {
					
					//Estas 4 líneas son la conexión a la BD. ---------------------------------
					Properties propiedades = new Properties();
					propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
					
					DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
					con = ds.getConnection();
					System.out.println("Conectado");
					// -------------------------------------------------------------------------
					
					//Obtener el ciclista más joven y más viejo
					//Operaciones BD
					sql = "SELECT * FROM ciclista WHERE" + " nacimiento IS NOT NULL ORDER BY nacimiento DESC";
					
					stmt = con.prepareStatement(sql,  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = stmt.executeQuery();
					
					//más joven
					rs.last();
					System.out.println("El nombre del ciclista más joven es --> " + rs.getString("nombre"));
					
					System.out.println("Más Joven --> " + rs.getString(2) + " - " + rs.getDate("nacimiento"));

					//más mayor
					rs.first();
					System.out.println("Más Mayor --> " + rs.getString(2) + " - " + rs.getDate("nacimiento"));

					
					
					
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
