package com.bsp.blogappspringboot.users;

import com.bsp.blogappspringboot.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsersServiceTests {

    @Autowired
    private UsersService usersService;

    @Test
    void can_create_users() {
        var user = usersService.createUser(new CreateUserRequest(
                "bsp",
                "bsp123",
                "bsp@dev.com"
        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("bsp", user.getUsername());
    }

}
