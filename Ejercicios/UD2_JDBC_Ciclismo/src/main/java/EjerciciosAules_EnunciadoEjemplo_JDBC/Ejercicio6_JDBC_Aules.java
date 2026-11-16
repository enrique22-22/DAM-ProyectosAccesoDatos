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

public class Ejercicio6_JDBC_Aules {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Variables para conectar a la BD.
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------
		
		int vdorsal;
		String vnuevoNombre, vnomeq;
		
		
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
			
			System.out.print("Introduce dorsal del nuevo ciclista: ");
			vdorsal = sc.nextInt();
			sc.nextLine();

			System.out.print("Introduce nombre del ciclista: ");
			vnuevoNombre = sc.nextLine();

			System.out.print("Introduce equipo (nomeq): ");
			vnomeq = sc.nextLine();
			
			sql = "SELECT COUNT(*) FROM equipo WHERE nomeq = ?";
			
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, vnomeq);
			rs = stmt.executeQuery();
			
			if (rs.first() && rs.getInt(1) == 0) {
			    System.out.println("ERROR: El equipo '" + vnomeq + "' no existe.");
			} else {
			    // VALIDACIÓN 2 (la que pide el enunciado): Contar cuántos ciclistas tiene ya ese equipo
			    sql = "SELECT COUNT(*) FROM ciclista WHERE nomeq = ?";
			    stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			    stmt.setString(1, vnomeq);
			    rs = stmt.executeQuery();

			    if (rs.first() && rs.getInt(1) >= 5) {
			        System.out.println("ERROR: No se puede inscribir. El equipo '" + vnomeq + "' ya tiene 15 o más ciclistas.");
			    } else {
			        // SI PASA LAS VALIDACIONES -> EJECUTAMOS EL INSERT
			        sql = "INSERT INTO ciclista (dorsal, nombre, nomeq) VALUES (?, ?, ?)";
			        stmt = con.prepareStatement(sql);
			        stmt.setInt(1, vdorsal);
			        stmt.setString(2, vnuevoNombre);
			        stmt.setString(3, vnomeq);

			        int filas = stmt.executeUpdate();
			        
			        if (filas > 0) {
			            System.out.println("Ciclista insertado correctamente en el equipo " + vnomeq + ".");
			        }
			    }
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
