package aiss.githubminer.service;

import aiss.githubminer.model.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Test
    @DisplayName("List of Comments")
    void getComments() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        List<Comment> comments = null;
        comments = commentService.getComments(owner, repo);
        assertFalse(comments.isEmpty(), "The list of comments is empty!");
        System.out.println(comments);
    }

    @Test
    @DisplayName("List of Comment with a ID")
    void getCommentDetail() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        String id = "453283970";
        Comment comment = null;
        comment = commentService.getCommentId(owner, repo, id);
        assertFalse(comment == null, "The comment is null!");
        System.out.println(comment);
    }
}