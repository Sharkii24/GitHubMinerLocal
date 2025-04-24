package aiss.githubminer.service;

import aiss.githubminer.model.Issue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class IssueServiceTest {

    @Autowired
    IssueService service;

    @Test
    @DisplayName("List of Issues")
    void getIssuesRepo() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        List<Issue> issues = null;
        issues = service.getIssues(owner, repo);
        assertFalse(issues.isEmpty(), "The list of issues is empty!");
        System.out.println(issues);
    }

    @Test
    @DisplayName("Get an Issue")
    void getIssue() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        String number = "34810";
        Issue issue = null;
        issue = service.getIssueByNumber(owner, repo, number);
        assertFalse(issue == null, "The issue is null!");
        System.out.println(issue);
    }
}
