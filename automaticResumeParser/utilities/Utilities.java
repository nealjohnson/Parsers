package automaticResumeParser.utilities;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import automaticResumeParser.constants.ParserConstants;

public class Utilities {

	/************************************************************************************************************************************/

	public static List<Long> sortedNumbersList(List<String> paragraphs) {
		List<Long> final_list = new ArrayList<Long>();
		for (int i = 0; i < paragraphs.size(); i++) {
			List<Long> list = returnNumberList((String) paragraphs.get(i));
			Iterator<Long> iterator = list.iterator();
			while (iterator.hasNext()) {
				Long insert = iterator.next();
				if (final_list.contains(insert)) {

				} else {
					final_list.add(insert);
				}
			}
		}
		Collections.sort(final_list);
		return final_list;
	}

	/************************************************************************************************************************************/
	public static List<Long> returnNumberList(String value) {
		List<Long> list = new ArrayList<Long>();
		Pattern intsOnly = Pattern.compile("\\d+");
		Matcher makeMatch = intsOnly.matcher(value);
		while (makeMatch.find()) {
			String inputInt = makeMatch.group();
			if ((list.contains(inputInt))) {
			} else {
				list.add(Long.parseLong(inputInt));
			}
		}
		return list;
	}

	/************************************************************************************************************************************/
	public static boolean stringContainsInteger(String value) {

		if (Pattern.matches("[\\D]+", value)) {

			return false;
		}

		return true;

	}

	/************************************************************************************************************************************/

	public static boolean emaildId(String value) {

		if (value.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$") == true) {

			return true;
		}

		return false;

	}

	/************************************************************************************************************************************/
	public static List<Integer> getyears(List<Long> numberList) {
		List<Integer> returnList = new ArrayList<Integer>();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int size = numberList.size();
		for (int i = 0; i < size; i++) {
			Long number = (Long) numberList.get(i);
			if ((number > 1980) && (number <= year)) {
				returnList.add(Integer.parseInt(numberList.get(i).toString()));
			}
		}
		Collections.sort(returnList);

		return returnList;
	}

	/************************************************************************************************************************************/
	public static List<Integer> getPercentage(List<Long> numberList) {
		List<Integer> returnList = new ArrayList<Integer>();
		;
		int size = numberList.size();
		for (int i = 0; i < size; i++) {
			Long number = (Long) numberList.get(i);
			if ((number > 30) && (number <= 100)) {
				returnList.add(Integer.parseInt(numberList.get(i).toString()));
			}
		}
		Collections.sort(returnList);

		return returnList;
	}

	/************************************************************************************************************************************/
	public static String properCase(String value) {
		value = value.replaceAll("\\s+", " ");
		char[] charArray = value.toCharArray();
		for (int i = 0; i < charArray.length - 1; i++) {
			if (i == 0) {
				charArray[i] = Character.toUpperCase(charArray[i]);
			}
			if (charArray[i] == ' ') {
				charArray[i + 1] = Character.toUpperCase(charArray[i + 1]);
			}
		}
		String result = new String(charArray);
		return result;
	}

	/************************************************************************************************************************************/
	public static String writeToLog(Exception exception, String Info) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.print(" [ ");
		pw.print(exception.getClass().getName());
		pw.print(" ] ");
		pw.print(exception.getMessage());
		exception.printStackTrace(pw);
		File file = new File(ParserConstants.getLogspath()+"Log.txt");
		String date = getSysDate();
		try {
			if (file.exists()) {
				file.createNewFile();

				String old_Data = FileUtils.readFileToString(file);
				String new_data = old_Data + "\n" + "Resume :" + Info + " "
						+ date + " " + sw.toString();
				FileUtils.writeStringToFile(file, new_data);
			} else {
				FileUtils.writeStringToFile(file, "Resume :" + Info + " "
						+ date + " " + sw.toString());
			}
		} catch (Exception e) {
			return "FatalException";
		}
		return "success";
	}

	/************************************************************************************************************************************/

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{2,})$";

	public static boolean validateEmailId(final String hex) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	/************************************************************************************************************************************/

	public static String getSysDate() {

		String date;
		Calendar cal = new GregorianCalendar();

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int Min = cal.get(Calendar.MINUTE);
		date = "" + year + ":" + (month + 1) + ":" + day + ":" + hour + ":"
				+ Min;

		return date;
	}

	/************************************************************************************************************************************/
	public static String FindExtension(String resumeName) {
		String extension = "";
		int length = resumeName.length();
		int p = 0;
		for (int j = 0; j < length; j++) {
			if (resumeName.charAt(j) == '.') {
				p = j;
			}
		}

		if (p != 0) {
			extension = resumeName.substring(p + 1, length);
		}
		return extension;

	}

	/**
	 * @return
	 **********************************************************************************************************************************/

	public static Set<String> pastForm(String word) {
		String past = "";
		String presentContinuous = "";
		Set<String> pastSet = new HashSet<String>();
		String lastChar = Character.toString(word.charAt(word.length() - 1));
		String secondlastChar = Character
				.toString(word.charAt(word.length() - 1));

		if ((lastChar.equalsIgnoreCase("e") || lastChar.equalsIgnoreCase("y") || lastChar
				.equalsIgnoreCase("o")) && secondlastChar.equalsIgnoreCase("e")) {
			presentContinuous = word + "ing";
			past = word + "d";
		} else if ((!isVowel(lastChar)) && isVowel(secondlastChar)) {
			past = word + lastChar + "ed";
			presentContinuous = word + lastChar + "ing";
		} else if (lastChar.equalsIgnoreCase("e")) {
			presentContinuous = word.subSequence(0, word.length() - 2) + "ing";
			past = word.subSequence(0, word.length() - 1) + "d";
		}
		pastSet.add(past);
		pastSet.add(presentContinuous);
		return pastSet;
	}

	/************************************************************************************************************************************/
	private static boolean isVowel(String val) {
		return val.equalsIgnoreCase("a") || val.equalsIgnoreCase("e")
				|| val.equalsIgnoreCase("i") || val.equalsIgnoreCase("o")
				|| val.equalsIgnoreCase("u");
	}
	
	
	/************************************************************************************************************************************/
	
	/** @return an array of adjacent letter pairs contained in the input string */

	private static String[] letterPairs(String str) {
		str=str.trim();
		int numPairs = str.length() - 1;

		String[] pairs = new String[numPairs];

		for (int i = 0; i < numPairs; i++) {

			pairs[i] = str.substring(i, i + 2);

		}

		return pairs;

	}

	/** @return an ArrayList of 2-character Strings. */

	private static ArrayList<String> wordLetterPairs(String str) {

		ArrayList<String> allPairs = new ArrayList<String>();

		// Tokenize the string and put the tokens/words into an array
		str=str.trim();
		str=str.replaceAll("//S+", " ");		;
		String[] words = str.split("\\s");

		// For each word

		for (int w = 0; w < words.length; w++) {

			// Find the pairs of characters

			String[] pairsInWord = letterPairs(words[w]);

			for (int p = 0; p < pairsInWord.length; p++) {

				allPairs.add(pairsInWord[p]);

			}

		}

		return allPairs;

	}

	/** @return lexical similarity value in the range [0,1] */

	public static double getSorensonCoefficient(String str1, String str2) {

		ArrayList<String> pairs1 = wordLetterPairs(str1.toUpperCase());

		ArrayList<String> pairs2 = wordLetterPairs(str2.toUpperCase());

		int intersection = 0;
		
	//	System.out.println(pairs1);
		
	//	System.out.println(pairs2);

		int union = pairs1.size() + pairs2.size();

		for (int i = 0; i < pairs1.size(); i++) {

			Object pair1 = pairs1.get(i);

			for (int j = 0; j < pairs2.size(); j++) {

				Object pair2 = pairs2.get(j);

				if (pair1.equals(pair2)) {

					intersection++;

					pairs2.remove(j);

					break;

				}

			}

		}

		return (2.0 * intersection) / union;

	}
	/************************************************************************************************************************************/
	
	
}
