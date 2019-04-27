package ph.mahvine.karappatan.service;

import ph.mahvine.karappatan.domain.Recommendation;
import ph.mahvine.karappatan.repository.RecommendationRepository;
import ph.mahvine.karappatan.service.dto.RecommendationDTO;
import ph.mahvine.karappatan.service.mapper.RecommendationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Recommendation.
 */
@Service
@Transactional
public class RecommendationService {

    private final Logger log = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationRepository recommendationRepository;

    private final RecommendationMapper recommendationMapper;

    public RecommendationService(RecommendationRepository recommendationRepository, RecommendationMapper recommendationMapper) {
        this.recommendationRepository = recommendationRepository;
        this.recommendationMapper = recommendationMapper;
    }

    /**
     * Save a recommendation.
     *
     * @param recommendationDTO the entity to save
     * @return the persisted entity
     */
    public RecommendationDTO save(RecommendationDTO recommendationDTO) {
        log.debug("Request to save Recommendation : {}", recommendationDTO);
        Recommendation recommendation = recommendationMapper.toEntity(recommendationDTO);
        recommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.toDto(recommendation);
    }

    /**
     * Get all the recommendations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecommendationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recommendations");
        return recommendationRepository.findAll(pageable)
            .map(recommendationMapper::toDto);
    }

    /**
     * Get all the Recommendation with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<RecommendationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return recommendationRepository.findAllWithEagerRelationships(pageable).map(recommendationMapper::toDto);
    }
    

    /**
     * Get one recommendation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RecommendationDTO> findOne(Long id) {
        log.debug("Request to get Recommendation : {}", id);
        return recommendationRepository.findOneWithEagerRelationships(id)
            .map(recommendationMapper::toDto);
    }

    /**
     * Delete the recommendation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Recommendation : {}", id);        recommendationRepository.deleteById(id);
    }
}
