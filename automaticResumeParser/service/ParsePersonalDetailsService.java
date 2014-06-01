package automaticResumeParser.service;

import java.util.List;

import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.execption.ParserException;
import automaticResumeParser.service.personal.ParseAddressService;
import automaticResumeParser.service.personal.ParseEmailIdService;
import automaticResumeParser.service.personal.ParseGenderOrMaritalService;
import automaticResumeParser.service.personal.ParseNameService;
import automaticResumeParser.service.personal.ParsePinOrPhoneService;
import automaticResumeParser.utilities.AddressUtility;

public class ParsePersonalDetailsService {

	public ResumeSaveEntity parsePersonalDetails(
			ResumeSaveEntity resumeSaveEntity, List<String> wordsList) throws ParserException {

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
		System.out.println(resumeSaveEntity.getSex());
		System.out.println(resumeSaveEntity.getEmailId());
		// ############################ PINCODE OR PHONE NUMBER
		// ##########################################
		ParsePinOrPhoneService pinOrPhoneService = new ParsePinOrPhoneService();
		// resumeSaveEntity = pinOrPhoneService.parsePinCodeOrPhoneNo(wordsList,
		// resumeSaveEntity);
		// ######################## FIND STATE
		// ##################################
		if (resumeSaveEntity.getPinCode() != null) {
			resumeSaveEntity.setState(AddressUtility.FindState(Integer
					.parseInt(resumeSaveEntity.getPinCode())));
		}
		// ######################################################################
		ParseAddressService addressService = new ParseAddressService();
		resumeSaveEntity = addressService.parseAddress(resumeSaveEntity,
				wordsList);
		return resumeSaveEntity;
	}
}
