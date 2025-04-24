package aiss.githubminer.service;

import aiss.githubminer.model.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;

    @Test
    @DisplayName("Get Projects of an organization")
    void getProjectDetail() {
        String owner = "github";
        Integer number = 5;
        List<Project> projects = null;
        projects = projectService.getProjects(owner, number);
        assertNotNull(projects);
        System.out.println(projects);
    }
}