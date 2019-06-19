package de.denizugurlu.stacirest.annotated.repositories;

import de.denizugurlu.stacirest.domain.Project;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProjectRepository extends ReactiveMongoRepository<Project, String> {
}
