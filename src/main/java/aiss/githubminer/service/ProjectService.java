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

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseuri" + "projects/")
    private String baseUri;

    public Project getProjectDetail(String owner, Integer id){
        String uri = baseUri+owner+"/projects/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);
        HttpEntity<Project> request = new HttpEntity<>(null, headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET, request, Project.class);
        return response.getBody();
    }

}
