package de.denizugurlu.stacirest.functional.routers;

import de.denizugurlu.stacirest.functional.handlers.ProjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class ProjectRouter {

    private ProjectHandler projectHandler;

    public ProjectRouter(ProjectHandler projectHandler) {
        this.projectHandler = projectHandler;
    }

    @Bean
    RouterFunction<ServerResponse> getAllProjects() {
        return RouterFunctions.route(GET("/api/v2/projects"), projectHandler::getAll);
    }
}
