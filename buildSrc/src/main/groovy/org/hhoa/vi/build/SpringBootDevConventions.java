import io.spring.gradle.dependencymanagement.DependencyManagementPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaBasePlugin;
import org.springframework.boot.gradle.plugin.SpringBootPlugin;

/**
 * SpringBootDevConventions
 *
 * @author hhoa
 * @since 2023/3/18
 **/

public class SpringBootDevConventions implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPlugins().withType(JavaBasePlugin.class, (java) -> {
            configureDevtoolsPlugins(project);
        });
    }
    void configureDevtoolsPlugins(Project project) {
        project.getPlugins().apply(DependencyManagementPlugin.class);
        project.getPlugins().apply(SpringBootPlugin.class);
        project.getDependencies().add("compileOnly", "org.springframework.boot:spring-boot-devtools");
        project.getDependencies().add("annotationProcessor", "org.projectlombok:lombok");
        project.getDependencies().add("annotationProcessor","org.springframework.boot:spring-boot-configuration-processor");
        project.getDependencies().add("testImplementation", "org.springframework.boot:spring-boot-starter-test");
        project.getDependencies().add("testImplementation", "org.springframework.restdocs:spring-restdocs-mockmvc");
        project.getDependencies().add("testImplementation", "org.junit.jupiter:junit-jupiter");
    }
}
