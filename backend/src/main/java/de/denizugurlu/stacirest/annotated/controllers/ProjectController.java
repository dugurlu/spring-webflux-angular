package de.denizugurlu.stacirest.annotated.controllers;

import de.denizugurlu.stacirest.annotated.repositories.ProjectRepository;
import de.denizugurlu.stacirest.domain.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(ProjectController.BASE_URL)
@Slf4j
public class ProjectController {
    public static final String BASE_URL = "/api/v1/projects";

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public Flux<Project> list() {
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Project>> get(@PathVariable String id) {
        return projectRepository.findById(id)
                .map(savedProject -> ResponseEntity.ok(savedProject))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Project> createProject(@Valid @RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Project>> updateProject(@PathVariable String id, @Valid @RequestBody Project project) {
        return projectRepository.findById(id)
                .flatMap(existingProject -> {
                    existingProject.setName(project.getName());
                    return projectRepository.save(existingProject);
                })
                .map(updatedProject -> new ResponseEntity<>(updatedProject, HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
