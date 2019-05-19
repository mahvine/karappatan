package ph.mahvine.karappatan.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.mahvine.karappatan.domain.Answer;
import ph.mahvine.karappatan.domain.CaseSummary;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.AnswerRepository;
import ph.mahvine.karappatan.repository.CaseSummaryRepository;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.dto.CreateCaseSummaryDTO;
import ph.mahvine.karappatan.service.mapper.CaseSummaryMapper;

@Service
@Transactional
public class KarappatanService {

    private final Logger log = LoggerFactory.getLogger(KarappatanService.class);

    private final CaseSummaryRepository caseSummaryRepository;

    private final CaseSummaryMapper caseSummaryMapper;
    
    private final AnswerRepository answerRepository;

    public KarappatanService(CaseSummaryRepository caseSummaryRepository, CaseSummaryMapper caseSummaryMapper, AnswerRepository answerRepository) {
        this.caseSummaryRepository = caseSummaryRepository;
        this.caseSummaryMapper = caseSummaryMapper;
        this.answerRepository = answerRepository;
    }
    
    public CaseSummaryDTO createCaseSummary(CreateCaseSummaryDTO caseSummaryDTO, User user) {
    	CaseSummary caseSummary = caseSummaryMapper.toEntityFromDTO(caseSummaryDTO);
    	if(caseSummary.getAnswers().size() > 0) {    		
    		Answer answer = answerRepository.getOne(caseSummaryDTO.getAnswerIds().get(0));
    		caseSummary.module(answer.getQuestion().getModule());
    	}
    	caseSummary.user(user);
    	caseSummary.dateCreated(Instant.now());
    	caseSummary = caseSummaryRepository.save(caseSummary);
    	log.info("Case summary:{} created by user:{}",caseSummary.getId(), user.getLogin());
    	return caseSummaryMapper.toDto(caseSummary);
    }
    
    public CaseSummaryDTO acceptCaseSummary(Long caseSummaryId, User user) {
    	CaseSummary caseSummary = caseSummaryRepository.getOne(caseSummaryId);
    	if(caseSummary.getAcceptedBy()!=null) {
    		throw new RuntimeException("Already accepted");
    	}
    	caseSummary.setAcceptedBy(user);
    	caseSummary = caseSummaryRepository.save(caseSummary);
    	log.info("Case summary:{} accepted by user:{}",caseSummary.getId(), user.getLogin());

    	return caseSummaryMapper.toDto(caseSummary);
    }

}
