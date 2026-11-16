import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

public class EjerciciosRepasoMongoDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Declarar las variables 
		
		Scanner sc = new Scanner(System.in);
		Integer contadorCoches = 0;
		String ciudad= "";
		
		// Colección de tipo MongoCursor en la que guardas los documentos Bson de la colección libros
		MongoCursor <Document> cursor_personas = null;
		MongoCursor<Document> cursor_ventas = null;
		MongoCursor <Document> cursor_coches = null;
		
		Document persona = null;
		Document venta = null;
		Document coche = null;
		
		// Objetos para manejar filtros
		Bson bsonFilter = null;
		Bson bsonProyeccion = null;
		
		// Lista para guardar los IDs de las personas que viven en esa ciudad
        List<Object> listaIdsPersonas = new ArrayList<Object>();
        List<Object> listaIdsCoches = new ArrayList<Object>();
		
		// Conectamos a la BD de MongoDB
		MongoClient mongoCliente = MongoClients.create();
		
		// Conectar a la BD Biblioteca
		MongoDatabase baseDatos = mongoCliente.getDatabase("Concesionario"); // Aquí declaro la variable que hace referencia a la BD de Biblicoteca
		MongoCollection col_personas= baseDatos.getCollection("clientes"); // Aquí declaro la variable que hace referenecia a la colección Libros dentro de la BD Biblioteca
		MongoCollection col_ventas = baseDatos.getCollection("ventas");
		MongoCollection col_coches = baseDatos.getCollection("coches"); // Aquí declaro la variable que hace referenecia a la colección Prestamos dentro de la BD Biblioteca
		System.out.println("Conectado ");
		
		
		
		try {
			System.out.print("Introduce la ciudad: ");
			ciudad = sc.nextLine();
			
			// PASO A: Buscar en 'Personas' las que viven en esa ciudad
			bsonFilter = Filters.eq("City", ciudad);
			cursor_personas = col_personas.find(bsonFilter).iterator();
			
			while(cursor_personas.hasNext()) {
				persona = (Document) cursor_personas.next();
				// Guardamos su identificador (revisa en Compass si el enlace es "_id" o "Pers_ID")
				listaIdsPersonas.add(persona.getInteger("id_persona"));
			}
			
			// PASO 2: Buscar en 'Ventas' las compras hechas por esas personas (Filters.in)
			bsonFilter = Filters.in("id_persona", listaIdsPersonas);
			cursor_ventas = col_ventas.find(bsonFilter).iterator();
			
			while(cursor_ventas.hasNext()) {
				venta = cursor_ventas.next();
				listaIdsCoches.add(venta.getInteger("id_car"));
			}
			
			// PASO 3: Buscar en 'Coches' esos id_car y sacar la proyección JSON
			bsonFilter = Filters.in("id_car", listaIdsCoches);
			bsonProyeccion = Projections.fields(Projections.include("Matrícula", "Model"), Projections.excludeId());
			
			cursor_coches = col_coches.find(bsonFilter).projection(bsonProyeccion).iterator();
			
			while(cursor_coches.hasNext()) {
				coche = cursor_coches.next();
				System.out.println(coche.toJson());
			}
			
			// PASO 4: Contar cuántos coches cumplen el filtro
			long totalCoches = col_coches.countDocuments(bsonFilter);
			System.out.println("Total de coches en " + ciudad + ": " + totalCoches);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
