package ph.mahvine.karappatan.web.rest;
import ph.mahvine.karappatan.domain.Module;
import ph.mahvine.karappatan.repository.ModuleRepository;
import ph.mahvine.karappatan.web.rest.errors.BadRequestAlertException;
import ph.mahvine.karappatan.web.rest.util.HeaderUtil;
import ph.mahvine.karappatan.web.rest.util.PaginationUtil;
import ph.mahvine.karappatan.service.dto.ModuleDTO;
import ph.mahvine.karappatan.service.mapper.ModuleMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Module.
 */
@RestController
@RequestMapping("/api")
public class ModuleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);

    private static final String ENTITY_NAME = "module";

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleResource(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    /**
     * POST  /modules : Create a new module.
     *
     * @param moduleDTO the moduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new moduleDTO, or with status 400 (Bad Request) if the module has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/modules")
    public ResponseEntity<ModuleDTO> createModule(@Valid @RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        log.debug("REST request to save Module : {}", moduleDTO);
        if (moduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        ModuleDTO result = moduleMapper.toDto(module);
        return ResponseEntity.created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /modules : Updates an existing module.
     *
     * @param moduleDTO the moduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated moduleDTO,
     * or with status 400 (Bad Request) if the moduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the moduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/modules")
    public ResponseEntity<ModuleDTO> updateModule(@Valid @RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        log.debug("REST request to update Module : {}", moduleDTO);
        if (moduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        ModuleDTO result = moduleMapper.toDto(module);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, moduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /modules : get all the modules.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of modules in body
     */
    @GetMapping("/modules")
    public ResponseEntity<List<ModuleDTO>> getAllModules(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Modules");
        Page<ModuleDTO> page;
        if (eagerload) {
            page = moduleRepository.findAllWithEagerRelationships(pageable).map(moduleMapper::toDto);
        } else {
            page = moduleRepository.findAll(pageable).map(moduleMapper::toDto);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/modules?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /modules/:id : get the "id" module.
     *
     * @param id the id of the moduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the moduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable Long id) {
        log.debug("REST request to get Module : {}", id);
        Optional<ModuleDTO> moduleDTO = moduleRepository.findOneWithEagerRelationships(id)
            .map(moduleMapper::toDto);
        return ResponseUtil.wrapOrNotFound(moduleDTO);
    }

    /**
     * DELETE  /modules/:id : delete the "id" module.
     *
     * @param id the id of the moduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.debug("REST request to delete Module : {}", id);
        moduleRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
