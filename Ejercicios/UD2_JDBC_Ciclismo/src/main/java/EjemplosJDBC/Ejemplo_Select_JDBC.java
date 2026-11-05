package EjemplosJDBC;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;


public class Ejemplo_Select_JDBC {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Variables para conectar a la BD.
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------
		
		Integer vcont;
		String vnomeq;
		
		
		try {
			
			//Estas 4 líneas son la conexión a la BD. ---------------------------------
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));
			
			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------
			
			//Obtener cuantos ciclistas tiene el equipo que introduzca el usuario.
			
			//Interfaz datos de entrada.
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el equipo --> ");
			vnomeq = sc.next();
			
			//Operaciones BD. 
			sql = "SELECT count(*),nomeq from ciclista where nomeq = ?";
			
			//Crear el PrepareStatement.
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			stmt.setString(1, vnomeq);
			
			rs = stmt.executeQuery();
			
			//Recuperar la información en el resultset.
			//Para acceder a un resultset siempre se usa un metodo propio, como un first o un last. Todo esto dentro de una condición siempre.
			if (rs.first() && rs.getInt(1) != 0) {
				
				vcont = rs.getInt(1);
				vnomeq = rs.getString("nomeq");
				
				//Si el equipo tiene más de 10 integrantes, escribiremos el "nomeq" es numeroso.
				//Si no es pequeño.
				
				if (vcont > 10) {
					System.out.println("El equipo " + vnomeq + " es numeroso porque tiene " +vcont+ " ciclistas");
				} else {
					System.out.println("El equipo " + vnomeq + " es pequeño porque tiene " +vcont+ " ciclistas");
				}
				
			} else {
				System.out.println("El equipo no tiene ciclistas.");
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
