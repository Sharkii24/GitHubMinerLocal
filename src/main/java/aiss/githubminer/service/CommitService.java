package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;

@Service
public class CommitService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    // Service to list Commits
    // {owner}/{repo}/commits
    public List<Commit> getCommits(String owner, String repo, String sinceCommits) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC).minusDays(Integer.parseInt(sinceCommits));
        String since = dateTime.format(DateTimeFormatter.ISO_INSTANT);
        String uri = baseUri + owner + "/" + repo + "/commits?since=" + since;
        ResponseEntity<Commit[]> response = authorizationService.getWithToken(uri, Commit[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Commit> getCommitsMaxPages(String owner, String repo, String sinceCommits, String maxPages) {
        List<Commit> commits = new ArrayList<>();
        for (Integer i = 0; i < Integer.parseInt(maxPages); i++) {
            ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC).minusDays(Integer.parseInt(sinceCommits));
            String since = dateTime.format(DateTimeFormatter.ISO_INSTANT);
            String uri = baseUri + owner + "/" + repo + "/commits?since=" + since + "&page=" + i.toString();
            ResponseEntity<Commit[]> response = authorizationService.getWithToken(uri, Commit[].class);
            commits.addAll(Arrays.asList(response.getBody()));
        }
        return commits;
    }

    // Service to list a Comment
    // https://api.github.com/repos/OWNER/REPO/commits/REF
    public Commit getCommitBySha(String owner, String repo, String sha) {
        String uri = baseUri + owner + "/" + repo + "/commits/" + sha;
        ResponseEntity<Commit> response = authorizationService.getWithToken(uri, Commit.class);
        return response.getBody();
    }
}
