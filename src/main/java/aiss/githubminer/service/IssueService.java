package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    public List<Issue> getIssues(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo + "/issues?per_page=5";
        ResponseEntity<Issue[]> response = authorizationService.getWithToken(uri,Issue[].class);
        return Arrays.asList(response.getBody());
    }

    public Issue getIssueByNumber(String owner, String repo, String number) {
        String uri = baseUri + owner + "/" + repo + "/issues/" + number;
        ResponseEntity<Issue> response = authorizationService.getWithToken(uri, Issue.class);
        return response.getBody();
    }
}
