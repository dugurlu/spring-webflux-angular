package de.denizugurlu.stacirest.bootstrap;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Profile("bootstrap-data")
@Component
public class Bootstrap implements ApplicationRunner {

    private ProjectRepository projectRepository;

    public Bootstrap(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Flux<Project> projects = Flux.just(
                Project.builder().name("A").build(),
                Project.builder().name("B").build(),
                Project.builder().name("C").build(),
                Project.builder().name("D").build(),
                Project.builder().name("E").build()
        );

        projectRepository.deleteAll()
                .thenMany(projectRepository.saveAll(projects))
                .blockLast();
    }
}
