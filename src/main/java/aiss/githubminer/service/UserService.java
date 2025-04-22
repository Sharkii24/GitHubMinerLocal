package aiss.githubminer.service;

import aiss.githubminer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${githubminer.token}")
    private String token;

    @Value("${githubminer.baseUri}")
    private String baseUri;

    public List<User> getUsers() {
        String uri = baseUri + "/users";
        ResponseEntity<User[]> response = restTemplate.getForEntity(uri, User[].class);
        return Arrays.stream(response.getBody()).toList();
    }
}
