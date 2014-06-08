package automaticResumeParser.service.personal;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import automaticResumeParser.constants.ParserConstants;
import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.utilities.Utilities;

public class ParseNameService {

	/**
	 * @throws ParserException
	 ************************************************************************************************************************/
	public ResumeSaveEntity parseName(List<String> list,
			ResumeSaveEntity resumeSaveEntity){
		// System.out.println(System.getProperty("user.dir"));
		// Set<String> allNames = ParserConstants.getAllnames();
		Set<Integer> misLeadWordsSet = misleadWordsPosition(list);
		// HashMap<String, Integer> nameMap1 = nameRegexIdentifier(list);
		HashMap<String, Integer> nameMap = nameIdentifier(list);
		// nameMap.putAll(nameMap1);
		Set<String> nameSet = nameMap.keySet();
		// nameSet.retainAll(allNames); // intersection of two sets

		// Reject the names which comes under the misleading words------- Start
		Iterator<String> nameIterator = nameSet.iterator();
		while (nameIterator.hasNext()) {

			String val = nameIterator.next();
			Integer wordNo = nameMap.get(val);
			Iterator<Integer> misLeadIterator = misLeadWordsSet.iterator();
			while (misLeadIterator.hasNext()) {
				Integer misleadWordNo = misLeadIterator.next();
				if (misleadWordNo - wordNo <= 2 && (misleadWordNo - wordNo > 0)) {
					nameMap.remove(wordNo);
				}
			}
		}
		// Reject the names which comes under the misleading words------- End //		

		// Detect on basis of sorenson coefficient Start //

		nameSet = nameMap.keySet();
		nameIterator = nameSet.iterator();
		String name = ""; // This will contain the final name having highest
							// sorenson coefficient value
		Double maxSorensonVal = -1.0D; // This will contain the final name
										// having highest sorenson coefficient
										// value
		String emailId = resumeSaveEntity.getEmailId();
		System.out.println("emailId " + emailId);
		// here we will find the value with highes sorenson coeff and then set
		// it into name
		while (nameIterator.hasNext()) {
			String val = nameIterator.next();

			double sorensonCoef = Utilities.getSorensonCoefficient(val,
					emailId.substring(0, emailId.indexOf("@")));

			if (sorensonCoef > maxSorensonVal) {
				maxSorensonVal = sorensonCoef;
				name = val;
			}
		}
		// Detect on basis of sorenson coefficient End //

		System.out.println("name is " + name);
		return resumeSaveEntity;
	}

	

	/**************************************************************************************************************************/

	public HashMap<String, Integer> nameRegexIdentifier(List<String> wordsList) {
		HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
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
				nameMap.put(name, j);
			}

		}
		return nameMap;
	}

	/**
	 * @throws ParserException
	 * @throws IOException
	 *             This method use composite algorithm to find the name 1) It
	 *             uses regex 2) it uses the English Word removal 3) it uses
	 *             place removal technique
	 ************************************************************************************************************************/

	private HashMap<String, Integer> nameIdentifier(List<String> list)
			 {

		HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
		HashSet<String> surnameSet = ParserConstants.getSurnames();
		for (int i = 0; i < list.size(); i++) {
			String val = list.get(i);
			String name = "";
			if (val.trim().length() > 0 && surnameSet.contains(val)
					|| surnameSet.contains(Utilities.properCase(val))) {
				int j = i;
				while (val.matches("[A-Za-z]+") && j >= 0
						&& !(isEnglishWord(val)) && !(isAPlace(val))) {

					{
						name = val + " " + name; // To maintain the space
													// between two words
						j = j - 1;
						if (j >= 0) {
							val = list.get(j);
						}
					}
				}
				// The length should be greater than zero and name should not be
				// already present like Anil and Anil kumar
				String trimmedName = name.trim();
				if (trimmedName.length() > 0) {
					Iterator<String> iterTemp = nameMap.keySet().iterator();
					while (iterTemp.hasNext()) {
						String mapVal = iterTemp.next();
						if (trimmedName.length()>mapVal.length()  && trimmedName.contains(mapVal)) 
						{ 
							iterTemp.remove();								
							
						}else if(mapVal.length()>trimmedName.length() && mapVal.contains(trimmedName))
						{
							trimmedName=mapVal;   //Now new value can not be added because mapVal is already added val
						}
					}
					nameMap.put(trimmedName, j);
				//	System.out.println(nameMap.keySet());
				}
			}

		}
		return nameMap;
	}

	/**
	 * @throws ParserException
	 * @throws IOException
	 **********************************************************************************************************************************/

	private boolean isAPlace(String val) {
		Set<String> placeSet = ParserConstants.getPlaces();// Utilities.places();
		return placeSet.contains(val);
	}

	/**
	 * @throws ParserException
	 * @throws IOException
	 **********************************************************************************************************************************/

	private boolean isEnglishWord(String val) {

		Set<String> wordSet = ParserConstants.getEnglishWords();// Utilities.englishWords();
		return wordSet.contains(val);

	}

	/************************************************************************************************************************************/

	/**
	 * @return Set This method returnd the position of misleading words
	 * **/
	public Set<Integer> misleadWordsPosition(List<String> list) {
		Set<String> misleadWords = ParserConstants.getNamemislead();
		Set<Integer> misleadWordsPosition = new HashSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (misleadWords.contains(list.get(i))
					&& misleadWords.contains(Utilities.properCase(list.get(i)))) {
				misleadWordsPosition.add(i);
			}

		}
		return misleadWordsPosition;
	}

}
