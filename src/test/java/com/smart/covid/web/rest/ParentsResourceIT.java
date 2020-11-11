package com.smart.covid.web.rest;

import com.smart.covid.Version1App;
import com.smart.covid.domain.Parents;
import com.smart.covid.repository.ParentsRepository;

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
 * Integration tests for the {@link ParentsResource} REST controller.
 */
@SpringBootTest(classes = Version1App.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParentsResourceIT {

    @Autowired
    private ParentsRepository parentsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParentsMockMvc;

    private Parents parents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parents createEntity(EntityManager em) {
        Parents parents = new Parents();
        return parents;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Parents createUpdatedEntity(EntityManager em) {
        Parents parents = new Parents();
        return parents;
    }

    @BeforeEach
    public void initTest() {
        parents = createEntity(em);
    }

    @Test
    @Transactional
    public void createParents() throws Exception {
        int databaseSizeBeforeCreate = parentsRepository.findAll().size();
        // Create the Parents
        restParentsMockMvc.perform(post("/api/parents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parents)))
            .andExpect(status().isCreated());

        // Validate the Parents in the database
        List<Parents> parentsList = parentsRepository.findAll();
        assertThat(parentsList).hasSize(databaseSizeBeforeCreate + 1);
        Parents testParents = parentsList.get(parentsList.size() - 1);
    }

    @Test
    @Transactional
    public void createParentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parentsRepository.findAll().size();

        // Create the Parents with an existing ID
        parents.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentsMockMvc.perform(post("/api/parents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parents)))
            .andExpect(status().isBadRequest());

        // Validate the Parents in the database
        List<Parents> parentsList = parentsRepository.findAll();
        assertThat(parentsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParents() throws Exception {
        // Initialize the database
        parentsRepository.saveAndFlush(parents);

        // Get all the parentsList
        restParentsMockMvc.perform(get("/api/parents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parents.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getParents() throws Exception {
        // Initialize the database
        parentsRepository.saveAndFlush(parents);

        // Get the parents
        restParentsMockMvc.perform(get("/api/parents/{id}", parents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parents.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingParents() throws Exception {
        // Get the parents
        restParentsMockMvc.perform(get("/api/parents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParents() throws Exception {
        // Initialize the database
        parentsRepository.saveAndFlush(parents);

        int databaseSizeBeforeUpdate = parentsRepository.findAll().size();

        // Update the parents
        Parents updatedParents = parentsRepository.findById(parents.getId()).get();
        // Disconnect from session so that the updates on updatedParents are not directly saved in db
        em.detach(updatedParents);

        restParentsMockMvc.perform(put("/api/parents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParents)))
            .andExpect(status().isOk());

        // Validate the Parents in the database
        List<Parents> parentsList = parentsRepository.findAll();
        assertThat(parentsList).hasSize(databaseSizeBeforeUpdate);
        Parents testParents = parentsList.get(parentsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingParents() throws Exception {
        int databaseSizeBeforeUpdate = parentsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentsMockMvc.perform(put("/api/parents")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(parents)))
            .andExpect(status().isBadRequest());

        // Validate the Parents in the database
        List<Parents> parentsList = parentsRepository.findAll();
        assertThat(parentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParents() throws Exception {
        // Initialize the database
        parentsRepository.saveAndFlush(parents);

        int databaseSizeBeforeDelete = parentsRepository.findAll().size();

        // Delete the parents
        restParentsMockMvc.perform(delete("/api/parents/{id}", parents.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Parents> parentsList = parentsRepository.findAll();
        assertThat(parentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
