package de.denizugurlu.stacirest.config;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.handler.ProjectHandler;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static de.denizugurlu.stacirest.config.RouterConfiguration.API_BASE_URL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = {RouterConfiguration.class, ProjectHandler.class, TestSecurityConfig.class})
class RouterConfigurationTest {

    @Autowired
    ApplicationContext context;

    private WebTestClient client;

    @MockBean
    ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        client = WebTestClient.bindToApplicationContext(context).build();
    }

    @Test
    void index() {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_HTML);

        verifyZeroInteractions(projectRepository);
    }

    @Test
    void getAllProjects() {
        when(projectRepository.findAll())
                .thenReturn(Flux.just(Project.builder().id("1").name("test-project").build()));

        client.get()
                .uri(API_BASE_URL + "/projects")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().jsonPath("$.[0].name").isEqualTo("test-project");
    }

    @Test
    void getProject() {
        when(projectRepository.findById(anyString()))
                .thenReturn(Mono.just(Project.builder().id("1").name("test-project").build()));

        client.get()
                .uri(API_BASE_URL + "projects/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().jsonPath("$.name").isEqualTo("test-project");
    }

    @Test
    void createProject() {
        when(projectRepository.insert(ArgumentMatchers.<Mono<Project>>any()))
                .thenReturn(Flux.just(Project.builder().id("1").name("test-project").customer("test-customer").build()));

        client.post()
                .uri(API_BASE_URL + "projects")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().jsonPath("$.id").isNotEmpty();
    }

    @Test
    void updateProject() {
        when(projectRepository.findById(anyString()))
                .thenReturn(Mono.just(Project.builder().id("1").name("test-project").build()));

        when(projectRepository.save(any(Project.class)))
                .thenReturn(Mono.just(Project.builder().id("1").name("test-project-updated").build()));

        client.put()
                .uri(API_BASE_URL + "projects/1")
                .body(BodyInserters.fromPublisher(Mono.just(Project.builder().id("1").name("foobar").build()), Project.class))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().jsonPath("$.name").isEqualTo("test-project-updated");

    }

    @Test
    void deleteProject() {
        when(projectRepository.deleteById(anyString()))
                .thenReturn(Mono.empty());

        client.delete()
                .uri(API_BASE_URL + "projects/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}