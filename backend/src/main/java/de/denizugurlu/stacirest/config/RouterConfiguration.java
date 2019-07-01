package de.denizugurlu.stacirest.config;

import de.denizugurlu.stacirest.handler.ProjectHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouterConfiguration {

    static final String API_BASE_URL = "/api/v1/";

    @Bean
    RouterFunction<ServerResponse> projectRoutes(ProjectHandler projectHandler) {
        return route(GET(API_BASE_URL + "projects"), projectHandler::projects)
                .andRoute(GET(API_BASE_URL + "projects/{id}"), projectHandler::project)
                .andRoute(POST(API_BASE_URL + "projects"), projectHandler::create)
                .andRoute(PUT(API_BASE_URL + "projects/{id}"), projectHandler::update)
                .andRoute(DELETE(API_BASE_URL + "projects/{id}"), projectHandler::delete);
    }

    @Bean
    RouterFunction<ServerResponse> indexRoute(@Value("classpath:/static/index.html") Resource html) {
        return route(GET("/"), r -> ok().contentType(MediaType.TEXT_HTML).syncBody(html));
    }
}

