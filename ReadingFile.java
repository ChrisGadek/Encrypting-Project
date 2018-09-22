package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*This file contains simple method to make operations on files.*/

public class ReadingFile{
	/*Inner private method to read information from a text file*/
	private static List<String> toReadFromFile(File file) throws FileNotFoundException {
		Scanner test = new Scanner(new BufferedReader(new FileReader(file)));
		List<String> lista = new ArrayList<>();
		while(test.hasNextLine()) {
			String s = test.nextLine();
			lista.add(s);
		}
	test.close();
		return lista;

	}
	/*Main method, which uses the previous method to return a whole information from reading file
	  into a simple String.*/
	public static String read(File file) throws FileNotFoundException {
		List<String> test = toReadFromFile(file);
		String wyjscie = "";
		for(String s: toReadFromFile(file)) {
			wyjscie += s;
		}
		return wyjscie;
	}
}