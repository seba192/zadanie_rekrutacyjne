package com.pierog.empik.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "LOGIN", unique = true)
    private String login;

    @Column(name = "REQUEST_COUNT")
    private Long requestCount = 0L;

    public UserEntity(String login) {
        this.login = login;
    }

    public void incrementRequestCount() {
        this.requestCount++;
    }
}
