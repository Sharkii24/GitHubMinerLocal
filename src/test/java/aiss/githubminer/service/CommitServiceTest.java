package aiss.githubminer.service;

import aiss.githubminer.model.Commit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceTest {

    @Autowired
    CommitService commitService;

    @Test
    @DisplayName("List of Commits")
    void getCommits() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        String sinceCommits = "5";
        List<Commit> commits = null;
        commits = commitService.getCommits(owner, repo, sinceCommits);
        assertFalse(commits.isEmpty(), "The list of commits is empty!");
        System.out.println(commits);
    }

    @Test
    @DisplayName("Get commit by sha")
    void getCommitBySha() {
        String owner = "spring-projects";
        String repo = "spring-framework";
        String sha = "ead76b63174fc152e9a9249fab6da15199b62a43";
        Commit commit = commitService.getCommitBySha(owner, repo, sha);
        System.out.println(commit);
    }
}