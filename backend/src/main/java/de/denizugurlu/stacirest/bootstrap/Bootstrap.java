package de.denizugurlu.stacirest.bootstrap;

import de.denizugurlu.stacirest.domain.Project;
import de.denizugurlu.stacirest.domain.Rating;
import de.denizugurlu.stacirest.domain.RatingDomain;
import de.denizugurlu.stacirest.repositories.ProjectRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Collections;

@Profile("dev")
@Component
public class Bootstrap implements ApplicationRunner {

    private ProjectRepository projectRepository;

    public Bootstrap(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        Rating rating = Rating.builder().id("1").domain(RatingDomain.BUILD).fulfillment(25).rule("test-rule").build();

        Flux<Project> projects = Flux.just(
                Project.builder().name("Project A").customer("Customer One").ratings(Collections.singletonList(rating)).build(),
                Project.builder().name("Project B").customer("Customer Two").build(),
                Project.builder().name("Project C").customer("Customer Three").build(),
                Project.builder().name("Project D").customer("Customer Four").build(),
                Project.builder().name("Project E").customer("Customer Five").build()
        );

        projectRepository.deleteAll()
                .thenMany(projectRepository.saveAll(projects))
                .blockLast();
    }
}
