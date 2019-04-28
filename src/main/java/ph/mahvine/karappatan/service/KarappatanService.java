package ph.mahvine.karappatan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    public void createCaseSummary(CaseSummaryDTO caseSummaryDTO) {
    	
    }
    
    public void acceptCaseSummary() {
    	
    }

}
