package de.denizugurlu.stacirest.functional.routers;

import de.denizugurlu.stacirest.functional.handlers.ProjectHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ProjectRouter {

    private ProjectHandler projectHandler;

    public ProjectRouter(ProjectHandler projectHandler) {
        this.projectHandler = projectHandler;
    }

    @Bean
    RouterFunction<ServerResponse> getAllProjects(@Value("classpath:/static/index.html") Resource html) {

        return RouterFunctions.route(GET("/api/v2/projects"), projectHandler::getAll)
                .andRoute(GET("/"), request -> ok().contentType(MediaType.TEXT_HTML).syncBody(html));
    }
}
