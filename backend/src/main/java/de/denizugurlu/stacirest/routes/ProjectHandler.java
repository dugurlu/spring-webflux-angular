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

    Mono<ServerResponse> projects(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(projectRepository.findAll(), Project.class);
    }

    Mono<ServerResponse> project(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(projectRepository.findById(id), Project.class);
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Mono<Project> project = request.bodyToMono(Project.class);
        return projectRepository.insert(project).next()
                .flatMap(p ->
                        created(UriComponentsBuilder.fromPath("/projects/{id}").buildAndExpand(p.getId()).toUri())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .body(fromObject(p)));
    }

    Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Project> project = request.bodyToMono(Project.class);
        return Mono.zip(projectRepository.findById(id), project).map(t -> {
            Project projectToSave = t.getT1();
            projectToSave.setName(t.getT2().getName());
            projectToSave.setCustomer(t.getT2().getCustomer());
            projectToSave.setRatings(t.getT2().getRatings());
            return projectRepository.save(projectToSave);
        }).flatMap(p ->
                ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p, Project.class));

    }
}
