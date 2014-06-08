package automaticResumeParser.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import automaticResumeParser.utilities.Utilities;

public class ParserConstants {

	public static HashSet<String> surnames;
	public static HashSet<String> names;
	private static HashSet<String> englishWords;
	public static HashSet<String> nameMislead = new HashSet<String>();
	private static HashSet<String> places;
	
	static final String[] EmailVset = { "Email Address", "Email Id",
			"email Id", "E-mail", "e-mail", "email", "Email", "Mail" };
	static final String[] NameVset = { "Name-", "Name:-", "Name :", "Name:",
			"Name", "name" };
	final String[] InstituteVset = { "University", "Institute", "College",
			"CBSE Board", "C.B.S.E", "School", "ISC", "ICSE", "XII", "X",
			"12th", "10th", "Vidyalaya", "university", "University", "Board",
			"S.S.C", "H.S.C", "CBSE" };

	public static String path;
	public static  String Logspath;

	

	

	final String[] CompaniesKeyWordsSet = { "Inc.", "Limited",
			"Administrator ", "Intern ", "Internship", "Consultancy Services",
			"Technologies", "Corporation", "Laboratories", "Ltd" };

	final String[] designationkeyset = { "Team Lead", "Technical lead",
			"Senior Software Engineer", "Associate Software Engineer",
			"Assistant Software Engineer", "Software Engineer",
			"Business Analyst", "Assistant Consultant", "Associate Consultant",
			"Senior Executive Manager", "Business Head", "Charted Accountant",
			"DBA", "Database Administrator", "Configuration Manager",
			"President", "Senior Manager", "Project Manager",
			"Mechanical Engineer", "Rf Engineer", "Electrical Engineer",
			"Civil Engineer", "Project Engineer", "Branch Manager",
			"Tranmission Manager", "IT Manager", "Design Manager",
			"Technical Architect", "Manager Director", "Legal Counsel",
			"Software Developer", "Attorney", "Lawyer", "Architect", "Manager",
			"Engineer", "Clerk", "Front Desk" };

	static final String[] unwantedAdressWordsList = { "Current Address",
			"Home Address", "Permanent Address", "Address" };

	/**********************************************************************************************************************************/
	static {
		nameMislead.add("Father's");
		nameMislead.add("Fathers");
		nameMislead.add("Father");
		nameMislead.add("Mother's");
		nameMislead.add("Mother");
		nameMislead.add("Husband's");
		nameMislead.add("C\\/o");
		nameMislead.add("C\\/O");
		nameMislead.add("S/O");

	}

	/**********************************************************************************************************************************/

	public static HashSet<String> loadSurnames() {
		try {
			surnames = new HashSet<>();
			BufferedReader bf = new BufferedReader((new FileReader(new File(
					System.getProperty("user.dir")
							+ "\\property-files\\SortedSurnames.prs"))));
			String line = bf.readLine();
			while (line != null) {
				line = bf.readLine();
				surnames.add(line);
			}

			return surnames;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return surnames;
	}

	/**********************************************************************************************************************************/

	public static HashSet<String> loadNames() {

		try {
			names = new HashSet<String>();
			BufferedReader bf = new BufferedReader((new FileReader(new File(
					System.getProperty("user.dir")
							+ "\\property-files\\SortedNamescomposite.prs"))));
			String line = bf.readLine();
			while (line != null) {
				names.add(line);
				line = bf.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return names;
	}

	/**********************************************************************************************************************************/

	public static Set<String> loadEnglishWords() {

		try {
			englishWords = new HashSet<String>();
			BufferedReader bf = new BufferedReader(new FileReader(new File(
					System.getProperty("user.dir")
							+ "\\property-files\\words.prs")));
			String line = bf.readLine();
			while (line != null) {
				if (line.trim().length() != 0) {
					englishWords.addAll(Utilities.pastForm(line));
					englishWords.add(line);

				}
				line = bf.readLine();
			}
			return englishWords;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return englishWords;
	}

	/**********************************************************************************************************************************/
	public static Set<String> loadplaces() {

		try {
			places = new HashSet<String>();
			BufferedReader bf = new BufferedReader(new FileReader(new File(
					System.getProperty("user.dir")
							+ "\\property-files\\Places.prs")));
			String line = bf.readLine();
			while (line != null) {
				if (line.trim().length() != 0) {
					places.addAll(Utilities.pastForm(line));
					places.add(line);

				}
				line = bf.readLine();
			}
			return places;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return englishWords;
	}

	/**********************************************************************************************************************************/

	
	public static HashSet<String> getSurnames() {
		if (surnames != null) {
			return surnames;
		} else {
			loadSurnames();
		}
		return surnames;
	}

	/**********************************************************************************************************************************/

	public static HashSet<String> getEnglishWords() {
		if (englishWords != null) {
			return englishWords;
		} else {
			loadEnglishWords();
		}
		return englishWords;
	}

	/**********************************************************************************************************************************/

	public static String[] getUnwantedadresswordslist() {
		return unwantedAdressWordsList;
	}

	/**********************************************************************************************************************************/

	public static Set<String> getNamemislead() {
		return nameMislead;
	}

	/**********************************************************************************************************************************/

	public static Set<String> getAllnames() {

		return names;
	}

	/**********************************************************************************************************************************/

	public static String getPath() {
		return path;

	}
	/**********************************************************************************************************************************/

	public static void setPath(String mypath) {
		path=mypath;

	}
	/**********************************************************************************************************************************/

	
	public static void setLogspath(String logspath) {
		Logspath = logspath;
	}
	
	/**********************************************************************************************************************************/

	public static String getLogspath() {
		return Logspath;
	}
	
	/**********************************************************************************************************************************/

	public static String[] getverbSet() {

		return null;
	}

	/**********************************************************************************************************************************/

	public static String[] getAddressvset() {
		// TODO Auto-generated method stub
		return null;
	}

	/**********************************************************************************************************************************/

	public static Set<String> getPlaces() {
		if(places==null)
		{
			loadplaces();
		}else
		{
			return places;
		}
		return places;
	}
	/**********************************************************************************************************************************/

}
