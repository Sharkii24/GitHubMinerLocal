package aiss.githubminer.service;

import aiss.githubminer.authorizationService.AuthorizationService;
import aiss.githubminer.model.UserComplete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class UserCompleteService {

    @Autowired
    AuthorizationService authorizationService;

    @Value("${githubminer.baseUri}" + "users")
    private String baseUri;

    public List<UserComplete> getUsers() {
        String uri = baseUri;
        ResponseEntity<UserComplete[]> response = authorizationService.getWithToken(uri, UserComplete[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    public UserComplete getUserByUsername(String username) {
        String uri = baseUri + "/" + username;
        ResponseEntity<UserComplete> response = authorizationService.getWithToken(uri, UserComplete.class);
        return response.getBody();
    }
}
