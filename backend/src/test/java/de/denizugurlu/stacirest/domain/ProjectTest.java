package de.denizugurlu.stacirest.domain;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProjectTest {
    @Test
    void shouldConstruct() {
        Project project = new Project("1", "test-project");

        // JUnit
        assertEquals("1", project.getId());
        // hamcrest
        assertThat(project.getName(), Matchers.equalToIgnoringCase("test-project"));

        // AssertJ
        Assertions.assertThat(project)
                .as("not a null reference")
                .isNotNull();

        Assertions.assertThat(project.getName())
                .as("The project name is populated")
                .isNotBlank();
    }

}