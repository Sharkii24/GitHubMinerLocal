package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    // Service to list Commits
    // {owner}/{repo}/commits
    public List<Commit> getCommits(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo + "/commits";
        ResponseEntity<Commit[]> response = authorizationService.getWithToken(uri, Commit[].class);
        return Arrays.asList(response.getBody());
    }

    // Service to list a Comment
    // https://api.github.com/repos/OWNER/REPO/commits/REF
    public Commit getCommitBySha(String owner, String repo, String sha) {
        String uri = baseUri + owner + "/" + repo + "/commits/" + sha;
        ResponseEntity<Commit> response = authorizationService.getWithToken(uri, Commit.class);
        return response.getBody();
    }
}
