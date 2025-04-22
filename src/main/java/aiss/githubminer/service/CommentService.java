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
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    /*
    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseUri}")
    private String baseUri;

     */

    // Service to list Comments
    public List<Comment> getComments(String owner, String repo) {
        // String uri = baseUri + owner + "/" + repo + "/issues/comments";
        String uri = "https://api.github.com/repos/" + owner + "/" + repo + "/issues/comments";
        // ResponseEntity<Comment[]> response = getCommentsToken(uri);
        // return Arrays.asList(response.getBody());
        Comment[] comments = restTemplate.getForObject(uri, Comment[].class);
        return Arrays.stream(comments).toList();
    }

    /*
    public ResponseEntity<Comment[]> getCommentsToken(String uri) {
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

    public ResponseEntity<Comment[]> getCommentToken2(){
        HttpHeaders headers = new HttpHeaders();
        String owner = "spring-projects";
        String repo = "spring-framework";
        String uri = "https://api.github.com/repos/spring-projects/spring-framework/issues/comments";
        System.out.println(uri);
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET,request,Comment[].class);
        return response;

    }


    // Service to list a Comment
    public Comment getCommentDetail(String owner, String repo, String id) {
        String uri = baseUri + owner + "/" + repo + "/issues/comments/" + id;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+token);
            HttpEntity<Comment> request = new HttpEntity<>(null, headers);
            ResponseEntity<Comment> comment = restTemplate.exchange(uri, HttpMethod.GET, request, Comment.class);
            return comment.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

     */
}
