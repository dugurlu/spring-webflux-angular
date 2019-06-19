package de.denizugurlu.stacirest.controllers;

import de.denizugurlu.stacirest.annotated.controllers.ProjectController;
import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.annotated.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    public static final String PROJECT_NAME_TOO_SHORT = "A";
    WebTestClient client;

    @Mock
    ProjectRepository repository;

    @InjectMocks
    ProjectController controller;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.initMocks(ProjectControllerTest.class);
        client = WebTestClient.bindToController(controller).build();
    }

    @Test
    void list() {
        given(repository.findAll()).willReturn(Flux.just(
                Project.builder().name("A").build(),
                Project.builder().name("B").build()));

        client.get()
                .uri(ProjectController.BASE_URL)
                .exchange()
                .expectBodyList(Project.class)
                .hasSize(2);
    }

    @Test
    public void createValidProjectIsOk() {
        given(repository.save(any(Project.class)))
                .willReturn(Mono.just(Project.builder().build()));

        Mono<Project> project = Mono.just(Project.builder().name("TestProject").build());

        client.post()
                .uri(ProjectController.BASE_URL)
                .body(project, Project.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void createInvalidProjectIs400() {
        Mono<Project> project = Mono.just(Project.builder().name(PROJECT_NAME_TOO_SHORT).build());

        client.post()
                .uri(ProjectController.BASE_URL)
                .body(project, Project.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void updateExistingProjectIsOk() {
        Mono<Project> newProject = Mono.just(Project.builder().name("TestProject").build());
        Mono<Project> savedProject = Mono.just(Project.builder().id("foo").name("OldName").build());
        Mono<Project> updatedProject = Mono.just(Project.builder().id("foo").name("TestProject").build());

        given(repository.findById(anyString())).willReturn(savedProject);
        given(repository.save(any(Project.class)))
                .willReturn(updatedProject);

        client.put()
                .uri(ProjectController.BASE_URL + "/foo")
                .body(newProject, Project.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Project.class);
    }


    @Test
    public void updateMissingProjectIsNotFound() {
        Mono<Project> newProject = Mono.just(Project.builder().name("TestProject").build());

        given(repository.findById(anyString())).willReturn(Mono.empty());

        client.put()
                .uri(ProjectController.BASE_URL + "/foo")
                .body(newProject, Project.class)
                .exchange()
                .expectStatus().isNotFound();
    }
}