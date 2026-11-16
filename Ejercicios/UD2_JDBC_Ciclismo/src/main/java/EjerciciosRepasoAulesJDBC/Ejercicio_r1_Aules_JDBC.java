package EjerciciosRepasoAulesJDBC;

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

public class Ejercicio_r1_Aules_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------

		Scanner sc = new Scanner(System.in);
		Integer vedad;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			System.out.print("Introduce la edad --> ");
			vedad = sc.nextInt();
			sc.nextLine();

			// Nota: si tu tabla usa 'edadc' en vez de 'nacimiento', cambia el WHERE por: WHERE edadc > ?
			sql = "SELECT DISTINCT nombre FROM ciclista NATURAL JOIN etapa "
				+ "WHERE year(curdate()) - year(nacimiento) > ?";

			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, vedad);
			rs = stmt.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
