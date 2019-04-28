package ph.mahvine.karappatan.service;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.mahvine.karappatan.domain.CaseSummary;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.CaseSummaryRepository;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.mapper.CaseSummaryMapper;

@Service
@Transactional
public class KarappatanService {

    private final Logger log = LoggerFactory.getLogger(KarappatanService.class);

    private final CaseSummaryRepository caseSummaryRepository;

    private final CaseSummaryMapper caseSummaryMapper;

    public KarappatanService(CaseSummaryRepository caseSummaryRepository, CaseSummaryMapper caseSummaryMapper) {
        this.caseSummaryRepository = caseSummaryRepository;
        this.caseSummaryMapper = caseSummaryMapper;
    }
    
    public CaseSummaryDTO createCaseSummary(CaseSummaryDTO caseSummaryDTO, User user) {
    	CaseSummary caseSummary = caseSummaryMapper.toEntity(caseSummaryDTO);
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
