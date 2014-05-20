package com.parsingServices;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class NameIdentifyingService {

	private static HashSet<String> surnamesSet;
	static {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					new File("")));
			String line = bufferedReader.readLine();
			while (line != null && !line.trim().equals("")) {
				surnamesSet.add(line);
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashSet<String> nameRegexIdentifier(ArrayList<String> wordsList) {
		HashSet<String> nameSet = new HashSet<String>();
		String name = "";
		for (int i = 0; i < wordsList.size(); i++) {
			if (wordsList
					.get(i)
					.matches(
							"[A-Z]([a-z]+|\\.)(?:\\s+[A-Z]([a-z]+|\\.))*(?:\\s+[a-z][a-z\\-]+){0,2}\\s+[A-Z]([a-z]+|\\.)")) {
				int j = i;
				while (wordsList
						.get(j)
						.matches(
								"[A-Z]([a-z]+|\\.)(?:\\s+[A-Z]([a-z]+|\\.))*(?:\\s+[a-z][a-z\\-]+){0,2}\\s+[A-Z]([a-z]+|\\.)")
						&& j <= i) {
					name = name + wordsList.get(j);
					j = j + 1;
					i = j;
				}
				nameSet.add(name);
			}

		}
		return nameSet;
	}

	public HashSet<String> NameSurnameIdentifier(ArrayList<String> wordsList) {
		String name = "";
		HashSet<String> nameSet = new HashSet<String>();
		for (int i = 0; i < wordsList.size(); i++) {
			if (surnamesSet.contains(wordsList.get(i)))
				;
			{
				int j = i;
				while (wordsList
						.get(i)
						.matches(
								"[A-Z]([a-z]+|\\.)(?:\\s+[A-Z]([a-z]+|\\.))*(?:\\s+[a-z][a-z\\-]+){0,2}\\s+[A-Z]([a-z]+|\\.)")
						&& j > 0) {

					{
						name = name + wordsList.get(i);
						j = j - 1;
					}
				}
				nameSet.add(name);
			}

		}
		return nameSet;
	}
}
