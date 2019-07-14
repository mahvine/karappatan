package ph.mahvine.karappatan.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ph.mahvine.karappatan.domain.Answer;
import ph.mahvine.karappatan.domain.CaseSummary;
import ph.mahvine.karappatan.domain.CaseSummaryOffer;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.domain.enumeration.OfferStatus;
import ph.mahvine.karappatan.repository.AnswerRepository;
import ph.mahvine.karappatan.repository.CaseSummaryOfferRepository;
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
    
    private final CaseSummaryOfferRepository caseSummaryOfferRepository;

    public KarappatanService(CaseSummaryRepository caseSummaryRepository, CaseSummaryMapper caseSummaryMapper, AnswerRepository answerRepository, CaseSummaryOfferRepository caseSummaryOfferRepository) {
        this.caseSummaryRepository = caseSummaryRepository;
        this.caseSummaryMapper = caseSummaryMapper;
        this.answerRepository = answerRepository;
        this.caseSummaryOfferRepository = caseSummaryOfferRepository;
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
    

    public CaseSummaryOffer createOffer(Long caseSummaryId, User user) {
    	CaseSummary caseSummary = caseSummaryRepository.getOne(caseSummaryId);
    	CaseSummaryOffer offer = new CaseSummaryOffer();
    	offer.dateCreated(Instant.now());
    	offer.lawyer(user);
    	offer.caseSummary(caseSummary);
    	log.info("Case summary:{} offered by user:{}",caseSummary.getId(), user.getLogin());
    	caseSummaryOfferRepository.save(offer);
    	return offer;
    }
    
    
    public void denyOffer(Long caseSummaryOfferId, User user) {
    	Optional<CaseSummaryOffer> caseSummaryOfferOpt = caseSummaryOfferRepository.findById(caseSummaryOfferId);
    	if(caseSummaryOfferOpt.isPresent()) {
    		CaseSummaryOffer caseSummaryOffer = caseSummaryOfferOpt.get();
    		CaseSummary caseSummary = caseSummaryOffer.getCaseSummary();
    		if(caseSummaryOffer.getStatus() == OfferStatus.ACCEPTED || caseSummaryOffer.getStatus() == OfferStatus.DECLINED) {
    			throw new RuntimeException("Cannot deny offer already in end state");
    		} else {
    			if(caseSummary.getUser().getId() == user.getId()) {    				
    				caseSummaryOffer.setStatus(OfferStatus.DECLINED);
    				caseSummaryOfferRepository.save(caseSummaryOffer);
    			} else {
    				throw new RuntimeException("Not allowed to do transaction");
    			}
    		}
    	} else {
    		throw new RuntimeException("Case summary offer not existing:"+caseSummaryOfferId);
    	}
    }
    
    public void acceptOffer(Long caseSummaryOfferId, User user) {
    	Optional<CaseSummaryOffer> caseSummaryOfferOpt = caseSummaryOfferRepository.findById(caseSummaryOfferId);
    	if(caseSummaryOfferOpt.isPresent()) {
    		CaseSummaryOffer caseSummaryOffer = caseSummaryOfferOpt.get();
    		CaseSummary caseSummary = caseSummaryOffer.getCaseSummary();
    		if(caseSummaryOffer.getStatus() == OfferStatus.ACCEPTED || caseSummaryOffer.getStatus() == OfferStatus.DECLINED) {
    			throw new RuntimeException("Cannot deny offer already in end state");
    		} else {
    			if(caseSummary.getUser().getId() == user.getId()) {  
    				List<CaseSummaryOffer> otherCaseOffers = caseSummaryOfferRepository.findByCaseSummaryId(caseSummary.getId());
    				otherCaseOffers.forEach(offer -> {
    					if(offer.getId() == caseSummaryOfferId) {
    						offer.setStatus(OfferStatus.ACCEPTED);
    					} else {    						
    						offer.setStatus(OfferStatus.DECLINED);
    					}
    				});
    				caseSummaryOfferRepository.saveAll(otherCaseOffers);
    				acceptCaseSummary(caseSummary.getId(), user);
    			} else {
    				throw new RuntimeException("Not allowed to do transaction");
    			}
    		}
    	} else {
    		throw new RuntimeException("Case summary offer not existing:"+caseSummaryOfferId);
    	}
    }

}
