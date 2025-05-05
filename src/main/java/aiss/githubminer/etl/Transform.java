package aiss.githubminer.etl;

import aiss.githubminer.model.*;
import aiss.githubminer.service.CommentService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class Transform {

    private final CommentService commentService;

    public Transform(CommentService commentService) {
        this.commentService = commentService;
    }

    public ProjectDB transform(Project project, List<Commit> commits, List<Issue> issues, String owner, String repoName, String maxPages){
        ProjectDB projectDB = transformProject(project);
        transformIssues(issues, projectDB, owner, repoName, maxPages);
        transformCommits(commits, projectDB);
        return projectDB;
    }

    public ProjectDB transformProject(Project project){
        return new ProjectDB(project.getId(), project.getName(), project.getUrl());
    }

    public void transformIssues(List<Issue> issues, ProjectDB project, String owner, String repoName, String maxPages) {
        for (Issue issue : issues) {
            List<Label> label1 = issue.getLabels();
            List<String> labels = getLabels(label1);
            UserDB author = transformUser(issue.getUser());
            UserDB assignee = null;
            if (issue.getAssignee() != null) {
                assignee = transformUser(issue.getAssignee());
            }
            Integer votes = issue.getReactions().getTotalCount();
            String closedAt = issue.getClosedAt();
            if (closedAt == null) {
                closedAt = "NOT CLOSED YET";
            }
            IssueDB issueDB = new IssueDB(issue.getId(),issue.getTitle(), issue.getBody(), issue.getState(),
                    issue.getCreatedAt(), issue.getUpdatedAt(), closedAt, labels, votes, author, assignee);
            project.getIssues().add(issueDB);
            List<Comment> comments = commentService.getCommentsMaxPages(owner, repoName, issue.getNumber().toString(), maxPages);
            transformComments(comments,issueDB);
        }
    }

    private List<String> getLabels(List<Label> labels){
        List<String> res = new ArrayList<>();
        for (Label label : labels) {
            res.add(label.getName());
        }
        return res;
    }

    public void transformCommits(List<Commit> commits, ProjectDB project) {
        for (Commit commit : commits) {
            Author author = commit.getCommit().getAuthor();
            String title = commit.getCommit().getMessage();
            if (title.length() > 255) {
                title = title.substring(0, 255);
            }
            CommitDB commitDB = new CommitDB(commit.getSha(), title, commit.getCommit().getMessage(),
                    author.getName(), author.getEmail(), author.getDate(), commit.getUrl());
            project.getCommits().add(commitDB);
        }
    }

    public void transformComments(List<Comment> comments, IssueDB issue) {
        for (Comment comment : comments) {
            UserDB author = transformUser(comment.getUser());
            CommentDB commentDB = new CommentDB(comment.getId(),comment.getBody(),comment.getCreatedAt(),
                    comment.getUpdatedAt(), author);
            issue.getComments().add(commentDB);
        }
    }

    public UserDB transformUser(User user) {
        String name = user.getLogin();
        return new UserDB(user.getId(), user.getLogin(), name, user.getAvatarUrl(), user.getUrl());
    }
}
