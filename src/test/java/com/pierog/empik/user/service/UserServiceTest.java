package com.pierog.empik.user.service;

import com.pierog.empik.github.repository.GithubUserRepository;
import com.pierog.empik.user.dto.UserDTO;
import com.pierog.empik.user.entity.UserEntity;
import com.pierog.empik.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {
    @Mock
    private GithubUserRepository githubUserRepository;

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void init() {
        userService = new UserService(githubUserRepository, userRepository);
    }

    @Test
    public void shouldReturnUser() {
        UserDTO gitUser = UserDTO.builder()
                .name("test")
                .id(1L)
                .type("User")
                .login("test")
                .createdAt("create")
                .avatarUrl("avatar url")
                .followers(2L)
                .publicRepos(1L)
                .build();
        when(githubUserRepository.getUser("test")).thenReturn(Optional.of(gitUser));

        Optional<UserDTO> actualUser = userService.getUser("test");
        UserDTO expectedUser = UserDTO.builder()
                .name("test")
                .id(1L)
                .type("User")
                .login("test")
                .createdAt("create")
                .avatarUrl("avatar url")
                .followers(2L)
                .publicRepos(1L)
                .build();
        Assertions.assertThat(actualUser.isPresent()).isTrue();
        Assertions.assertThat(actualUser.get()).isEqualTo(expectedUser);
        Assertions.assertThat(actualUser.get().getCalculations()).isEqualTo(9.0);

        Optional<UserEntity> entity = userRepository.findById("test");
        Assertions.assertThat(entity.isEmpty()).isFalse();
        Assertions.assertThat(entity.get().getLogin()).isEqualTo("test");
        Assertions.assertThat(entity.get().getRequestCount()).isEqualTo(1);
    }

    @Test
    public void shouldAdd2Users() {
        UserDTO gitUser = UserDTO.builder()
                .name("test")
                .id(1L)
                .type("User")
                .login("test")
                .createdAt("create")
                .avatarUrl("avatar url")
                .followers(2L)
                .publicRepos(1L)
                .build();
        when(githubUserRepository.getUser("test")).thenReturn(Optional.of(gitUser));

        userService.getUser("test");
        userService.getUser("test");

        Optional<UserEntity> entity = userRepository.findById("test");
        Assertions.assertThat(entity.isEmpty()).isFalse();
        Assertions.assertThat(entity.get().getLogin()).isEqualTo("test");
        Assertions.assertThat(entity.get().getRequestCount()).isEqualTo(2);
    }

    @Test
    public void shouldNotFound() {
        when(githubUserRepository.getUser("test")).thenReturn(Optional.empty());
        Assertions.assertThat(userService.getUser("test").isPresent()).isFalse();

        Optional<UserEntity> entity = userRepository.findById("test");
        Assertions.assertThat(entity.isEmpty()).isFalse();
        Assertions.assertThat(entity.get().getLogin()).isEqualTo("test");
        Assertions.assertThat(entity.get().getRequestCount()).isEqualTo(1);
    }

    @Test
    public void shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> userService.getUser(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Login can not be null");
    }
}
