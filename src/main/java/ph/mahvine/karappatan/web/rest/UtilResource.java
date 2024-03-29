package ph.mahvine.karappatan.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.mahvine.karappatan.domain.Annex;
import ph.mahvine.karappatan.domain.Answer;
import ph.mahvine.karappatan.domain.Module;
import ph.mahvine.karappatan.domain.Question;
import ph.mahvine.karappatan.domain.Recommendation;
import ph.mahvine.karappatan.repository.AnnexRepository;
import ph.mahvine.karappatan.repository.AnswerRepository;
import ph.mahvine.karappatan.repository.ModuleRepository;
import ph.mahvine.karappatan.repository.QuestionRepository;
import ph.mahvine.karappatan.repository.RecommendationRepository;

@RestController
@RequestMapping("/api/util")
public class UtilResource {
	
	private static Logger logger = LoggerFactory.getLogger(UtilResource.class);
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnnexRepository annexRepository;
	
	@Autowired
	RecommendationRepository recommendationRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	ModuleRepository moduleRepository;
	
	static boolean running = false;

	@GetMapping("/provisionData")
	public void provisionData() throws IOException {
		if(!running) {
			Module module = moduleRepository.getOne(1L);
			String karappatanExcel = "Karappatan.xlsx";
			
			try {
				running = true;
				loadQuestions(false, karappatanExcel, module);
				loadRecommendations(false, karappatanExcel, module);
				loadAnnexes(false, karappatanExcel, module);
				loadQuestions(true, karappatanExcel, module);
				loadRecommendations(true, karappatanExcel, module);
				loadAnnexes(true, karappatanExcel, module);
			} finally {
				running = false;
			}
		} else {
			logger.error("Upload excel file currently running");
		}
	}
	

	@GetMapping("/provisionIndigencyTest")
	public void provisionDataIndigencyTest() throws IOException {
		if(!running) {
			Module module = moduleRepository.getOne(2L);
			String excelFile = "Indigency-Test-v2.xlsx";
			
			try {
				running = true;
				loadQuestions(false, excelFile, module);
				loadRecommendations(false, excelFile, module);
				loadQuestions(true, excelFile, module);
				loadRecommendations(true, excelFile, module);
			} finally {
				running = false;
			}
		} else {
			logger.error("Upload excel file currently running");
		}
	}
	
	
	
	
		
	private void loadQuestions(boolean loadReferences,String filename, Module module) {
		
		logger.info("Question Present:"+questionRepository.findOneByIdentifier("Q1").isPresent());
		
	    try {
	    	FileInputStream fileInputStream = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                if(currentRow.getRowNum() <2) {
                	currentRow = iterator.next();
                }
                
                int i = 0;

            	String questionId = null;
                if(currentRow.getCell(i) != null) {
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	questionId =  currentRow.getCell(i).getStringCellValue().isEmpty()?null:currentRow.getCell(i).getStringCellValue();
                	i++;
                }
                String questionContent = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
                String questionInfo = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;

                logger.debug("Row:{},i:{}, ID:{}, question:{}",currentRow.getRowNum(),i, questionId, questionContent);
                
                if(questionContent != null && questionId != null) {
	                Question question = new Question();
	                question.identifier(questionId);
	                question.module(module);
	                Optional<Question> optQuestion = questionRepository.findOneByIdentifier(questionId);
	                if(optQuestion.isPresent()) {
	                	question = optQuestion.get();
	                	
	                }
	                question.question(questionContent).info(questionInfo);
	                questionRepository.save(question);
	                //answer 1
	                int aCtr = 0;
	                List<Answer> existingAnswers = answerRepository.findByQuestionId(question.getId());
	                do {
		                String answerValue = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
		                String answerInfo = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
		                String answerNextQuestion = null;
		                if(currentRow.getCell(i) != null) {
		                	currentRow.getCell(i).setCellType(CellType.STRING);
		                	answerNextQuestion =  currentRow.getCell(i).getStringCellValue().isEmpty()?null:currentRow.getCell(i).getStringCellValue();
		                	i++;
		                }
		                String answerRecommendation = null;
		                if(currentRow.getCell(i) != null) {
		                	currentRow.getCell(i).setCellType(CellType.STRING);
		                	answerRecommendation =  currentRow.getCell(i).getStringCellValue().isEmpty()?null:currentRow.getCell(i).getStringCellValue();
		                	i++;
		                }
		                String answerAnnex = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
		                Answer answer = new Answer();
		                if(answerValue!=null) {		                	
		                	if(aCtr < existingAnswers.size() && existingAnswers.size()>0) {
		                		answer = existingAnswers.get(aCtr);
		                	}
		                	answer.answer(answerValue).instructions(answerInfo).question(question);
		                	
		                	if(loadReferences) {
		                		Question nextQuestion = null;
		                		if(answerNextQuestion!=null && !answerNextQuestion.trim().isEmpty() && !answerNextQuestion.trim().equals("?")) { 
		                			logger.debug("question:{}",answerNextQuestion);
		                			nextQuestion = questionRepository.findOneByIdentifier(answerNextQuestion.trim()).orElse(null);	 
		                			answer.nextQuestion(nextQuestion);
		                		}
		                		Recommendation recommendation = null;
		                		if(answerRecommendation!=null && !answerRecommendation.trim().isEmpty()) {
		                			logger.debug("Answer Recommendation:"+answerRecommendation);
		                			recommendation = recommendationRepository.findOneByIdentifier(answerRecommendation.trim()).orElse(null);
		                			answer.recommendation(recommendation);
		                		}
		                		Annex annex = null;
		                		if(answerAnnex!=null && !answerAnnex.isEmpty()) {		                			
		                			annex = annexRepository.findOneByIdentifier(answerAnnex).orElse(null);
		                			answer.annex(annex);
		                		}
		                	}
		                	
		                	answerRepository.save(answer);
		                }
		                
		                aCtr++;
	                }while(currentRow.getCell(i) != null);
                }
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	    logger.info("done questions");
	}
	
	
	private void loadRecommendations(boolean loadReferences, String filename, Module module) {

	    logger.info("start recommendations");
	    try { 
	    	// Recommendation
	    	FileInputStream fileInputStream = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(1);
            int rowNum = 2;
            while (rowNum < sheet.getLastRowNum()) {
                Row currentRow = sheet.getRow(rowNum);
                
                int i = 0;

            	String recommendationId = null;
                if(currentRow.getCell(i) != null) {
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	recommendationId =  currentRow.getCell(i).getStringCellValue().isEmpty()?null:currentRow.getCell(i).getStringCellValue().trim();
                	i++;
                }
                String content = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
                String nextQuestionIDs = null;
                if(currentRow.getCell(i) != null) {                	
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	nextQuestionIDs = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;
                }
                
                String nextRecommendationID = null;
                if(currentRow.getCell(i)!= null) {
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	nextRecommendationID =  currentRow.getCell(i).getStringCellValue().trim();
                }
                if(recommendationId != null && content!=null && !recommendationId.isEmpty()) {                	
                	Recommendation recommendation = new Recommendation();
                	Optional<Recommendation> optRecommendation = recommendationRepository.findOneByIdentifier(recommendationId);
                	if(optRecommendation.isPresent()) {
                		recommendation = optRecommendation.get();
                	}
                	recommendation.module(module);
                	recommendation.identifier(recommendationId).content(content);
                	recommendationRepository.save(recommendation);
                	if(loadReferences) {
                		if(nextQuestionIDs !=null && !nextQuestionIDs.isEmpty()) {
	                		String[] arrayIds = nextQuestionIDs.split(",");
	                		recommendation.getNextQuestions().clear();
	                		for(int j=0; j<arrayIds.length;j++ ) {
	                			String nextQuestionID = arrayIds[j];
	                			Question nextQuestion = nextQuestionID!=null?questionRepository.findOneByIdentifier(nextQuestionID.trim()).orElse(null): null;
	                			if(nextQuestion!=null) {                				
	                				recommendation.getNextQuestions().add(nextQuestion);                			
	                			} else {
	                				logger.error("Question not found for recommendation:"+nextQuestionID);
	                			}
	                		}
                		}
                		Recommendation nextRecommendation = nextRecommendationID!=null?recommendationRepository.findOneByIdentifier(nextRecommendationID).orElse(null): null;
                		recommendation.setNextRecommendation(nextRecommendation);
                		recommendationRepository.save(recommendation);
                	}
                }
                
                logger.debug("Row:{},i:{}, ID:{}, question:{}",currentRow.getRowNum(),i, recommendationId, nextRecommendationID);
                rowNum++;
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
	    }

		logger.info("Recommendation Present:"+recommendationRepository.findOneByIdentifier("R1").isPresent());
	}
	
	
	private void loadAnnexes(boolean loadReferences, String filename, Module module) {
	    try { 
	    	// Annex
	    	FileInputStream fileInputStream = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(2);
            int rowNum = 2;
            while (rowNum < sheet.getLastRowNum()) {
                Row currentRow = sheet.getRow(rowNum);
                
                
                int i = 0;
            	String annexID = null;
                if(currentRow.getCell(i) != null) {
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	annexID =  currentRow.getCell(i).getStringCellValue().isEmpty()?null:currentRow.getCell(i).getStringCellValue();
                	i++;
                }

                String content = currentRow.getCell(i)!= null && currentRow.getCell(i).getCellType() == CellType.STRING?currentRow.getCell(i).getStringCellValue(): currentRow.getCell(i).getCellType()==CellType.NUMERIC ? currentRow.getCell(i).getNumericCellValue()+"":null; i++;

            	String nextQuestionIDs = null;
                if(currentRow.getCell(i)!= null) {
                	currentRow.getCell(i).setCellType(CellType.STRING);
                	nextQuestionIDs =  currentRow.getCell(i).getStringCellValue();
                }

                if(annexID != null && content!=null) {
	                Annex annex = new Annex();
	                Optional<Annex> optAnnex = annexRepository.findOneByIdentifier(annexID);
	                if(optAnnex.isPresent()) {
	                	annex = optAnnex.get();
	                }
	                annex.module(module);
	                if(loadReferences) {
	                	annex.getNextQuestions().clear();
	                	if(nextQuestionIDs !=null ) {
	                		String[] arrayIds = nextQuestionIDs.split(",");
	                		for(int j=0; j<arrayIds.length;j++ ) {
	                			String nextQuestionID = arrayIds[j];
	                			Question nextQuestion = nextQuestionID!=null?questionRepository.findOneByIdentifier(nextQuestionID.trim()).orElse(null): null;
	                			if(nextQuestion!=null) {                				
	                				annex.getNextQuestions().add(nextQuestion);                			
	                			} else {
	                				logger.error("Question not found for annex:"+nextQuestionID);
	                			}

	                		}
                		}
	                }
	                
	                annex.identifier(annexID).content(content);
	                annexRepository.save(annex);
                }
                
                logger.debug("Row:{},i:{}, ID:{}, question:{}",currentRow.getRowNum(),i, annexID, nextQuestionIDs);
                rowNum++;
                if(annexID == null)
                	break;
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
	    }
		logger.info("Annex Present:"+annexRepository.findOneByIdentifier("A1").isPresent());
	}

}
