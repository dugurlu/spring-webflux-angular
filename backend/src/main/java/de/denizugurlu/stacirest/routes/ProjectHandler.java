package de.denizugurlu.stacirest.routes;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ProjectHandler {

    private ProjectRepository projectRepository;

    public ProjectHandler(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    Mono<ServerResponse> projects(ServerRequest req) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(projectRepository.findAll(), Project.class);
    }

    Mono<ServerResponse> project(ServerRequest req) {
        String id = req.pathVariable("id");
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(projectRepository.findById(id), Project.class);
    }
}
