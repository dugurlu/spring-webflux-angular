package de.denizugurlu.stacirest.functional.handlers;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.annotated.repositories.ProjectRepository;
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

    public Mono<ServerResponse> getAll(ServerRequest req) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(projectRepository.findAll(), Project.class);
    }
}
