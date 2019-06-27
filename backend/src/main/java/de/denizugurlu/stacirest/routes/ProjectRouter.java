package de.denizugurlu.stacirest.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ProjectRouter {

    @Bean
    RouterFunction<ServerResponse> routes(ProjectHandler projectHandler) {
        return route(GET("/api/v2/projects"), projectHandler::projects)
                .andRoute(GET("/api/v2/projects/{id}"), projectHandler::project);
    }

    @Bean
    RouterFunction<ServerResponse> indexRoute(@Value("classpath:/static/index.html") Resource html) {
        return route(GET("/"), r -> ok().contentType(MediaType.TEXT_HTML).syncBody(html));
    }
}

