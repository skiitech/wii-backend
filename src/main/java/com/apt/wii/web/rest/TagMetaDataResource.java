package com.apt.wii.web.rest;

import com.apt.wii.repository.TagMetaDataRepository;
import com.apt.wii.service.TagMetaDataService;
import com.apt.wii.service.dto.TagMetaDataDTO;
import com.apt.wii.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apt.wii.domain.TagMetaData}.
 */
@RestController
@RequestMapping("/api")
public class TagMetaDataResource {

    private final Logger log = LoggerFactory.getLogger(TagMetaDataResource.class);

    private static final String ENTITY_NAME = "tagMetaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagMetaDataService tagMetaDataService;

    private final TagMetaDataRepository tagMetaDataRepository;

    public TagMetaDataResource(TagMetaDataService tagMetaDataService, TagMetaDataRepository tagMetaDataRepository) {
        this.tagMetaDataService = tagMetaDataService;
        this.tagMetaDataRepository = tagMetaDataRepository;
    }

    /**
     * {@code POST  /tag-meta-data} : Create a new tagMetaData.
     *
     * @param tagMetaDataDTO the tagMetaDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagMetaDataDTO, or with status {@code 400 (Bad Request)} if the tagMetaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/secure/tag-meta-data")
    public ResponseEntity<TagMetaDataDTO> createTagMetaData(@RequestBody TagMetaDataDTO tagMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to save TagMetaData : {}", tagMetaDataDTO);
        if (tagMetaDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagMetaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagMetaDataDTO result = tagMetaDataService.save(tagMetaDataDTO);
        return ResponseEntity
            .created(new URI("/api/tag-meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-meta-data/:id} : Updates an existing tagMetaData.
     *
     * @param id the id of the tagMetaDataDTO to save.
     * @param tagMetaDataDTO the tagMetaDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagMetaDataDTO,
     * or with status {@code 400 (Bad Request)} if the tagMetaDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagMetaDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/secure/tag-meta-data/{id}")
    public ResponseEntity<TagMetaDataDTO> updateTagMetaData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagMetaDataDTO tagMetaDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TagMetaData : {}, {}", id, tagMetaDataDTO);
        if (tagMetaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagMetaDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagMetaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TagMetaDataDTO result = tagMetaDataService.save(tagMetaDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagMetaDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tag-meta-data/:id} : Partial updates given fields of an existing tagMetaData, field will ignore if it is null
     *
     * @param id the id of the tagMetaDataDTO to save.
     * @param tagMetaDataDTO the tagMetaDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagMetaDataDTO,
     * or with status {@code 400 (Bad Request)} if the tagMetaDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tagMetaDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tagMetaDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/secure/tag-meta-data/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TagMetaDataDTO> partialUpdateTagMetaData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TagMetaDataDTO tagMetaDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TagMetaData partially : {}, {}", id, tagMetaDataDTO);
        if (tagMetaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tagMetaDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tagMetaDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TagMetaDataDTO> result = tagMetaDataService.partialUpdate(tagMetaDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagMetaDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tag-meta-data} : get all the tagMetaData.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagMetaData in body.
     */
    @GetMapping("/tag-meta-data")
    public List<TagMetaDataDTO> getAllTagMetaData() {
        log.debug("REST request to get all TagMetaData");
        return tagMetaDataService.findAll();
    }

    /**
     * {@code GET  /tag-meta-data/:id} : get the "id" tagMetaData.
     *
     * @param id the id of the tagMetaDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagMetaDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-meta-data/{id}")
    public ResponseEntity<TagMetaDataDTO> getTagMetaData(@PathVariable Long id) {
        log.debug("REST request to get TagMetaData : {}", id);
        Optional<TagMetaDataDTO> tagMetaDataDTO = tagMetaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagMetaDataDTO);
    }

    /**
     * {@code GET  /tag-meta-data/:id} : get the "id" tagMetaData.
     *
     * @param id the id of the tagMetaDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagMetaDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question/{id}/tag-meta-data")
    public List<TagMetaDataDTO> getTagMetaDataByQuestion(@PathVariable Long id) {
        log.debug("REST request to get TagMetaData : {}", id);
        List<TagMetaDataDTO> tagMetaDataDTO = tagMetaDataService.findByQuestion(id);
        return tagMetaDataDTO;
    }

    @GetMapping("/tag-meta-data/allUnique")
    public Map<String, List<String>> getTagsForFilters() {
        log.debug("REST request to get TagMetaData for Filter");
        return tagMetaDataService.findAllUniqueTags();
    }

    /**
     * {@code DELETE  /tag-meta-data/:id} : delete the "id" tagMetaData.
     *
     * @param id the id of the tagMetaDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/secure/tag-meta-data/{id}")
    public ResponseEntity<Void> deleteTagMetaData(@PathVariable Long id) {
        log.debug("REST request to delete TagMetaData : {}", id);
        tagMetaDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
