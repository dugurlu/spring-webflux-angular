package de.denizugurlu.stacirest.repositories;

import de.denizugurlu.stacirest.domain.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.Repository;
import reactor.core.publisher.Flux;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
    Flux<Project> findByName(String name);
}
