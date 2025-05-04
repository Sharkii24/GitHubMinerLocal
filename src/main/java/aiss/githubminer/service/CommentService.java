package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    // Service to list Comments
    public List<Comment> getComments(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo + "/issues/comments";
        ResponseEntity<Comment[]> response = authorizationService.getWithToken(uri,Comment[].class);
        return Arrays.asList(response.getBody());
    }

    // Service to list a Comment
    public Comment getCommentById(String owner, String repo, String id) {
        String uri = baseUri + owner + "/" + repo + "/issues/comments/" + id;
        ResponseEntity<Comment> response = authorizationService.getWithToken(uri, Comment.class);
        return response.getBody();
    }

    // Service to list a Comment of an issue number
    public List<Comment> getCommentByIssueNumber(String owner, String repo, String issueNumber) {
        String uri = baseUri + owner + "/" + repo + "/issues/" + issueNumber + "/comments";
        ResponseEntity<Comment[]> response = authorizationService.getWithToken(uri, Comment[].class);
        return Arrays.asList(response.getBody());
    }

    public List<Comment> getCommentsMaxPages(String owner, String repo, String issueNumber, String maxPages) {
        List<Comment> comments = new ArrayList<>();
        for (Integer i = 0; i < Integer.parseInt(maxPages); i++) {
            String uri = baseUri + owner + "/" + repo + "/issues/" + issueNumber + "/comments?page=" + i.toString();
            ResponseEntity<Comment[]> response = authorizationService.getWithToken(uri, Comment[].class);
            comments.addAll(Arrays.asList(response.getBody()));
        }
        return comments;
    }
}
