package de.denizugurlu.stacirest.routers;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ProjectRouter {

    private final ProjectRepository projectRepository;

    public ProjectRouter(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Bean
    RouterFunction<ServerResponse> routes(@Value("classpath:/static/index.html") Resource html) {
        return route(GET("/"), r -> ok().contentType(MediaType.TEXT_HTML).syncBody(html))
                .andRoute(GET("/api/v2/projects"), this::list);
    }


    private Mono<ServerResponse> list(ServerRequest req) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(projectRepository.findAll(), Project.class);
    }

}
