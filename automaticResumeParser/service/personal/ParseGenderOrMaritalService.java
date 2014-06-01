package automaticResumeParser.service.personal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import automaticResumeParser.constants.ParserConstants;
import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.utilities.Utilities;

public class ParseGenderOrMaritalService {
	public ResumeSaveEntity parseGenderOrMarital(
			List<String> alphabeticList, ResumeSaveEntity resumeSaveEntity) {

		String[] rlnKeywords = { "Single", "Unmarried", "Divorced", "Married",
				"Widowed" };
		String[] sexKeywords = { "Female", "Male" };
		Integer gender_token = 0;
		Integer marital_token = 0;
		Iterator<String> iterator = alphabeticList.iterator();
		outerloopRln: while (iterator.hasNext()) {
			final String value = (String) iterator.next();

			// ################################ for marital status
			// ##################################
			if (marital_token == 0) {
				InnerLoopForMaritalStatus: for (String keyword : rlnKeywords) {
					if (value.contains(keyword)
							|| value.contains(keyword.toLowerCase())
							|| value.contains(keyword.toUpperCase())) {
						resumeSaveEntity.setRelationShip(keyword);
						marital_token = 1;
						break InnerLoopForMaritalStatus;
					}
				}
			}

			// ################################ for gender status
			// ##################################
			if (gender_token == 0) {
				InnerLoopForGender: for (String keyword : sexKeywords) {
					if (value.contains(keyword)
							|| value.contains(keyword.toLowerCase())
							|| value.contains(keyword.toUpperCase())) {
						resumeSaveEntity.setSex(keyword);
						gender_token = 1;
						break InnerLoopForGender;
					}
				}
			}
						
			if (gender_token == 1 && marital_token == 1) {
				
				break outerloopRln;
			}
		}
		return resumeSaveEntity;

	}
}
