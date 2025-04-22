package aiss.githubminer.service;

import aiss.githubminer.model.Comment;
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
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseUri}" + "repos/")
    private String baseUri;

    // Service to list Comments
    public List<Comment> getComments(String owner, String repo) {
        String uri = baseUri + owner + "/" + repo + "/issues/comments";
        ResponseEntity<Comment[]> response = getCommentsToken(uri);
        return Arrays.asList(response.getBody());
    }

    private ResponseEntity<Comment[]> getCommentsToken(String uri) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+token);
            HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
            ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Service to list a Comment
    public Comment getCommentId(String owner, String repo, String id) {
        List<Comment> comments = getComments(owner, repo);
        return comments.stream().filter(x -> x.getId().equals(Integer.parseInt(id))).findFirst().orElse(null);
    }
}
