package RepasoJSONPojosGSON;

import java.io.FileWriter;
import java.util.ArrayList;

import org.bson.Document;

import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MainRepasoPOJOS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MongoCursor<Document> cursor_coches = null;
		Bson bsonFilter = null;
		Document doc_coche = null;
		String cocheJsonString = null;
		
		ArrayList<ClasePOJOCoche> listaCoches = new ArrayList<>();

		MongoClient mongoCliente = MongoClients.create();
		MongoDatabase baseDatos = mongoCliente.getDatabase("Concesionario");
		MongoCollection col_coches = baseDatos.getCollection("coches"); 
		
		try {
			System.out.println("=== 1. DE MONGODB A OBJETOS POJO JAVA (fromJson) ===");
			
			bsonFilter = Filters.gt("Value", 10000);
			cursor_coches = col_coches.find(bsonFilter).iterator();
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();;
			
			while(cursor_coches.hasNext()) {
				doc_coche = cursor_coches.next();
				cocheJsonString = doc_coche.toJson();
				
				ClasePOJOCoche objCoche = gson.fromJson(cocheJsonString, ClasePOJOCoche.class);
				listaCoches.add(objCoche);
				
				
			}
			
			for (ClasePOJOCoche c : listaCoches) {
				System.out.println("Modelo: " + c.getModel() + " | Matrícula: " + c.getMatrícula() + " | Año: " + c.getYear());
				if (c.getRevisiones() !=null) {
					for (ClasePOJORevision r : c.getRevisiones()) {
						System.out.println("   -> Revisión: " + r.getTipo() + " (" + r.getFecha() + ")");
					}
				}
			
			}
			
			System.out.println("\n=== 2. DE ARRAYLIST<COCHE> A ARCHIVO JSON (toJson) ===");
			
			String jsonArchivo = gson.toJson(listaCoches);
			System.out.println(jsonArchivo);
			
			FileWriter fw = new FileWriter("ficheros/coches_caros.json");
			fw.write(jsonArchivo);
			fw.flush();
			fw.close();
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            if (cursor_coches!= null) {
                cursor_coches.close();
            }
            mongoCliente.close();
        }
		
		
		
		
	}

}
