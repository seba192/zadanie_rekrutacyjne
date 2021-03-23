package com.pierog.empik.user.controller;

import com.pierog.empik.user.dto.UserDTO;
import com.pierog.empik.user.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

@RestController
@RequestMapping("/users")
@Api(value = "User controller", tags = {"User"})
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{login}")
    @ApiOperation(value = "Get user")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "User", response = UserDTO.class),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User not found")
    })
    public ResponseEntity<UserDTO> getUser(@ApiParam(name = "User login", value = "login", required = true) @PathVariable String login) {
        Optional<UserDTO> user = userService.getUser(login);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

}
