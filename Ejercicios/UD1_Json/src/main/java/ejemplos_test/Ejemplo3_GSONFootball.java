package ejemplos_test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class Ejemplo3_GSONFootball {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<FootballPlayer> jugador = Arrays.asList(new FootballPlayer(13, "Jaume Domech", Arrays.asList("portero"), "Valencia CF"), 
				new FootballPlayer(15, "César Tárrega", Arrays.asList("Defensa central", "central"), "Valencia CF"));
		
		String json = new Gson().toJson(jugador);
		System.out.println(json);
		
		try {
			
			FileWriter fw = new FileWriter("ficheros/FootballJSON.json");
			fw.write(json);
			fw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
