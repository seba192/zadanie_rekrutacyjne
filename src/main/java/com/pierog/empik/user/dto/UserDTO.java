package com.pierog.empik.user.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDTO {
    private Long id;

    private String login;

    private String name;

    private String type;

    @JsonSetter("avatar_url")
    private String avatarUrl;

    @JsonSetter("created_at")
    private String createdAt;

    @JsonProperty(value = "public_repos", access = JsonProperty.Access.WRITE_ONLY)
    private Long publicRepos;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long followers;

    public Double getCalculations() {
        return 6.0 / followers * (2 + publicRepos);
    }

    @JsonGetter("avatarUrl")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @JsonGetter("createdAt")
    public String getCreatedAt() {
        return createdAt;
    }
}
