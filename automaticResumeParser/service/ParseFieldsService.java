package automaticResumeParser.service;

import java.util.List;

import automaticResumeParser.entity.ResumeSaveEntity;
public class ParseFieldsService {

	public ResumeSaveEntity parseFields(ResumeSaveEntity resumeSaveEntity,
			
			List<String> wordsList) {
		
		//########################## PERSONAL DETAILS ############################################		
		ParsePersonalDetailsService personalDetailsService = new ParsePersonalDetailsService();
		resumeSaveEntity=personalDetailsService.parsePersonalDetails(resumeSaveEntity,
				wordsList);
	/*	
		//########################### PROF QUALIFICATIONS ######################################		
		ParseEducationService educationService=new ParseEducationService();
		resumeSaveEntity=educationService.parseEducation(resumeSaveEntity, paragraphList, numericList);	 
		
		// ########################### EMPLOYMENT DETAILS ########################################## 
		ParseEmployerService employerService=new ParseEmployerService();
		resumeSaveEntity=employerService.employerData(resumeSaveEntity, numericList, paragraphList);

		// ######################################################################
		ParseProfessionalSkillsService professionalSkillsService=new ParseProfessionalSkillsService();
		resumeSaveEntity=professionalSkillsService.parseProfessionalSkills(paragraphList, resumeSaveEntity);
	
		*/
		return resumeSaveEntity; 
	}

}
