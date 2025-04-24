package aiss.githubminer.service;

import aiss.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("List of Projects of an organization")
    void getProjects() {
        String owner = "spring-projects";
        List<Project> projects = null;
        projects = projectService.getProjects(owner);
        assertFalse(projects.isEmpty(), "The list of projects is empty!");
        System.out.println(projects);
    }

    @Test
    @DisplayName("List of Projects by name")
    void getProjectByName() {
        String owner = "spring-projects";
        String name = "spring-data-commons";
        Project project = null;
        project = projectService.getProjectByName(owner, name);
        assertFalse(project == null, "The project is null!");
        System.out.println(project);
    }
}