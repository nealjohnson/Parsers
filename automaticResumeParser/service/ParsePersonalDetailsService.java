package automaticResumeParser.service;

import java.util.List;

import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.service.personal.ParseEmailIdService;
import automaticResumeParser.service.personal.ParseGenderOrMaritalService;
import automaticResumeParser.service.personal.ParseNameService;

public class ParsePersonalDetailsService {

	public ResumeSaveEntity parsePersonalDetails(
			ResumeSaveEntity resumeSaveEntity, List<String> wordsList) {

		// ########################## GENDER AND MARITAL STATUS
		// ####################################
		ParseGenderOrMaritalService service = new ParseGenderOrMaritalService();
		resumeSaveEntity = service.parseGenderOrMarital(wordsList,
				resumeSaveEntity);

		// ####################### E-MAIL ID ###################################
		ParseEmailIdService emailIdService = new ParseEmailIdService();
		resumeSaveEntity = emailIdService.parseEmailId(wordsList,
				resumeSaveEntity);
		// ########################## NAME ####################################
		ParseNameService nameService = new ParseNameService();
		resumeSaveEntity = nameService.parseName(wordsList, resumeSaveEntity);
	
		return resumeSaveEntity;
	}
}
