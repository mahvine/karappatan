package ph.mahvine.karappatan.service;

import ph.mahvine.karappatan.domain.CaseSummary;
import ph.mahvine.karappatan.repository.CaseSummaryRepository;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.mapper.CaseSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CaseSummary.
 */
@Service
@Transactional
public class CaseSummaryService {

    private final Logger log = LoggerFactory.getLogger(CaseSummaryService.class);

    private final CaseSummaryRepository caseSummaryRepository;

    private final CaseSummaryMapper caseSummaryMapper;

    public CaseSummaryService(CaseSummaryRepository caseSummaryRepository, CaseSummaryMapper caseSummaryMapper) {
        this.caseSummaryRepository = caseSummaryRepository;
        this.caseSummaryMapper = caseSummaryMapper;
    }

    /**
     * Save a caseSummary.
     *
     * @param caseSummaryDTO the entity to save
     * @return the persisted entity
     */
    public CaseSummaryDTO save(CaseSummaryDTO caseSummaryDTO) {
        log.debug("Request to save CaseSummary : {}", caseSummaryDTO);
        CaseSummary caseSummary = caseSummaryMapper.toEntity(caseSummaryDTO);
        caseSummary = caseSummaryRepository.save(caseSummary);
        return caseSummaryMapper.toDto(caseSummary);
    }

    /**
     * Get all the caseSummaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CaseSummaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CaseSummaries");
        return caseSummaryRepository.findAll(pageable)
            .map(caseSummaryMapper::toDto);
    }

    /**
     * Get all the CaseSummary with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<CaseSummaryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return caseSummaryRepository.findAllWithEagerRelationships(pageable).map(caseSummaryMapper::toDto);
    }
    

    /**
     * Get one caseSummary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CaseSummaryDTO> findOne(Long id) {
        log.debug("Request to get CaseSummary : {}", id);
        return caseSummaryRepository.findOneWithEagerRelationships(id)
            .map(caseSummaryMapper::toDto);
    }

    /**
     * Delete the caseSummary by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CaseSummary : {}", id);        caseSummaryRepository.deleteById(id);
    }
}
