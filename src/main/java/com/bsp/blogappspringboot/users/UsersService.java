package com.bsp.blogappspringboot.users;

import com.bsp.blogappspringboot.users.dtos.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(CreateUserRequest u) {
          var newUser = UserEntity.builder()
                  .username(u.getUsername())
                  .email(u.getEmail())
                  //.password(u.getPassword()) TODO: encrypt p
                  .build();
          return usersRepository.save(newUser);
    }

    public UserEntity getUser(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
    public UserEntity getUser(String userName) {
        return usersRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }

    public UserEntity loginUser(String userName, String password) {
        var user = this.getUser(userName);
        // TODO: match password
        return user;
    }
    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }

        public UserNotFoundException(Long id) {
            super("User with id: " + id + " not found");
        }
    }


}
