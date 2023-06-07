package com.bsp.blogappspringboot.users;

import com.bsp.blogappspringboot.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserRequest u) {
        UserEntity newUser = modelMapper.map(u, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));
        // TODO: encrypt and save password as well
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
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException(userName);
        }
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

    public static class InvalidCredentialsException extends IllegalArgumentException {
        public InvalidCredentialsException(String username) {
            super("The credentials entered for user: " + username + " is incorrect");
        }
    }


}
