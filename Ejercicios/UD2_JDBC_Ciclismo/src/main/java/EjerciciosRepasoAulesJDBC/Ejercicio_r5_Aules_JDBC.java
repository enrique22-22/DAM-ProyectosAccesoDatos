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

public class Ejercicio_r5_Aules_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------

		Scanner sc = new Scanner(System.in);
		String vequipoAbsorbido, vequipoNuevo;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			System.out.print("Introduce el equipo que va a ser absorbido (desaparece) --> ");
			vequipoAbsorbido = sc.nextLine();

			System.out.print("Introduce el equipo que absorbe (el destino) --> ");
			vequipoNuevo = sc.nextLine();

			// Iniciamos la transacción: todo o nada
			con.setAutoCommit(false);

			// 1. Los ciclistas del equipo absorbido pasan al nuevo equipo
			sql = "UPDATE ciclista SET nomeq = ? WHERE nomeq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vequipoNuevo);
			stmt.setString(2, vequipoAbsorbido);
			stmt.executeUpdate();

			// 2. Sus coches pasan al nuevo equipo
			sql = "UPDATE coche SET nomeq = ? WHERE nomeq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vequipoNuevo);
			stmt.setString(2, vequipoAbsorbido);
			stmt.executeUpdate();

			// 3. Su representante pasa al nuevo equipo
			sql = "UPDATE representante SET nomeq = ? WHERE nomeq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vequipoNuevo);
			stmt.setString(2, vequipoAbsorbido);
			stmt.executeUpdate();

			// 4. Se borra el equipo absorbido
			sql = "DELETE FROM equipo WHERE nomeq = ?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, vequipoAbsorbido);
			stmt.executeUpdate();

			// Si los 4 pasos han ido bien, confirmamos la transacción
			con.commit();
			con.setAutoCommit(true);
			System.out.println("Fusión completada correctamente: " + vequipoAbsorbido + " ha sido absorbido por " + vequipoNuevo + ".");

		} catch (SQLException e) {
			try {
				if (con != null) {
					con.rollback();
					System.out.println("Error en la fusión. Se ha realizado rollback y no se ha modificado nada.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
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
