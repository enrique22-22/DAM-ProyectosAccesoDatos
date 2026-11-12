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

public class Ejemplo2_ActualizarFilaResulset_JDBC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Variables para conectar a la BD.
				Connection con = null;
				PreparedStatement stmt = null;
				Integer result;
				String sql;
				ResultSet rs = null;
				// -------------------------------
				
				Integer vpremio;
		
				try {
					
					//Estas 4 líneas son la conexión a la BD. ---------------------------------
					Properties propiedades = new Properties();
					propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
					
					DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
					con = ds.getConnection();
					System.out.println("Conectado");
					// -------------------------------------------------------------------------
					
					sql = "SELECT codigo, premio FROM maillot";
					
					stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

					rs = stmt.executeQuery();
					
					while(rs.next()) {
						vpremio = rs.getInt("premio");
						rs.updateInt("premio", vpremio + 100);
						rs.updateRow();
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
