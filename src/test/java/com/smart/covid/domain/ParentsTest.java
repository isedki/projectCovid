package com.smart.covid.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.smart.covid.web.rest.TestUtil;

public class ParentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parents.class);
        Parents parents1 = new Parents();
        parents1.setId(1L);
        Parents parents2 = new Parents();
        parents2.setId(parents1.getId());
        assertThat(parents1).isEqualTo(parents2);
        parents2.setId(2L);
        assertThat(parents1).isNotEqualTo(parents2);
        parents1.setId(null);
        assertThat(parents1).isNotEqualTo(parents2);
    }
}
