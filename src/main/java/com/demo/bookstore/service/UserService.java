package com.demo.bookstore.service;

import com.demo.bookstore.dto.UserDTO;

public interface UserService {
    UserDTO register(UserDTO userDTO);
    String login(String username, String password);
    UserDTO find(String username);
}
