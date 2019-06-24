package de.denizugurlu.stacirest;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import de.denizugurlu.stacirest.routers.ProjectRouter;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "server.port=0")
@ExtendWith(SpringExtension.class)
@Import(ProjectRouter.class)
@ActiveProfiles("test")
public class BaseClass {

    @LocalServerPort
    private int port;

    @MockBean
    private ProjectRepository projectRepository;

    @BeforeEach
    public void before() {

        RestAssured.baseURI = "http://localhost:" + this.port;

        Mockito.when(projectRepository.findAll())
                .thenReturn(Flux.just(Project.builder().id("1").name("test-project").build()));

    }
}
