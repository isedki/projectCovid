package com.smart.covid.web.rest;

import com.smart.covid.Version1App;
import com.smart.covid.domain.CovidUpdates;
import com.smart.covid.repository.CovidUpdatesRepository;
import com.smart.covid.service.CovidUpdatesService;
import com.smart.covid.service.dto.CovidUpdatesCriteria;
import com.smart.covid.service.CovidUpdatesQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CovidUpdatesResource} REST controller.
 */
@SpringBootTest(classes = Version1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class CovidUpdatesResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PUBLISHED_AT = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHED_AT = "BBBBBBBBBB";

    @Autowired
    private CovidUpdatesRepository covidUpdatesRepository;

    @Autowired
    private CovidUpdatesService covidUpdatesService;

    @Autowired
    private CovidUpdatesQueryService covidUpdatesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCovidUpdatesMockMvc;

    private CovidUpdates covidUpdates;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovidUpdates createEntity(EntityManager em) {
        CovidUpdates covidUpdates = new CovidUpdates()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .source(DEFAULT_SOURCE)
            .domain(DEFAULT_DOMAIN)
            .image(DEFAULT_IMAGE)
            .publishedAt(DEFAULT_PUBLISHED_AT);
        return covidUpdates;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CovidUpdates createUpdatedEntity(EntityManager em) {
        CovidUpdates covidUpdates = new CovidUpdates()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .source(UPDATED_SOURCE)
            .domain(UPDATED_DOMAIN)
            .image(UPDATED_IMAGE)
            .publishedAt(UPDATED_PUBLISHED_AT);
        return covidUpdates;
    }

    @BeforeEach
    public void initTest() {
        covidUpdates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCovidUpdates() throws Exception {
        int databaseSizeBeforeCreate = covidUpdatesRepository.findAll().size();
        // Create the CovidUpdates
        restCovidUpdatesMockMvc.perform(post("/api/covid-updates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covidUpdates)))
            .andExpect(status().isCreated());

        // Validate the CovidUpdates in the database
        List<CovidUpdates> covidUpdatesList = covidUpdatesRepository.findAll();
        assertThat(covidUpdatesList).hasSize(databaseSizeBeforeCreate + 1);
        CovidUpdates testCovidUpdates = covidUpdatesList.get(covidUpdatesList.size() - 1);
        assertThat(testCovidUpdates.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCovidUpdates.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testCovidUpdates.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testCovidUpdates.getDomain()).isEqualTo(DEFAULT_DOMAIN);
        assertThat(testCovidUpdates.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testCovidUpdates.getPublishedAt()).isEqualTo(DEFAULT_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void createCovidUpdatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = covidUpdatesRepository.findAll().size();

        // Create the CovidUpdates with an existing ID
        covidUpdates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCovidUpdatesMockMvc.perform(post("/api/covid-updates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covidUpdates)))
            .andExpect(status().isBadRequest());

        // Validate the CovidUpdates in the database
        List<CovidUpdates> covidUpdatesList = covidUpdatesRepository.findAll();
        assertThat(covidUpdatesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCovidUpdates() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covidUpdates.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].publishedAt").value(hasItem(DEFAULT_PUBLISHED_AT)));
    }
    
    @Test
    @Transactional
    public void getCovidUpdates() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get the covidUpdates
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates/{id}", covidUpdates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(covidUpdates.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.publishedAt").value(DEFAULT_PUBLISHED_AT));
    }


    @Test
    @Transactional
    public void getCovidUpdatesByIdFiltering() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        Long id = covidUpdates.getId();

        defaultCovidUpdatesShouldBeFound("id.equals=" + id);
        defaultCovidUpdatesShouldNotBeFound("id.notEquals=" + id);

        defaultCovidUpdatesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCovidUpdatesShouldNotBeFound("id.greaterThan=" + id);

        defaultCovidUpdatesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCovidUpdatesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title equals to DEFAULT_TITLE
        defaultCovidUpdatesShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the covidUpdatesList where title equals to UPDATED_TITLE
        defaultCovidUpdatesShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title not equals to DEFAULT_TITLE
        defaultCovidUpdatesShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the covidUpdatesList where title not equals to UPDATED_TITLE
        defaultCovidUpdatesShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCovidUpdatesShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the covidUpdatesList where title equals to UPDATED_TITLE
        defaultCovidUpdatesShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title is not null
        defaultCovidUpdatesShouldBeFound("title.specified=true");

        // Get all the covidUpdatesList where title is null
        defaultCovidUpdatesShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesByTitleContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title contains DEFAULT_TITLE
        defaultCovidUpdatesShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the covidUpdatesList where title contains UPDATED_TITLE
        defaultCovidUpdatesShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where title does not contain DEFAULT_TITLE
        defaultCovidUpdatesShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the covidUpdatesList where title does not contain UPDATED_TITLE
        defaultCovidUpdatesShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content equals to DEFAULT_CONTENT
        defaultCovidUpdatesShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the covidUpdatesList where content equals to UPDATED_CONTENT
        defaultCovidUpdatesShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content not equals to DEFAULT_CONTENT
        defaultCovidUpdatesShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the covidUpdatesList where content not equals to UPDATED_CONTENT
        defaultCovidUpdatesShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultCovidUpdatesShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the covidUpdatesList where content equals to UPDATED_CONTENT
        defaultCovidUpdatesShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content is not null
        defaultCovidUpdatesShouldBeFound("content.specified=true");

        // Get all the covidUpdatesList where content is null
        defaultCovidUpdatesShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesByContentContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content contains DEFAULT_CONTENT
        defaultCovidUpdatesShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the covidUpdatesList where content contains UPDATED_CONTENT
        defaultCovidUpdatesShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where content does not contain DEFAULT_CONTENT
        defaultCovidUpdatesShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the covidUpdatesList where content does not contain UPDATED_CONTENT
        defaultCovidUpdatesShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source equals to DEFAULT_SOURCE
        defaultCovidUpdatesShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the covidUpdatesList where source equals to UPDATED_SOURCE
        defaultCovidUpdatesShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source not equals to DEFAULT_SOURCE
        defaultCovidUpdatesShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the covidUpdatesList where source not equals to UPDATED_SOURCE
        defaultCovidUpdatesShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultCovidUpdatesShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the covidUpdatesList where source equals to UPDATED_SOURCE
        defaultCovidUpdatesShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source is not null
        defaultCovidUpdatesShouldBeFound("source.specified=true");

        // Get all the covidUpdatesList where source is null
        defaultCovidUpdatesShouldNotBeFound("source.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesBySourceContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source contains DEFAULT_SOURCE
        defaultCovidUpdatesShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the covidUpdatesList where source contains UPDATED_SOURCE
        defaultCovidUpdatesShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where source does not contain DEFAULT_SOURCE
        defaultCovidUpdatesShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the covidUpdatesList where source does not contain UPDATED_SOURCE
        defaultCovidUpdatesShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesByDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain equals to DEFAULT_DOMAIN
        defaultCovidUpdatesShouldBeFound("domain.equals=" + DEFAULT_DOMAIN);

        // Get all the covidUpdatesList where domain equals to UPDATED_DOMAIN
        defaultCovidUpdatesShouldNotBeFound("domain.equals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByDomainIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain not equals to DEFAULT_DOMAIN
        defaultCovidUpdatesShouldNotBeFound("domain.notEquals=" + DEFAULT_DOMAIN);

        // Get all the covidUpdatesList where domain not equals to UPDATED_DOMAIN
        defaultCovidUpdatesShouldBeFound("domain.notEquals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByDomainIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain in DEFAULT_DOMAIN or UPDATED_DOMAIN
        defaultCovidUpdatesShouldBeFound("domain.in=" + DEFAULT_DOMAIN + "," + UPDATED_DOMAIN);

        // Get all the covidUpdatesList where domain equals to UPDATED_DOMAIN
        defaultCovidUpdatesShouldNotBeFound("domain.in=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByDomainIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain is not null
        defaultCovidUpdatesShouldBeFound("domain.specified=true");

        // Get all the covidUpdatesList where domain is null
        defaultCovidUpdatesShouldNotBeFound("domain.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesByDomainContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain contains DEFAULT_DOMAIN
        defaultCovidUpdatesShouldBeFound("domain.contains=" + DEFAULT_DOMAIN);

        // Get all the covidUpdatesList where domain contains UPDATED_DOMAIN
        defaultCovidUpdatesShouldNotBeFound("domain.contains=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByDomainNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where domain does not contain DEFAULT_DOMAIN
        defaultCovidUpdatesShouldNotBeFound("domain.doesNotContain=" + DEFAULT_DOMAIN);

        // Get all the covidUpdatesList where domain does not contain UPDATED_DOMAIN
        defaultCovidUpdatesShouldBeFound("domain.doesNotContain=" + UPDATED_DOMAIN);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesByImageIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image equals to DEFAULT_IMAGE
        defaultCovidUpdatesShouldBeFound("image.equals=" + DEFAULT_IMAGE);

        // Get all the covidUpdatesList where image equals to UPDATED_IMAGE
        defaultCovidUpdatesShouldNotBeFound("image.equals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image not equals to DEFAULT_IMAGE
        defaultCovidUpdatesShouldNotBeFound("image.notEquals=" + DEFAULT_IMAGE);

        // Get all the covidUpdatesList where image not equals to UPDATED_IMAGE
        defaultCovidUpdatesShouldBeFound("image.notEquals=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByImageIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image in DEFAULT_IMAGE or UPDATED_IMAGE
        defaultCovidUpdatesShouldBeFound("image.in=" + DEFAULT_IMAGE + "," + UPDATED_IMAGE);

        // Get all the covidUpdatesList where image equals to UPDATED_IMAGE
        defaultCovidUpdatesShouldNotBeFound("image.in=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image is not null
        defaultCovidUpdatesShouldBeFound("image.specified=true");

        // Get all the covidUpdatesList where image is null
        defaultCovidUpdatesShouldNotBeFound("image.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesByImageContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image contains DEFAULT_IMAGE
        defaultCovidUpdatesShouldBeFound("image.contains=" + DEFAULT_IMAGE);

        // Get all the covidUpdatesList where image contains UPDATED_IMAGE
        defaultCovidUpdatesShouldNotBeFound("image.contains=" + UPDATED_IMAGE);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByImageNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where image does not contain DEFAULT_IMAGE
        defaultCovidUpdatesShouldNotBeFound("image.doesNotContain=" + DEFAULT_IMAGE);

        // Get all the covidUpdatesList where image does not contain UPDATED_IMAGE
        defaultCovidUpdatesShouldBeFound("image.doesNotContain=" + UPDATED_IMAGE);
    }


    @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt equals to DEFAULT_PUBLISHED_AT
        defaultCovidUpdatesShouldBeFound("publishedAt.equals=" + DEFAULT_PUBLISHED_AT);

        // Get all the covidUpdatesList where publishedAt equals to UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldNotBeFound("publishedAt.equals=" + UPDATED_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt not equals to DEFAULT_PUBLISHED_AT
        defaultCovidUpdatesShouldNotBeFound("publishedAt.notEquals=" + DEFAULT_PUBLISHED_AT);

        // Get all the covidUpdatesList where publishedAt not equals to UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldBeFound("publishedAt.notEquals=" + UPDATED_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtIsInShouldWork() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt in DEFAULT_PUBLISHED_AT or UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldBeFound("publishedAt.in=" + DEFAULT_PUBLISHED_AT + "," + UPDATED_PUBLISHED_AT);

        // Get all the covidUpdatesList where publishedAt equals to UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldNotBeFound("publishedAt.in=" + UPDATED_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt is not null
        defaultCovidUpdatesShouldBeFound("publishedAt.specified=true");

        // Get all the covidUpdatesList where publishedAt is null
        defaultCovidUpdatesShouldNotBeFound("publishedAt.specified=false");
    }
                @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt contains DEFAULT_PUBLISHED_AT
        defaultCovidUpdatesShouldBeFound("publishedAt.contains=" + DEFAULT_PUBLISHED_AT);

        // Get all the covidUpdatesList where publishedAt contains UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldNotBeFound("publishedAt.contains=" + UPDATED_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void getAllCovidUpdatesByPublishedAtNotContainsSomething() throws Exception {
        // Initialize the database
        covidUpdatesRepository.saveAndFlush(covidUpdates);

        // Get all the covidUpdatesList where publishedAt does not contain DEFAULT_PUBLISHED_AT
        defaultCovidUpdatesShouldNotBeFound("publishedAt.doesNotContain=" + DEFAULT_PUBLISHED_AT);

        // Get all the covidUpdatesList where publishedAt does not contain UPDATED_PUBLISHED_AT
        defaultCovidUpdatesShouldBeFound("publishedAt.doesNotContain=" + UPDATED_PUBLISHED_AT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCovidUpdatesShouldBeFound(String filter) throws Exception {
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(covidUpdates.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].publishedAt").value(hasItem(DEFAULT_PUBLISHED_AT)));

        // Check, that the count call also returns 1
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCovidUpdatesShouldNotBeFound(String filter) throws Exception {
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCovidUpdates() throws Exception {
        // Get the covidUpdates
        restCovidUpdatesMockMvc.perform(get("/api/covid-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCovidUpdates() throws Exception {
        // Initialize the database
        covidUpdatesService.save(covidUpdates);

        int databaseSizeBeforeUpdate = covidUpdatesRepository.findAll().size();

        // Update the covidUpdates
        CovidUpdates updatedCovidUpdates = covidUpdatesRepository.findById(covidUpdates.getId()).get();
        // Disconnect from session so that the updates on updatedCovidUpdates are not directly saved in db
        em.detach(updatedCovidUpdates);
        updatedCovidUpdates
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .source(UPDATED_SOURCE)
            .domain(UPDATED_DOMAIN)
            .image(UPDATED_IMAGE)
            .publishedAt(UPDATED_PUBLISHED_AT);

        restCovidUpdatesMockMvc.perform(put("/api/covid-updates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCovidUpdates)))
            .andExpect(status().isOk());

        // Validate the CovidUpdates in the database
        List<CovidUpdates> covidUpdatesList = covidUpdatesRepository.findAll();
        assertThat(covidUpdatesList).hasSize(databaseSizeBeforeUpdate);
        CovidUpdates testCovidUpdates = covidUpdatesList.get(covidUpdatesList.size() - 1);
        assertThat(testCovidUpdates.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCovidUpdates.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testCovidUpdates.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testCovidUpdates.getDomain()).isEqualTo(UPDATED_DOMAIN);
        assertThat(testCovidUpdates.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testCovidUpdates.getPublishedAt()).isEqualTo(UPDATED_PUBLISHED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCovidUpdates() throws Exception {
        int databaseSizeBeforeUpdate = covidUpdatesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCovidUpdatesMockMvc.perform(put("/api/covid-updates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(covidUpdates)))
            .andExpect(status().isBadRequest());

        // Validate the CovidUpdates in the database
        List<CovidUpdates> covidUpdatesList = covidUpdatesRepository.findAll();
        assertThat(covidUpdatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCovidUpdates() throws Exception {
        // Initialize the database
        covidUpdatesService.save(covidUpdates);

        int databaseSizeBeforeDelete = covidUpdatesRepository.findAll().size();

        // Delete the covidUpdates
        restCovidUpdatesMockMvc.perform(delete("/api/covid-updates/{id}", covidUpdates.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CovidUpdates> covidUpdatesList = covidUpdatesRepository.findAll();
        assertThat(covidUpdatesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
