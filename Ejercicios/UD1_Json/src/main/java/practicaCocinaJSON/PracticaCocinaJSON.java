package practicaCocinaJSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class PracticaCocinaJSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		JSONArray array;
		
		try {
			
			JSONArray jArray = (JSONArray) parser.parse(new FileReader ("ficheros/Platos.json"));
			
			System.out.println("------------- 1. Obtener los ingredientes del gazpacho ----------------");

			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				if (obj.get("nombre").equals("Gazpacho")) {
					JSONArray ingredientes = (JSONArray) obj.get("ingredientes");
						for (Object resultados : ingredientes) {
							System.out.println("Ingredientes --> " +resultados.toString());
						}
				}
			}
			
			System.out.println();
			System.out.println("------------- 2. Obtener todos los platos (nombre) que tiene el ingrediente tomate ----------------");

			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				JSONArray ingredientes = (JSONArray) obj.get("ingredientes");
					for (Object resultados : ingredientes) {
						JSONObject docIngrediente = (JSONObject) resultados;
						if (docIngrediente.get("nombre").equals("Tomate")) {
							System.out.println("Platos -->  " +obj.get("nombre"));
						}
					}
			}
			
			
			System.out.println();
			System.out.println("------------- 3. Obtener que platos se ofrecen como primero y que ingredientes tienen ----------------");
	
			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				if (obj.get("tipo").equals("Primero")) {
					JSONArray ingredientes = (JSONArray) obj.get("ingredientes");
						for (Object resultados : ingredientes) {
							System.out.println("Plato --> " +obj.get("nombre")+ " - Ingredientes: " +resultados.toString());
						}
				}
			}
			
			
			System.out.println();
			System.out.println("------------- 4. Obtener que cantidad de huevos necesito para poder ofrecer al menos 3 platos de los que necesitan huevo  ----------------");
	
			int cantidadHuevos = 0;
			
			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				JSONArray ingredientes = (JSONArray) obj.get("ingredientes");
					for (Object resultados : ingredientes) {
						JSONObject docIngrediente = (JSONObject) resultados;
						if (docIngrediente.get("nombre").equals("Huevo")) {
							String ud = (String) docIngrediente.get("cantidad");
							int cantidad = Integer.parseInt(ud.split(" ")[0]);
							
							cantidadHuevos += cantidad;
						}
					}
			}
			
			System.out.println("Huevos necesarios --> " +cantidadHuevos);
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
