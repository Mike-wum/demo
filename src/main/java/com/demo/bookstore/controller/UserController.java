package com.demo.bookstore.controller;

import com.demo.bookstore.dto.UserDTO;
import com.demo.bookstore.security.JwtAuthenticationRequest;
import com.demo.bookstore.security.JwtAuthenticationResponse;
import com.demo.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        final String token = userService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        log.info("token generated:" + token);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserDTO user) throws AuthenticationException{
        UserDTO dto = userService.register(user);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body("Error! [" + user.getUsername() + "] has been registered! Or password is null!");
        }
    }

    @GetMapping(value = "/name/{username}")
    public UserDTO get(@PathVariable("username") String username) {
        return userService.find(username);
    }
}
