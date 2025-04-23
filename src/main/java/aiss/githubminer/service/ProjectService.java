package aiss.githubminer.service;

import aiss.githubminer.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseuri}" + "graphql")
    private String baseUri;

    public List<Project> getProjects(String owner, Integer num) {
        String uri = baseUri;
        ResponseEntity<Project[]> response = getProjectsToken(uri);
        return Arrays.asList(response.getBody());
    }

    private ResponseEntity<Project[]> getProjectsToken(String uri) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            String query = """
                    query {
                          organization(login: "github") {
                            projectsV2(first: 100) {
                              nodes {
                                id
                                number
                                title
                                url
                              }
                            }
                          }
                        }
                    """;
            Map<String, String> body = new HashMap<>();
            body.put("query", query);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<Project[]> response = restTemplate.exchange(uri, HttpMethod.POST, request, Project[].class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
