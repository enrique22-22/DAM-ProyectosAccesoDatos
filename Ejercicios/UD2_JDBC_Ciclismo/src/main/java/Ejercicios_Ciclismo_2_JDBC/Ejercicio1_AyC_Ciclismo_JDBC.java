package Ejercicios_Ciclismo_2_JDBC;


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

public class Ejercicio1_AyC_Ciclismo_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------

		Scanner sc = new Scanner(System.in);
		String vnompuerto, vcategoria;
		Integer valtura, vnetapa, vdorsal;
		Double vpendiente;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			System.out.print("Introduce el nombre del puerto --> ");
			vnompuerto = sc.nextLine();

			System.out.print("Introduce la altura --> ");
			valtura = sc.nextInt();

			System.out.print("Introduce la pendiente --> ");
			vpendiente = sc.nextDouble();

			System.out.print("Introduce el número de etapa --> ");
			vnetapa = sc.nextInt();

			System.out.print("Introduce el dorsal del ganador --> ");
			vdorsal = sc.nextInt();

			// Apartado 1c: Asignar categoría automáticamente según la altura
			if (valtura > 1500) {
				vcategoria = "E";
			} else if (valtura >= 1000 && valtura <= 1500) {
				vcategoria = "1";
			} else if (valtura >= 800 && valtura < 1000) {
				vcategoria = "2";
			} else {
				vcategoria = "3";
			}

			// Validación 1 (1a): Comprobar si la etapa existe
			sql = "SELECT count(*) FROM etapa WHERE netapa = ?";
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, vnetapa);
			rs = stmt.executeQuery();

			if (rs.first() && rs.getInt(1) == 0) {
				System.out.println("No se puede insertar: la etapa no existe.");
			} else {
				// Validación 2 (1a): Comprobar que esa etapa no tiene más de 2 puertos de primera categoría
				sql = "SELECT count(*) FROM puerto WHERE netapa = ? AND categoria = '1'";
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1, vnetapa);
				rs = stmt.executeQuery();

				if (rs.first() && vcategoria.equals("1") && rs.getInt(1) >= 2) {
					System.out.println("No se puede insertar: la etapa ya tiene 2 puertos de primera categoría.");
				} else {
					// Insertar el puerto
					sql = "INSERT INTO puerto VALUES (?, ?, ?, ?, ?, ?)";
					stmt = con.prepareStatement(sql);
					stmt.setString(1, vnompuerto);
					stmt.setInt(2, valtura);
					stmt.setString(3, vcategoria);
					stmt.setDouble(4, vpendiente);
					stmt.setInt(5, vnetapa);
					stmt.setInt(6, vdorsal);

					result = stmt.executeUpdate();
					System.out.println("Puerto insertado con categoría " + vcategoria);
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
