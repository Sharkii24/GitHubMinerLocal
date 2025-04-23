package aiss.githubminer.service;

import aiss.githubminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    public List<Issue> getIssues(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo + "/issues?per_page=2";
        ResponseEntity<Issue[]> response = getIssuesToken(uri);
        return Arrays.asList(response.getBody());
    }

    private ResponseEntity<Issue[]> getIssuesToken(String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Issue[]> request = new HttpEntity<>(null,headers);
        ResponseEntity<Issue[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        return response;
    }

//   /repos/{owner}/{repo}/issues/{issue_number}
    public Issue getIssueId(String owner, String repo, String number) {
        // List<Issue> issues = getIssues(owner, repo);
        // return issues.stream().filter(i -> i.getId().equals(Integer.parseInt(id))).findFirst().orElse(null);
        String uri = baseUri + owner + "/" + repo + "/issues/" + number;
        ResponseEntity<Issue[]> response = getIssuesToken(uri);
        return Arrays.asList(response.getBody()).get(0);
    }
}
