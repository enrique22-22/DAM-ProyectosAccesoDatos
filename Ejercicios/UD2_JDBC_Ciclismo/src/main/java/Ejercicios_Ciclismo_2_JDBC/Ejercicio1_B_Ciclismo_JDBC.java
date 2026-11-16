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

public class Ejercicio1_B_Ciclismo_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------

		Scanner sc = new Scanner(System.in);
		Integer vnetapa, vkm, vdorsal, vkmTotales;
		String vsalida, vllegada;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			System.out.print("Introduce el número de etapa --> ");
			vnetapa = sc.nextInt();

			System.out.print("Introduce los km de la etapa --> ");
			vkm = sc.nextInt();
			sc.nextLine();

			System.out.print("Introduce la salida --> ");
			vsalida = sc.nextLine();

			System.out.print("Introduce la llegada --> ");
			vllegada = sc.nextLine();

			System.out.print("Introduce el dorsal del ganador --> ");
			vdorsal = sc.nextInt();

			// Validación 1 (1b): Comprobar que sus km están entre 100 y 300
			if (vkm < 100 || vkm > 300) {
				System.out.println("No se puede insertar: los km deben estar entre 100 y 300.");
			} else {
				// Validación 2 (1b): Comprobar que la suma total de km no supera 1500
				sql = "SELECT sum(km) FROM etapa";
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery();

				vkmTotales = 0;
				if (rs.first()) {
					vkmTotales = rs.getInt(1);
				}

				if ((vkmTotales + vkm) > 1500) {
					System.out.println("No se puede insertar: se superan los 1500 km totales de la vuelta.");
				} else {
					// Insertar la etapa
					sql = "INSERT INTO etapa VALUES (?, ?, ?, ?, ?)";
					stmt = con.prepareStatement(sql);
					stmt.setInt(1, vnetapa);
					stmt.setInt(2, vkm);
					stmt.setString(3, vsalida);
					stmt.setString(4, vllegada);
					stmt.setInt(5, vdorsal);

					result = stmt.executeUpdate();
					System.out.println("Etapa insertada correctamente.");
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
