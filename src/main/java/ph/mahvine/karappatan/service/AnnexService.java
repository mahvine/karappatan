package ph.mahvine.karappatan.service;

import ph.mahvine.karappatan.domain.Annex;
import ph.mahvine.karappatan.repository.AnnexRepository;
import ph.mahvine.karappatan.service.dto.AnnexDTO;
import ph.mahvine.karappatan.service.mapper.AnnexMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Annex.
 */
@Service
@Transactional
public class AnnexService {

    private final Logger log = LoggerFactory.getLogger(AnnexService.class);

    private final AnnexRepository annexRepository;

    private final AnnexMapper annexMapper;

    public AnnexService(AnnexRepository annexRepository, AnnexMapper annexMapper) {
        this.annexRepository = annexRepository;
        this.annexMapper = annexMapper;
    }

    /**
     * Save a annex.
     *
     * @param annexDTO the entity to save
     * @return the persisted entity
     */
    public AnnexDTO save(AnnexDTO annexDTO) {
        log.debug("Request to save Annex : {}", annexDTO);
        Annex annex = annexMapper.toEntity(annexDTO);
        annex = annexRepository.save(annex);
        return annexMapper.toDto(annex);
    }

    /**
     * Get all the annexes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AnnexDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Annexes");
        return annexRepository.findAll(pageable)
            .map(annexMapper::toDto);
    }

    /**
     * Get all the Annex with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AnnexDTO> findAllWithEagerRelationships(Pageable pageable) {
        return annexRepository.findAllWithEagerRelationships(pageable).map(annexMapper::toDto);
    }
    

    /**
     * Get one annex by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AnnexDTO> findOne(Long id) {
        log.debug("Request to get Annex : {}", id);
        return annexRepository.findOneWithEagerRelationships(id)
            .map(annexMapper::toDto);
    }

    /**
     * Delete the annex by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Annex : {}", id);        annexRepository.deleteById(id);
    }
}
