package automaticResumeParser.service.personal;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import automaticResumeParser.constants.ParserConstants;
import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.execption.ParserException;
import automaticResumeParser.utilities.Utilities;

public class ParseNameService {

	/**
	 * @throws ParserException
	 ************************************************************************************************************************/
	public ResumeSaveEntity parseName(List<String> list,
			ResumeSaveEntity resumeSaveEntity) throws ParserException {
		System.out.println(System.getProperty("user.dir"));
	//	Set<String> allNames = ParserConstants.getAllnames();
		Set<Integer> misLeadWordsSet = misleadWordsPosition(list);
		// HashMap<String, Integer> nameMap1 = nameRegexIdentifier(list);
		HashMap<String, Integer> nameMap = nameSurnameIdentifier(list);
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

		System.out.println(nameSet);
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
	 ************************************************************************************************************************/

	private HashMap<String, Integer> nameSurnameIdentifier(List<String> list)
			throws ParserException {

		HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
		HashSet<String> surnameSet = ParserConstants.getSurnames();
		for (int i = 0; i < list.size(); i++) {
			String val = list.get(i);
			String name = "";			
			if (val.trim().length() > 0 && surnameSet.contains(val)
					|| surnameSet.contains(Utilities.properCase(val))
					|| surnameSet.contains(val.toUpperCase())
					|| surnameSet.contains(val.toLowerCase())) {
				int j = i;
				while (val.matches("[A-Za-z]+") && j >= 0
						&& !(isEnglishWord(val))) {

					{
						name = val + " " + name; // To maintain the space
													// between two words
						j = j - 1;
						if (j >= 0) {
							val = list.get(j);
						}
					}
				}
				nameMap.put(name.trim(), j);
			}

		}
		return nameMap;
	}

	/**
	 * @throws ParserException
	 * @throws IOException
	 **********************************************************************************************************************************/

	private boolean isEnglishWord(String val) throws ParserException {
		
			Set<String> wordSet = ParserConstants.getEnglishWords();// Utilities.englishWords();
			return wordSet.contains(val.toUpperCase());
		
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
