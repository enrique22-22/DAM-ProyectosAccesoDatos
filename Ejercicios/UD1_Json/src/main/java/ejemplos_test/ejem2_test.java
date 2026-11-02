package ejemplos_test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ejem2_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Aquí creo el objeto JSONParser para poder parsear el archivo JSON y guardarlo en memoria.
				JSONParser parser = new JSONParser();
				JSONObject obj2 = null;
				JSONArray pages; // Aquí declaro un JSONArray.
				
				
				// Aquí guardo todo en un try para controlar las excepciones
				try {
					
					System.out.println("------------- Ejemplo de sacar el ID ----------------");
					
					// Aquí guardo dentro de la variable obj el archivo JSON test.json de memoria
					JSONObject obj = (JSONObject) parser.parse(new FileReader ("ficheros/test2.json"));
					
					// Aquí lo que hago es acceder al campo del JSON, igual que en MongoDB, y lo guardo en una variable del mismo tipo, y lo casteo porque viene de Object.
					String v_id = (String) obj.get("id");
					
					System.out.println("El ID es: " +v_id);
					
					System.out.println();
					System.out.println("------------- Ejemplo de sacar el pagesURL ----------------");
					
					
					// Aquí guardo el array pages del JSON para poder acceder después a sus elementos.
					pages = (JSONArray) obj.get("pages");
					
					
					// Aquí creo un ForEach que va a recorrer el array de pages, después creara un nuevo objeto que contendrá cada elemento del array y así por último hacer el get del elemento porque ya es un document.
					for (Object pg: pages) {
						obj2 = (JSONObject) pg;
						System.out.println("la URL es --> " + obj2.get("pageUrl"));
					}
					
					
					System.out.println();
					System.out.println("------------- Ejemplo de obtener los number de la URL http://www.example0.com ----------------");
					
					
					for (Object pg: pages) {
						obj2 = (JSONObject) pg;
						if (obj2.get("pageUrl").equals("http://www.example0.com")) {
								JSONArray result = (JSONArray) obj2.get("results");
								for (Object resultados : result) {
									obj2 = (JSONObject) resultados;
									System.out.println("Los números son --> " +obj2.get("number"));
								}
							
						}
					}
					
					
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}catch (ParseException e) {
					e.printStackTrace();
				}

	}

}
