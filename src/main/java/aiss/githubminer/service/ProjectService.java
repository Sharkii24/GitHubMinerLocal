package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}")
    private String baseUri;

    public List<Project> getProjects(String owner) {
        String uri = baseUri + "orgs/" + owner + "/repos";
        ResponseEntity<Project[]> response = authorizationService.getWithToken(uri, Project[].class);
        return Arrays.asList(response.getBody());
    }

    public Project getProjectByName(String owner, String name) {
        String uri = baseUri + "repos/" + owner + "/" + name;
        ResponseEntity<Project> response = authorizationService.getWithToken(uri, Project.class);
        return response.getBody();
    }
}
