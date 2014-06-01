package automaticResumeParser.service.personal;

import java.util.List;

import automaticResumeParser.constants.ParserConstants;
import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.utilities.Utilities;

public class ParseEmailIdService {

	public ResumeSaveEntity parseEmailId(List<String> wordsList,
			ResumeSaveEntity resumeSaveEntity) {
		for (int i = 0; i < wordsList.size(); i++) {
			String word = wordsList.get(i);
			boolean flag = Utilities.emaildId(word);
			if (flag) {
				resumeSaveEntity.setEmailId(word);
			}
		}
		return resumeSaveEntity;

	}

}
