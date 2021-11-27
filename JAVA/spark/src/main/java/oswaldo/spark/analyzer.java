package oswaldo.spark;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class analyzer {
	
	static List<List<String>> diccionario;
	
	public static List<List<String>> generateDictionaries() throws FileNotFoundException {
		
		System.out.println("****************************************");
		System.out.println("GENERANDO DICCIONARIOS DE PALABRAS");
		System.out.println("****************************************");
		
		String positivos = "positive-words.txt";
		String negativos = "negative-words.txt";
		
		Scanner sc = new Scanner(new File(positivos));
		List<String> dictPositivo = new ArrayList<String>();
		while (sc.hasNextLine()) {
			dictPositivo.add(sc.nextLine());
		}
		
		Scanner sc2 = new Scanner(new File(negativos));
		List<String> dictNegativo = new ArrayList<String>();
		while (sc2.hasNextLine()) {
			dictNegativo.add(sc2.nextLine());
		}
		
		
		
		
		List<List<String>> diccionario = new ArrayList<List<String>>();
		
		diccionario.add(dictPositivo);
		diccionario.add(dictNegativo);
		
		
		return diccionario;
		
	}
	
	public float analize(String lyrics) {
		
		String simbolos = ",.?!()";
		
		for (int i = 0; i < simbolos.length(); i++) {
			String str = "\\" + simbolos.charAt(i);
			lyrics = lyrics.replaceAll(str, "");
            
        }
		
		String[] palabras = lyrics.split(" ");
		
		int puntosPositivos = 0;
		int puntosNegativos = 0;
		int puntosTotales = 0;
		
		for (int i = 0; i < palabras.length; i++) {
			String palabra = palabras[i];
			palabra = palabra.toLowerCase();
			
			
			
			//positivo
			if (diccionario.get(0).contains(palabra)) {
				puntosPositivos++;
				puntosTotales++;
			}
			
			//negativo
			if (diccionario.get(1).contains(palabra)) {
				puntosNegativos++;
				puntosTotales++;
			}
			
        }
		
		float puntuacion = 0;
		if (puntosTotales != 0) {
			puntuacion = (puntosPositivos-puntosNegativos)/(float)puntosTotales;
		}
		
		
		
		return puntuacion;
	}
	
	public analyzer() throws FileNotFoundException {
			diccionario = generateDictionaries();
			System.out.println("****************************************");
			System.out.println(" DICCIONARIOS GENERADOS!");
			System.out.println("****************************************");
		}

}


