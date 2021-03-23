package com.pierog.empik.user.service;

import com.pierog.empik.github.repository.GithubUserRepository;
import com.pierog.empik.user.dto.UserDTO;
import com.pierog.empik.user.entity.UserEntity;
import com.pierog.empik.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final GithubUserRepository githubUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(GithubUserRepository githubUserRepository, UserRepository userRepository) {
        this.githubUserRepository = githubUserRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<UserDTO> getUser(String login) {
        if(login == null) {
            throw new IllegalArgumentException("Login can not be null");
        }

        Optional<UserDTO> user = githubUserRepository.getUser(login);
        updateUserDB(login);
        return user;
    }

    private void updateUserDB(String login) {
        UserEntity user = userRepository.findById(login)
                .orElseGet(() -> new UserEntity(login));

        user.incrementRequestCount();
        userRepository.save(user);
    }

}
