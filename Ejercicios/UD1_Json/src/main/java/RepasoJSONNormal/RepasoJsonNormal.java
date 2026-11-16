package RepasoJSONNormal;

import org.json.simple.JSONArray;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RepasoJsonNormal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		JSONArray array = new JSONArray();
		
		try {
			
			JSONArray jArray = (JSONArray) parser.parse(new FileReader("ficheros/Eventos.json"));
			
			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				
				String tipo = (String) obj.get("tipo");
				Long aforo = (Long) obj.get("aforo");
				
				if (tipo.equals("música") && aforo > 400) {
					String nombre = (String) obj.get("nombre");
					String fecha = (String) obj.get("fecha");
					
					System.out.println("Nombres --: " +nombre+ " - fecha --: " +fecha);
					
					JSONObject nuevoEvento = new JSONObject();
					nuevoEvento.put("nombre", nombre);
					nuevoEvento.put("fecha", fecha);
					
					array.add(nuevoEvento);
				}
				
			}
			
			FileWriter fw = new FileWriter("ficheros/EventosFiltrados.json");
			fw.write(array.toJSONString());
			fw.flush();
			fw.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
