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

public class Ejercicio2_Ciclismo_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		ResultSet rsGana = null;
		// -------------------------------

		Integer vdorsal, vpremioTotal, vcodigoPremio;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			// 1. Sumar los premios de los maillots que ha llevado cada ciclista
			sql = "SELECT l.dorsal, sum(m.premio) FROM llevar l JOIN maillot m ON l.codigo = m.codigo GROUP BY l.dorsal";
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery();

			// 2. Recorrer cada ciclista y asignar el código válido de la tabla premios (1, 2 o 3)[cite: 1]
			while (rs.next()) {
				vdorsal = rs.getInt(1);
				vpremioTotal = rs.getInt(2);

				if (vpremioTotal >= 3000) {
					vcodigoPremio = 3;
				} else if (vpremioTotal >= 1500) {
					vcodigoPremio = 2;
				} else {
					vcodigoPremio = 1;
				}

				// 3. Comprobar si ya existe la clave primaria (dorsal, premio) en gana
				sql = "SELECT count(*) FROM gana WHERE dorsal = ? AND premio = ?";
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1, vdorsal);
				stmt.setInt(2, vcodigoPremio);
				rsGana = stmt.executeQuery();

				if (rsGana.first() && rsGana.getInt(1) == 0) {
					sql = "INSERT INTO gana VALUES (?, ?)";
					stmt = con.prepareStatement(sql);
					stmt.setInt(1, vdorsal);
					stmt.setInt(2, vcodigoPremio);
					stmt.executeUpdate();
					System.out.println("Insertado -> Dorsal: " + vdorsal + " - Suma maillots: " + vpremioTotal + " -> Código premio: " + vcodigoPremio);
				} else {
					System.out.println("Ya existía -> Dorsal: " + vdorsal + " con código premio: " + vcodigoPremio);
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
