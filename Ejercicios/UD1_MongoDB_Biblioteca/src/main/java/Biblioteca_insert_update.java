import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class Biblioteca_insert_update {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Declarar las variables 
		
		String numeroSocio = "";
		Integer socioNumero = 0;
		String cad = "";
		
		// Colección de tipo MongoCursor en la que guardas los documentos Bson de la colección libros
		MongoCursor <Document> cursor_libros = null;
		MongoCursor <Document> cursor_prestamos = null;
		MongoCursor <Document> cursor_socios = null;
		
		Document libro = null;
		Document autor = null;
		Document num_socio = null;
		
		// Objetos para manejar filtros
		Bson bsonFilter = null;
		
		// Conectamos a la BD de MongoDB
		MongoClient mongoCliente = MongoClients.create();
		
		// Conectar a la BD Biblioteca
		MongoDatabase baseDatos = mongoCliente.getDatabase("Biblioteca"); // Aquí declaro la variable que hace referencia a la BD de Biblicoteca
		MongoCollection col_libros = baseDatos.getCollection("Libros"); // Aquí declaro la variable que hace referenecia a la colección Libros dentro de la BD Biblioteca
		MongoCollection col_prestamos = baseDatos.getCollection("Prestamos"); // Aquí declaro la variable que hace referenecia a la colección Prestamos dentro de la BD Biblioteca
		MongoCollection col_socios = baseDatos.getCollection("Socios"); // Aquí declaro la variable que hace referenecia a la colección Socios dentro de la BD Biblioteca
		
		// Recorremos la colección con un bucle
		try {
			System.out.println("----------------- Ejemplo A -----------------------");
			cursor_libros = col_libros.find().iterator();
			
			// Recorremos la colección libros y obtenemos en Json su contenido
			while(cursor_libros.hasNext()) {
				//System.out.println(cursor_libros.next().toJson());
				
				// Sacar por pantalla los títulos de los libros
				System.out.println(cursor_libros.next().get("titulo"));
			}
			System.out.println();
			System.out.println("----------------- Ejemplo B -----------------------");
			
			// Obtener el primer elemento de una colección 
			libro = (Document) col_libros.find().first();
			System.out.println("El primer libro " +libro.toJson());
			
			System.out.println();
			System.out.println("----------------- Ejemplo C -----------------------");
			
			// Obtener del primer libro el ISBN
			System.out.println("El isbn del primer libro es: " +libro.get("isbn"));
			
			System.out.println();
			System.out.println("----------------- Ejemplo D -----------------------");
			
			// Obtener del primer libro el nombre del autor
			autor = (Document) libro.get("autor");
			System.out.println("El nombre del autor del primer libro es: " +autor.get("nombre"));
			
			
			System.out.println();
			System.out.println("----------------- Insertar un libro a la BD -----------------------");
			
			// Ejemplo de añadir un libro a la base de Datos.
			List<String> Generos = new ArrayList<String>();
			
			Generos.add("juvenil");
			Generos.add("fantasia");
			Generos.add("aventura");
			
			libro = new Document ("isbn", 12345).
					append("titulo", "El día de la trilla").
					append("autor", new Document ("nombre", "Rebecca").
							append("apellido", "Yarros").
							append("nacionalidad", "Americana")).
					append("editorial", "Planeta").
					append("año_publicacion", 2026).
					append("páginas", 900).
					append("genero", Generos);
			
			//col_libros.insertOne(libro);
			
			System.out.println();
			System.out.println("----------------- Filtro para libros -----------------------");
			
			// Filtros.
			
			// Mostrar los libros (documento Libros de año de publicación mayor que 2000).
			bsonFilter = Filters.gt("año_publicacion", 2000); // Aquí se crea el filtro con la condición de buscar por "año_publicacion".
			
			cursor_libros = col_libros.find(bsonFilter).iterator(); // Aquí se declara que busque dentro de la colección libros, pero solo por el filtro declarado arriba.
			
			// Aquí se usa un bucle para recorrer todos los libros y mostrarlo en formato Json.
			while(cursor_libros.hasNext()) {
				//System.out.println("Libros publicados > 2000 -> " + cursor_libros.next().toJson());
				
				// Sacar el título solo de los libros filtrados.
				System.out.println("Título Libros publicados > 2000 -> " + cursor_libros.next().get("titulo"));

			}
			
			System.out.println();
			
			// Mostrar los libros que entre el 2000 y 2020.
			bsonFilter = Filters.and(Filters.gt("año_publicacion", 2000), Filters.lt("año_publicacion", 2020));
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while(cursor_libros.hasNext()) {
				System.out.println("Libros publicados entre 2000 y 2020 -> " + cursor_libros.next().toJson());
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 1. Buscar el registro del primer libro (clase) -----------------------");
			
			cursor_libros = col_libros.find().iterator();
			
			libro = (Document) col_libros.find().first();
			
			System.out.println("El registro del primer libro es: " +libro.toJson());
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 2. Buscar todos los libros (clase)-----------------------");
			
			cursor_libros = col_libros.find().iterator();
			
			while(cursor_libros.hasNext()) {
				System.out.println(cursor_libros.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 3. Obtener la lista de socios (clase) -----------------------");
			
			cursor_socios = col_socios.find().iterator();
			
			while(cursor_socios.hasNext()) {
				System.out.println(cursor_socios.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 4. Obtener el nombre de los socios (usar método get()) (clase) -----------------------");
			
			cursor_socios = col_socios.find().iterator();
			
			while(cursor_socios.hasNext()) {
				System.out.println(cursor_socios.next().get("Nombre"));
			}
			
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 5. Buscar el libro con titulo \"El Quijote\" (clase) -----------------------");
			
			bsonFilter = Filters.eq("titulo" , "El Quijote");
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while (cursor_libros.hasNext()) {
				System.out.println("El libro es: " + cursor_libros.next().toJson());
			}
			
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 6. Buscar el préstamo con dos libros -----------------------");
			
			bsonFilter = Filters.size("libro", 2); // Este filtro busca el parámetro que se llame "libro" dentro de la colección y que tenga 2 posiciones.
			
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator(); // Aquí se declara que busque dentro de la colección libros, pero solo por el filtro declarado arriba. 
			
			while(cursor_prestamos.hasNext()) {
				System.out.println("El préstamos con dos libros es: " + cursor_prestamos.next().toJson());
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 7. Buscar el título de libros de autor que se apellida Follet -----------------------");
			
			
			bsonFilter = Filters.eq("autor.apellido", "Follet"); // Este filtro hace que puedas acceder al campo que sea dentro de un array para así compararlo por su apellido en vez de por nombre
			
			cursor_libros = col_libros.find(bsonFilter).iterator(); // Aquí se declara que busque dentro de la colección libros, pero solo por el filtro declarado arriba. 

			while(cursor_libros.hasNext()) {
				System.out.println("Título de los libros " + cursor_libros.next().get("titulo"));
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 8. Buscar dentro de Prestamos el documento del libro con ISBN 446854 -----------------------");
			
			bsonFilter = Filters.eq("libro.isbn", "446854"); // Este filtro hace que puedas acceder al campo isbn dentro del array de libro para coger uno en específico
			
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator(); 

			while(cursor_prestamos.hasNext()) {
				System.out.println("El libro con isbn 446854 es: " + cursor_prestamos.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejemplo 8.2. Mostrar el título del libro con isbn 446854 solo si este libro tiene algún préstamo -----------------------");
			
			bsonFilter = Filters.eq("libro.isbn", "446854"); // Este filtro hace que puedas acceder al campo isbn dentro del array de libro para coger uno en específico
			
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator(); 

			// si tiene préstamos, entonces...
			if (cursor_prestamos != null) {
				// Buscar el título del libro
				bsonFilter = Filters.eq("isbn", "446854"); 
				
				cursor_libros = col_libros.find(bsonFilter).iterator(); 

				while(cursor_libros.hasNext()) {
					System.out.println("Título de los libros con isbn 446854 es: " + cursor_libros.next().get("titulo"));
				}
			} else {
				System.out.println("Este isbn 446854 no tiene préstamos");
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 9. Buscar el nombre de los socios que han tenido prestado el libro con isbn 456779 -----------------------");
			
			
			while(cursor_prestamos.hasNext()) {
		
				Document prestamos = cursor_prestamos.next();
				num_socio = (Document) prestamos.get("socio"); // Aquí cojo el valor del elemento socio haciendo el get().
			
				numeroSocio = num_socio.getString("num_socio"); // Aquí cojo ese valor y lo guardo como String porque dentro de la colección de Prestamos es un String el num_socio.
				
				socioNumero = Integer.parseInt(numeroSocio); // Aquí hago un parseo de String a Integer porque el Num_socio dentro de la colección Socios es un Integer.
				
				bsonFilter = Filters.eq("Num_socio", socioNumero); // Aquí creo el filtro para que busque dentro del parámetro Num_socio de la colección Socios por el valor parseado de num_socio de Prestamos.
				
				cursor_socios = col_socios.find(bsonFilter).iterator();
				
				// Aqui recorro la colección de Socios para mostrar el nombre de cada uno de los socios.
				while(cursor_socios.hasNext()) {
					System.out.println("El nombre de los socios que han tenido prestado el libro con isbn 456779 es: " + cursor_socios.next().get("Nombre"));
				}
				
			}
			

			System.out.println();
			System.out.println("----------------- Ejercicio 10. Buscar libros con año mayor que 2000 y de más de 500 páginas -----------------------");
			
			bsonFilter = Filters.and(Filters.gt("año_publicacion", 2000), Filters.gt("páginas", 500));
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while(cursor_libros.hasNext()) {
				
				Document doc = cursor_libros.next();
				
				System.out.println("Los libros con año mayor a 2000 son: " +doc.get("titulo")+ " --> que contiene " +doc.get("páginas")+ " páginas");
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 11. Buscar libros editados entre los años 2010 y 2020 -----------------------");
			
			bsonFilter = Filters.and(Filters.gte("año_publicacion", 2010), Filters.lte("año_publicacion", 2020));
			
			cursor_libros = col_libros.find(bsonFilter).iterator();

			while(cursor_libros.hasNext()) {
				System.out.println("Los libros que están entre los años 2010 y 2020 son: " +cursor_libros.next().get("titulo"));
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 12. Buscar los libros de la editorial Debolsillo -----------------------");
			
			bsonFilter = Filters.eq("editorial", "Debolsillo");
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while(cursor_libros.hasNext()) {
				System.out.println("Los libros de la editorial Debolsillo son: " +cursor_libros.next().get("titulo"));
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 13. Mostrar el nombre del socio 2 -----------------------");
			bsonFilter = Filters.eq("Num_socio", 2);
			cursor_socios = col_socios.find(bsonFilter).iterator();
			
			if (cursor_socios.hasNext()) {
			    System.out.println("El nombre del socio 2 es: " + cursor_socios.next().get("Nombre"));
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 14. Libros (título, nombre autor) de autor americano O editorial Planeta -----------------------");
			bsonFilter = Filters.or(Filters.eq("autor.nacionalidad", "Americana"),Filters.eq("editorial", "Planeta"));
			
			cursor_libros = col_libros.find(bsonFilter).projection(Projections.fields(Projections.include("titulo", "autor.nombre"), Projections.excludeId())).iterator();
			
			while (cursor_libros.hasNext()) {
			    System.out.println(cursor_libros.next().toString());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 15. Mostrar la fecha de préstamo del libro prestado al socio num_socio 1 -----------------------");
			bsonFilter = Filters.eq("socio.num_socio", "1");
			cursor_prestamos = col_prestamos.find(bsonFilter).iterator();
			
			while (cursor_prestamos.hasNext()) {
			    Document prestamo = cursor_prestamos.next();
			    System.out.println("Fecha préstamo socio 1: " + prestamo.get("fecha_pres"));
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 16. Buscar libros editados entre 1990 y 2000 -----------------------");
			bsonFilter = Filters.and(Filters.gte("año_publicacion", 1990),Filters.lte("año_publicacion", 2000));
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while (cursor_libros.hasNext()) {
			    System.out.println("Libro 1990-2000: " + cursor_libros.next().get("titulo"));
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 17. Mostrar el título de los libros escritos por isaac Asimov -----------------------");
			
			bsonFilter = Filters.eq("autor.apellido", "Asimov");
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.descending("titulo")).iterator();
			//cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo")).iterator();
			
			while (cursor_libros.hasNext()) {
				System.out.println("El título de los libros de Asimov son: " + cursor_libros.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 17.2. Proyección (extraer del filtro de los libros de Asimov un JSON con el título y el año_publicación -----------------------");
			
			// El Projections.fields hace que puedas poner más de un Projections. 
			// Con el include hace que los campos que pongas, son los que se van a mostrar y el excludeId hace que quite el primer ID del JSON
			cursor_libros = col_libros.find(bsonFilter).projection(Projections.fields(Projections.include("titulo", "año_publicacion"), Projections.excludeId())).iterator();
			
			
			// Sacar lo de arriba pero ordenado.
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo")).projection(Projections.fields(Projections.include("titulo", "año_publicacion"), Projections.excludeId())).iterator();
			
			
			// Excuir del JSON que nos devuelve la editorial
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("titulo")).projection(Projections.fields(Projections.exclude("editorial", "año_publicacion"), Projections.excludeId())).iterator();

			
			while (cursor_libros.hasNext()) {
				System.out.println("Lista de libros Proyección por título: " +cursor_libros.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 18. Mostrar el nombre de los socios que se ha llevado prestado el libro de título El Quijote -----------------------");
			
			bsonFilter = Filters.eq("titulo", "El Quijote");
			
			cursor_libros = col_libros.find(bsonFilter).iterator();
			
			while(cursor_libros.hasNext()) {
				
				cad	= (String) cursor_libros.next().get("isbn");
				bsonFilter = Filters.eq("libro.isbn", cad);
				cursor_prestamos = col_prestamos.find(bsonFilter).iterator();
				
				while(cursor_prestamos.hasNext()) {
					num_socio = (Document) cursor_prestamos.next().get("socio");
					
					String socios = num_socio.getString("num_socio");
					
					Integer numero = Integer.parseInt(socios);
					bsonFilter = Filters.eq("Num_socio", numero);
		
					num_socio = (Document) col_socios.find(bsonFilter).first();
					
					System.out.println("El nombre de los socios que se han llevado el libro de El Quijote es: " + num_socio.get("Nombre"));
					
				}
				
			}
			
			System.out.println();
			System.out.println("----------------- Ejercicio 19. Mostrar el título de los libros que tiene prestados el socio con nombre Javier García  -----------------------");
			
			bsonFilter = Filters.eq("Nombre", "Javier Garcia");
			cursor_socios = col_socios.find(bsonFilter).iterator();
			
			while(cursor_socios.hasNext()) {
				
				Document socio = cursor_socios.next();
				
				socioNumero = socio.getInteger("Num_socio");
				String numeros = String.valueOf(socioNumero);
				//System.out.println(numeros);
				
				bsonFilter = Filters.eq("socio.num_socio", numeros);
				
				cursor_prestamos = col_prestamos.find(bsonFilter).iterator();
				
				while(cursor_prestamos.hasNext()) {
					
					Document prestamo = cursor_prestamos.next();
					
					List<Document> datosLibros = prestamo.getList("libro", Document.class);
					
					for (Document document : datosLibros) {
						String isbns = document.getString("isbn");
						
						bsonFilter = Filters.eq("isbn", isbns);
							
						cursor_libros = col_libros.find(bsonFilter).iterator();
						
						while(cursor_libros.hasNext()) {
							System.out.println("El título de los libros que tiene prestado Javier García es: " + cursor_libros.next().get("titulo"));
						}
						
					}
					
				}
				
			}
			
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 20. Mostrar el título y el autor de los libros de la editorial Planeta ordenados por el año de edición  -----------------------");
			
			bsonFilter = Filters.eq("editorial", "Planeta");
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("año_publicacion")).projection(Projections.fields(Projections.include("titulo", "autor.nombre"), Projections.excludeId())).iterator();
	
			while (cursor_libros.hasNext()) {
				System.out.println("El título y el autor de los libros con editorial Planeta son: " + cursor_libros.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 21. Mostrar el título y la editorial (proyección) de los libros ordenados por el número de páginas  -----------------------");
			
			cursor_libros = col_libros.find(bsonFilter).sort(Sorts.ascending("páginas")).projection(Projections.fields(Projections.include("titulo", "editorial"), Projections.excludeId())).iterator();

			while (cursor_libros.hasNext()) {
				System.out.println("El título y el autor de los libros con editorial Planeta son: " + cursor_libros.next().toJson());
			}
			
			
			System.out.println();
			System.out.println("----------------- Ejercicio 22. Mostrar el título de los libros que tienen algún préstamo -----------------------");
		
			cursor_prestamos = col_prestamos.find().projection(Projections.fields(Projections.include("libro"), Projections.excludeId())).iterator();;
			
			while(cursor_prestamos.hasNext()) {
				
				Document prestamo = cursor_prestamos.next();
				List<Document> datosLibros = prestamo.getList("libro", Document.class);
				
				for (Document document : datosLibros) {
					String isbns = document.getString("isbn");
					
					bsonFilter = Filters.eq("isbn", isbns);
						
					cursor_libros = col_libros.find(bsonFilter).iterator();
					
					System.out.println("El título de los libros prestados son: " + cursor_libros.next().get("titulo"));
					
					
				}
				
			}
			
			
			System.out.println();
			System.out.println("----------------- FIN DE LOS EJERCICIOS DEL BOLETIN 01 DE AULES -----------------------");
			
			
			
			System.out.println();
			System.out.println("----------------- Ejemplo de Actualizar el ISBN 456779. Añadir el campo Páginas a 1200 -----------------------");
			
			bsonFilter = Filters.eq("isbn", "12345");

			// Esto es para actualizar todas las filas que coincidan con el filtro de arriba.
			col_libros.updateMany(bsonFilter, new Document ("$set", new Document ("páginas", 1200 )));
			
			// Esto es para actualizar solo una fila por el campo que filtras.
			//col_libros.updateOne(bsonFilter, new Document ("$set", new Document ("páginas", 1200 )));
			
			
			// Esto es para borrar los campos que se han filtrado arriba y mostrar cuantos campos se han borrado.
			//DeleteResult resultado = col_libros.deleteMany(bsonFilter);
			//System.out.println("las filas borradas son: " +resultado);

			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
