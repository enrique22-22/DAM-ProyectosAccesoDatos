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

public class Ejercicio_r4_Aules_JDBC {

	public static void main(String[] args) {

		// Variables para conectar a la BD
		Connection con = null;
		PreparedStatement stmt = null;
		Integer result;
		String sql;
		ResultSet rs = null;
		// -------------------------------

		Scanner sc = new Scanner(System.in);
		Integer vdorsal, vnetapa;
		String vcodigo;

		try {
			// Conexión a la BD
			Properties propiedades = new Properties();
			propiedades.load(new FileInputStream("Configuracion\\PropiedadesCiclismo"));

			DataSource ds = BasicDataSourceFactory.createDataSource(propiedades);
			con = ds.getConnection();
			System.out.println("Conectado");
			// -------------------------------------------------------------------------

			System.out.print("Introduce el dorsal del ciclista --> ");
			vdorsal = sc.nextInt();

			System.out.print("Introduce el número de etapa --> ");
			vnetapa = sc.nextInt();
			sc.nextLine();

			System.out.print("Introduce el código del maillot --> ");
			vcodigo = sc.nextLine();

			// VALIDACIÓN 1: Comprobar que existen el ciclista, la etapa y el maillot
			sql = "SELECT count(*) FROM ciclista WHERE dorsal = ?";
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, vdorsal);
			rs = stmt.executeQuery();
			boolean existeCiclista = rs.first() && rs.getInt(1) > 0;

			sql = "SELECT count(*) FROM etapa WHERE netapa = ?";
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1, vnetapa);
			rs = stmt.executeQuery();
			boolean existeEtapa = rs.first() && rs.getInt(1) > 0;

			sql = "SELECT count(*) FROM maillot WHERE codigo = ?";
			stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setString(1, vcodigo);
			rs = stmt.executeQuery();
			boolean existeMaillot = rs.first() && rs.getInt(1) > 0;

			if (!existeCiclista || !existeEtapa || !existeMaillot) {
				System.out.println("Error: El ciclista, la etapa o el maillot no existen en la base de datos.");
			} else {
				// VALIDACIÓN 2: Comprobar si ese maillot ya lo lleva otro ciclista en esa etapa
				sql = "SELECT c.nombre FROM llevar l JOIN ciclista c ON l.dorsal = c.dorsal WHERE l.netapa = ? AND l.codigo = ?";
				stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				stmt.setInt(1, vnetapa);
				stmt.setString(2, vcodigo);
				rs = stmt.executeQuery();

				if (rs.first()) {
					System.out.println("No se puede asignar: ese maillot ya lo lleva " + rs.getString("nombre") + " en la etapa " + vnetapa + ".");
				} else {
					// VALIDACIÓN 3: Comprobar si ese ciclista ya lleva otro maillot en esa etapa
					sql = "SELECT count(*) FROM llevar WHERE netapa = ? AND dorsal = ?";
					stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					stmt.setInt(1, vnetapa);
					stmt.setInt(2, vdorsal);
					rs = stmt.executeQuery();

					if (rs.first() && rs.getInt(1) > 0) {
						System.out.println("No se puede asignar: el ciclista con dorsal " + vdorsal + " ya lleva otro maillot en esa etapa.");
					} else {
						// TODO CORRECTO -> Insertamos en llevar
						sql = "INSERT INTO llevar (dorsal, netapa, codigo) VALUES (?, ?, ?)";
						stmt = con.prepareStatement(sql);
						stmt.setInt(1, vdorsal);
						stmt.setInt(2, vnetapa);
						stmt.setString(3, vcodigo);

						result = stmt.executeUpdate();
						System.out.println("Maillot asignado correctamente al ciclista.");
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
