package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    public List<Issue> getIssues(String owner, String repo, String sinceIssues) {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC).minusDays(Integer.parseInt(sinceIssues));
        String since = dateTime.format(DateTimeFormatter.ISO_INSTANT);
        String uri = baseUri + owner + "/" + repo + "/issues?state=all&since=" + since;
        ResponseEntity<Issue[]> response = authorizationService.getWithToken(uri,Issue[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Issue> getIssuesMaxPages(String owner, String repo, String sinceIssues, String maxPages) {
        List<Issue> issues = new ArrayList<>();
        for (Integer i = 0; i < Integer.parseInt(maxPages); i++) {
            ZonedDateTime dateTime = ZonedDateTime.now(ZoneOffset.UTC).minusDays(Integer.parseInt(sinceIssues));
            String since = dateTime.format(DateTimeFormatter.ISO_INSTANT);
            String uri = baseUri + owner + "/" + repo + "/issues?state=all&since=" + since + "&page=" + i.toString();
            ResponseEntity<Issue[]> response = authorizationService.getWithToken(uri,Issue[].class);
            issues.addAll(Arrays.asList(response.getBody()));
        }
        return issues;
    }

    public Issue getIssueByNumber(String owner, String repo, String number) {
        String uri = baseUri + owner + "/" + repo + "/issues/" + number;
        ResponseEntity<Issue> response = authorizationService.getWithToken(uri, Issue.class);
        return response.getBody();
    }
}
