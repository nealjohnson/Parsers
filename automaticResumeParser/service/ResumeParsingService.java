package automaticResumeParser.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import automaticResumeParser.constants.ParserConstants;
import automaticResumeParser.entity.ResumeSaveEntity;
import automaticResumeParser.utilities.Utilities;

public class ResumeParsingService {

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 ***************************************************************************************************************/

	public ResumeSaveEntity parseResumes(String resumeName) {
		String extension = null;

		extension = Utilities.FindExtension(resumeName);
		System.out.println(resumeName);

		ResumeSaveEntity resumeSaveEntity=new ResumeSaveEntity();
		if (extension.equalsIgnoreCase("doc")) {
			try {
				resumeSaveEntity = parseDocResume(resumeName);
			} catch (Exception exception) {
				Utilities.writeToLog(exception, resumeName);

			}

		} else if (extension.equalsIgnoreCase("docx")) {
			try {
				resumeSaveEntity = parseDOCXResume(resumeName);
			} catch (Exception exception) {
				Utilities.writeToLog(exception, resumeName);

			}

		} else if (extension.equalsIgnoreCase("pdf")) {
			try {
				resumeSaveEntity = parsePDFResume(resumeName);
			} catch (Exception exception) {
				Utilities.writeToLog(exception, resumeName);

			}

		}
		return resumeSaveEntity;

	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InvalidFormatException
	 * 
	 ******************************************************************************************************************************************/

	private ResumeSaveEntity parseDOCXResume(String resumeName)
			throws IOException, InvalidFormatException {

		ResumeSaveEntity resumeSaveEntity;
		File file = new File(ParserConstants.getPath() + resumeName);
		OPCPackage opcPackage;

		opcPackage = OPCPackage.openOrCreate(file);

		XWPFDocument docx;

		docx = new XWPFDocument(opcPackage);

		XWPFWordExtractor wx = new XWPFWordExtractor(docx);

		Scanner scanner = new Scanner(wx.getText());

		resumeSaveEntity = parseResumeScanner(scanner);
		return resumeSaveEntity;

	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 
	 ******************************************************************************************************************************************/

	private ResumeSaveEntity parsePDFResume(String resumeName)
			throws FileNotFoundException, IOException {
		ResumeSaveEntity resumeSaveEntity;
		PDFParser parser;
		PDFTextStripper pdfStripper;
		PDDocument pdDoc;
		COSDocument cosDoc;
		File file = new File(ParserConstants.getPath() + resumeName);
		parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		Scanner scanner = new Scanner(pdfStripper.getText(pdDoc));

		resumeSaveEntity = parseResumeScanner(scanner);
		return resumeSaveEntity;
	}

	/**
	 * @throws IOException
	 * @throws FileNotFoundException
	 ***************************************************************************************************************/
	private ResumeSaveEntity parseDocResume(String resumeName)
			throws FileNotFoundException, IOException {

		ResumeSaveEntity resumeSaveEntity;

		POIFSFileSystem fileSytem = null;

		ParserConstants.getPath();
		fileSytem = new POIFSFileSystem(new FileInputStream(
				ParserConstants.getPath() + resumeName));
		HWPFDocument doc = new HWPFDocument(fileSytem);
		WordExtractor we = new WordExtractor(doc);

		Scanner scanner = new Scanner(we.getText());
		resumeSaveEntity = parseResumeScanner(scanner);
		return resumeSaveEntity;
	}

	/**
	 *********************************************************************************************************************/

	ResumeSaveEntity parseResumeScanner(Scanner scanner) {
		ParseFieldsService fieldsService = new ParseFieldsService();
		List<String> wordsList = new ArrayList<String>();
		ResumeSaveEntity resumeSaveEntity = new ResumeSaveEntity();
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			data=data.toUpperCase();
			data = data.replaceAll(":", " ");
			data = data.replaceAll(";", " ");
			data = data.replaceAll(",", " ");
			data = data.replaceAll("\\s+", " ");
			
			String[] words = data.split(" ");			
			
			wordsList.addAll(Arrays.asList(words));
	
		}
		resumeSaveEntity = fieldsService.parseFields(resumeSaveEntity,
				wordsList);
		String date = Utilities.getSysDate();
		resumeSaveEntity.setInsertionDate(date);

		return resumeSaveEntity;

	}

	/***********************************************************************************************************************/

	void verbEntityData(List paragraphList) {
		String[] verbSet = ParserConstants.getverbSet();
		String val = "";
		Iterator iterator = paragraphList.iterator();

		while (iterator.hasNext()) {
			val = (String) iterator.next();
			for (String verbSetval : verbSet) {
				;

			}
		}
	}

}
