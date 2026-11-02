package ejemplos_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

public class Ejemplo4_FootballPlayerFrom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File archivo = null;
		FileReader reader = null;
		BufferedReader br = null;
		String linea, jsonAll = new String();
		
		try {
			
			reader = new FileReader("ficheros/FootballPlay.json");
			br = new BufferedReader(reader);
			
			while((linea = br.readLine()) != null) {
				
				jsonAll = jsonAll + linea;
				
			}
			
			Gson gson = new Gson();
			FootballPlayer[] footballPlayers = gson.fromJson(jsonAll, FootballPlayer[].class);
			
			for (FootballPlayer footballPlayer : footballPlayers) {
				System.out.println("Jugador --> " +footballPlayer.toString()+ " -||- Nombre --> " +footballPlayer.getName());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
