package de.denizugurlu.stacirest.routes;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
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

    Mono<ServerResponse> create(ServerRequest req) {
        Mono<Project> project = req.bodyToMono(Project.class);
        return projectRepository.insert(project).next()
                .flatMap(p ->
                        created(UriComponentsBuilder.fromPath("/projects/{id}").buildAndExpand(p.getId()).toUri())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .body(fromObject(p)));
    }
}
