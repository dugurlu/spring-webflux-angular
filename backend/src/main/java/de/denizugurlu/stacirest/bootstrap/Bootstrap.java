package de.denizugurlu.stacirest.bootstrap;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.annotated.repositories.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Profile("dev")
@Component
public class Bootstrap implements ApplicationRunner {

    private ProjectRepository projectRepository;

    public Bootstrap(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.just("A", "B", "C")
                .map(name -> Project.builder().name(name).build())
                .flatMap(project -> projectRepository.save(project))
                .subscribe(project -> System.out.println(project));
    }
}
