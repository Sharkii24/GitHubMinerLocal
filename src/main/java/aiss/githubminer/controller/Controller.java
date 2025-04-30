package aiss.githubminer.controller;

import aiss.githubminer.etl.ProjectDB;
import aiss.githubminer.etl.Transform;
import aiss.githubminer.model.Commit;
import aiss.githubminer.model.Issue;
import aiss.githubminer.model.Project;
import aiss.githubminer.service.CommitService;
import aiss.githubminer.service.IssueService;
import aiss.githubminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/github")
public class Controller {

    private RestTemplate restTemplate;
    private ProjectService projectService;
    private CommitService commitService;
    private IssueService issueService;
    private Transform transform;

    @Autowired
    public Controller(ProjectService projectService, CommitService commitService, IssueService issueService, Transform transform, RestTemplate restTemplate) {
        this.projectService = projectService;
        this.commitService = commitService;
        this.issueService = issueService;
        this.transform = transform;
        this.restTemplate = restTemplate;
    }

    // http://localhost:8082/github/spring-projects/spring-framework
    @GetMapping("/{owner}/{repoName}")
    public ProjectDB getProject(@PathVariable String owner, @PathVariable String repoName,
                                @RequestParam(defaultValue = "2")String sinceCommits, @RequestParam(defaultValue = "20")String sinceIssues,
                                @RequestParam(defaultValue = "2")String maxPages){
        Project project = projectService.getProjectByName(owner, repoName);
        List<Commit> commits = commitService.getCommits(owner, repoName, sinceCommits);
        List<Issue> issues = issueService.getIssues(owner, repoName, sinceIssues);
        ProjectDB projectDB = transform.transform(project,commits,issues, owner, repoName);
        return projectDB;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{owner}/{repoName}")
    public ProjectDB createProject(@PathVariable String owner, @PathVariable String repoName,
                               @RequestParam(defaultValue = "2")String sinceCommits, @RequestParam(defaultValue = "20")String sinceIssues,
                               @RequestParam(defaultValue = "2")String maxPages) {
        Project project = projectService.getProjectByName(owner, repoName);
        List<Commit> commits = commitService.getCommits(owner, repoName, sinceCommits);
        List<Issue> issues = issueService.getIssues(owner, repoName, sinceIssues);
        ProjectDB projectDB = transform.transform(project,commits,issues, owner , repoName);
        String uri = "http://localhost:8080/gitminer/projects";
        HttpEntity<ProjectDB> request = new HttpEntity<>(projectDB);
        ResponseEntity<ProjectDB> response = restTemplate.exchange(uri, HttpMethod.POST, request, ProjectDB.class);
        return response.getBody();
    }
}
