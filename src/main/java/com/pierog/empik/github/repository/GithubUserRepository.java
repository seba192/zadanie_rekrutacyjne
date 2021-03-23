package com.pierog.empik.github.repository;

import com.pierog.empik.github.GithubProperties;
import com.pierog.empik.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Repository
public class GithubUserRepository {
    private final GithubProperties gitHubProperties;

    @Autowired
    public GithubUserRepository(GithubProperties gitHubProperties) {
        this.gitHubProperties = gitHubProperties;
    }

    public Optional<UserDTO> getUser(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(gitHubProperties.getUserUrl())
                .pathSegment(name)
                .build()
                .toUriString();
        try{
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(url, UserDTO.class);
            return Optional.ofNullable(response.getBody());
        }catch (HttpStatusCodeException exception){
            return Optional.empty();
        }
    }
}
