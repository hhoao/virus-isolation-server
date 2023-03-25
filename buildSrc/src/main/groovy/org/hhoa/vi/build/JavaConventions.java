package org.hhoa.vi.build;

import com.github.spotbugs.snom.SpotBugsExtension;
import com.github.spotbugs.snom.SpotBugsPlugin;
import com.github.spotbugs.snom.SpotBugsTask;
import io.spring.javaformat.gradle.SpringJavaFormatPlugin;
import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;
import org.gradle.external.javadoc.CoreJavadocOptions;
import org.hhoa.vi.build.toolchain.ToolchainPlugin;
import org.hhoa.vi.build.testing.TestFailuresPlugin;

import java.util.Arrays;
import java.util.List;


class JavaConventions implements Plugin<Project>{

    private static final String SOURCE_AND_TARGET_COMPATIBILITY = "17";

    @Override
    public void apply(Project project) {
        project.getPlugins().apply("java");
        project.getPlugins().withType(JavaBasePlugin.class, (java) -> {
            project.getPlugins().apply("java-library");
            project.getPlugins().apply(TestFailuresPlugin.class);
            configureJavaFormat(project);
            configureJavaConventions(project);
            configureJavadocConventions(project);
            configureTestConventions(project);
            configureToolchain(project);
        });
    }

    private void configureTestConventions(Project project) {
        project.getTasks().withType(Test.class, (test) -> {
            test.useJUnitPlatform();
            test.setMaxHeapSize("1024M");
            project.getTasks().withType(Checkstyle.class, test::mustRunAfter);
        });
    }


    private void configureJavadocConventions(Project project) {
        project.getTasks().withType(Javadoc.class, (javadoc) -> {
            CoreJavadocOptions options = (CoreJavadocOptions) javadoc.getOptions();
            options.source("17");
            options.encoding("UTF-8");
            options.addStringOption("Xdoclint:none", "-quiet");
        });
    }

    private void configureJavaConventions(Project project) {
        if (!project.hasProperty("toolchainVersion")) {
            JavaPluginExtension javaPluginExtension = project.getExtensions().getByType(JavaPluginExtension.class);
            javaPluginExtension.setSourceCompatibility(JavaVersion.toVersion(SOURCE_AND_TARGET_COMPATIBILITY));
        }
        project.getTasks().withType(JavaCompile.class, (compile) -> {
            compile.getOptions().setEncoding("UTF-8");
            List<String> args = compile.getOptions().getCompilerArgs();
            if (!args.contains("-parameters")) {
                args.add("-parameters");
            }
            if (project.hasProperty("toolchainVersion")) {
                compile.setSourceCompatibility(SOURCE_AND_TARGET_COMPATIBILITY);
                compile.setTargetCompatibility(SOURCE_AND_TARGET_COMPATIBILITY);
            } else if (buildingWithJava17(project)) {
                args.addAll(Arrays.asList("-Werror", "-Xlint:unchecked", "-Xlint:deprecation", "-Xlint:rawtypes",
                        "-Xlint:varargs"));
            }
        });
    }

    private boolean buildingWithJava17(Project project) {
        return !project.hasProperty("toolchainVersion") && JavaVersion.current() == JavaVersion.VERSION_17;
    }

    private void configureJavaFormat(Project project) {
        project.getPlugins().apply(SpringJavaFormatPlugin.class);
        project.getPlugins().apply(SpotBugsPlugin.class);
        project.getPlugins().apply(CheckstylePlugin.class);
        SpotBugsExtension spotBugsExtension = project.getExtensions().getByType(SpotBugsExtension.class);
        //omitVisitors = [ 'FindNonShortCircuit' ]
        ////    reportsDir = file("$buildDir/spotbugs")
        ////    File includeFile = new File("$rootDir/config/findbugs/include.xml")
        ////    if (includeFile.exists()) {
        ////        includeFilter = file("$rootDir/config/findbugs/include.xml")
        ////    }
        ////    File excludeFile = new File("$rootDir/config/findbugs/exclude.xml")
        ////    if (excludeFile.exists()) {
        ////        excludeFilter = file("$rootDir/config/findbugs/exclude.xml")
        ////    }
        ////    maxHeapSize = '1g'
        project.getTasks().withType(SpotBugsTask.class, spotBugsTask -> {
            spotBugsTask.reports(
                    spotBugsReports -> {
                        spotBugsReports.forEach(
                                spotBugsReport -> {
                                    spotBugsReport.setStylesheet("fancy-hist.xsl");
                                    spotBugsReport.getOutputLocation().set(project.getProject().file("reports/spotbugs/main/spotbugs.html"));
                                    spotBugsReport.getRequired().set(true);
                                }

                        );
                    }
            );
        });
        spotBugsExtension.setProperty("showStackTraces", true);
        spotBugsExtension.setProperty("reportsDir", project.getProject().file("spotbugs"));
        spotBugsExtension.setProperty("includeFilter", project.getRootProject().file("config/findbugs/include.xml"));
        spotBugsExtension.setProperty("includeFilter", project.getRootProject().file("config/findbugs/exclude.xml"));
        spotBugsExtension.setProperty("maxHeapSize", "1g");
//		project.getExtensions().getByType(SpotBugsPlugin.class);
        CheckstyleExtension checkstyle = project.getExtensions().getByType(CheckstyleExtension.class);
        checkstyle.setToolVersion("8.45.1");
        checkstyle.getConfigDirectory().set(project.getRootProject().file("config/checkstyle"));
        String version = SpringJavaFormatPlugin.class.getPackage().getImplementationVersion();
        DependencySet checkstyleDependencies = project.getConfigurations().getByName("checkstyle").getDependencies();
        checkstyleDependencies
                .add(project.getDependencies().create("io.spring.javaformat:spring-javaformat-checkstyle:" + version));
    }


    private void configureToolchain(Project project) {
        project.getPlugins().apply(ToolchainPlugin.class);
    }
}
