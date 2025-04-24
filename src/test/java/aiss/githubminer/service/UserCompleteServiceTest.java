package aiss.githubminer.service;

import aiss.githubminer.model.UserComplete;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserCompleteServiceTest {

    @Autowired
    UserCompleteService userCompleteService;

    @Test
    @DisplayName("List of Users")
    void getUsers() {
        List<UserComplete> users = null;
        users = userCompleteService.getUsers();
        assertFalse(users.isEmpty(), "The list of users is empty!");
        System.out.println(users);
    }

    @Test
    @DisplayName("User by name")
    void getUserByUsername() {
        String name = "IvanFM05";
        UserComplete user = null;
        user = userCompleteService.getUserByUsername(name);
        assertFalse(user == null, "The user is null!");
        System.out.println(user);
    }
}