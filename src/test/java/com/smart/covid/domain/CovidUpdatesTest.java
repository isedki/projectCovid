package com.smart.covid.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smart.covid.web.rest.TestUtil;

public class CovidUpdatesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CovidUpdates.class);
        CovidUpdates covidUpdates1 = new CovidUpdates();
        covidUpdates1.setId(1L);
        CovidUpdates covidUpdates2 = new CovidUpdates();
        covidUpdates2.setId(covidUpdates1.getId());
        assertThat(covidUpdates1).isEqualTo(covidUpdates2);
        covidUpdates2.setId(2L);
        assertThat(covidUpdates1).isNotEqualTo(covidUpdates2);
        covidUpdates1.setId(null);
        assertThat(covidUpdates1).isNotEqualTo(covidUpdates2);
    }
}
