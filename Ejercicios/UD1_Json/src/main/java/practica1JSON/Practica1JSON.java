package practica1JSON;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Practica1JSON {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		JSONObject obj2 = null;
		JSONArray array;
		
		try {
			
			System.out.println("------------- 1. Obten el nombre de todos los productos ----------------");
			
			JSONArray jArray = (JSONArray) parser.parse(new FileReader ("ficheros/productos.json"));
			
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				System.out.println("Nombre de los productos --> " + obj.get("nombre_producto"));
			}
			
			
			System.out.println();
			System.out.println("------------- 2. Obten el nombre y el precio de los productos de la tipo <<Ropa>> ----------------");
			
			for (Object msg : jArray) {
				obj = (JSONObject) msg;
				if (obj.get("tipo_producto").equals("Ropa")) {
					System.out.println("Nombre --> " +obj.get("nombre_producto") + " - Precio --> " + obj.get("precio"));
				}
			}
			
			
			System.out.println();
			System.out.println("------------- 3. Mostrar toda la información del Pantalones Levi's 501 ----------------");
		
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				if (obj.get("nombre_producto").equals("Pantalones Levi's 501")) {
					System.out.println("Información --> " +obj.toString());
				}
			}
			
			
			System.out.println();
			System.out.println("------------- 4. Mostrar el nombre y el stock de los productos que están en el <<Almacén 1>> y que su precio es inferior a 15 ----------------");
			
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				Double precioReal = (Double) obj.get("precio");
				JSONObject ubicacion = (JSONObject) obj.get("ubicacion");

				if (ubicacion.get("almacen").equals("Almacén 1") && precioReal < 200 ) {	
					System.out.println("Nombre --> " + obj.get("nombre_producto") + " - Stock --> " + obj.get("stock"));
				}
				
			}
			
			
			System.out.println();
			System.out.println("------------- 5. Mostrar el nombre y la ubicación completa de los productos con un stock menor a 50 ----------------");
		
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				Long stock = (Long) obj.get("stock");
				
				if (stock < 50) {
					System.out.println("Nombre --> " + obj.get("nombre_producto") + " - Ubicación --> " + obj.get("ubicacion"));
				}
			}
			
			
			System.out.println();
			System.out.println("------------- 6. Mostrar los productos nombre y el tipo de producto que están en el Pasillo 3 del Almacen 3 ----------------");
		
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				JSONObject ubicacion = (JSONObject) obj.get("ubicacion");

				if (ubicacion.get("pasillo").equals("Pasillo 3") && ubicacion.get("almacen").equals("Almacén 3") ) {	
					System.out.println("Nombre --> " + obj.get("nombre_producto") + " - Stock --> " + obj.get("stock"));

				}
				

			}
			
			
			System.out.println();
			System.out.println("------------- 7. Mostrar cuantos productos hay de la Categoria Deportes ----------------");
		
			Integer contador = 0;
			
			for (Object msg: jArray) {
				obj = (JSONObject) msg;
				JSONArray categorias = (JSONArray) obj.get("categorias");
				for (Object resultados : categorias) {
					if (resultados.equals("Deportes")) {
						System.out.println("Productos -->"  + obj.get("nombre_producto"));
						contador ++;
					}
					
				}
				
			}
			
			System.out.println("Hay " +contador+ " productos de la categoria Deportes");
			
			
			System.out.println();
			System.out.println("------------- 8. Pide los datos de un nuevo producto al usuario e insertalo en el documentos ----------------");
			
			/*
			
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Introduce el nombre del producto: ");
			String nombreProducto = sc.next();
			
			System.out.print("Introduce el precio del producto: ");
			Integer precioProducto = sc.nextInt();
			
			System.out.print("Introduce la primera categoría: ");
			String categoriaUno = sc.next();
			
			System.out.print("Introduce la segunda categoría: ");
			String categoriaDos = sc.next();
			
			System.out.print("Introduce el stock del producto: ");
			String stock = sc.next();
			
			System.out.print("Introduce la ubicación del almacén del producto: ");
			String ubiAlmacen = sc.next();
			
			System.out.print("Introduce la ubicación de la estantería del producto: ");
			String ubiEstanteria = sc.next();
			
			System.out.print("Introduce la ubicación del pasillo del producto: ");
			String ubiPasillo = sc.next();
			
			System.out.print("Introduce el tipo de producto: ");
			String tipoProducto = sc.next();
			
			JSONArray aCategorias = new JSONArray();
			aCategorias.add(categoriaUno);
			aCategorias.add(categoriaDos);
			
			JSONObject oUbicacion = new JSONObject();
			oUbicacion.put("almacen", ubiAlmacen);
			oUbicacion.put("estanteria", ubiEstanteria);
			oUbicacion.put("pasillo", ubiPasillo);

			JSONObject documento = new JSONObject();
			documento.put("nombre_producto", nombreProducto);
			documento.put("precio", precioProducto);
			documento.put("categorias", aCategorias);
			documento.put("stock", stock);
			documento.put("ubicacion", oUbicacion);
			documento.put("tipo_producto", tipoProducto);
			
			jArray.add(documento);
			
			FileWriter file = new FileWriter(("ficheros/productos.json"));
			
			file.write(jArray.toJSONString());
			
			file.close();
			
			*/
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}

}
