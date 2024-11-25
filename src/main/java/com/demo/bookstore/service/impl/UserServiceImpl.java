package com.demo.bookstore.service.impl;

import com.demo.bookstore.common.exception.BadRequestException;
import com.demo.bookstore.common.util.BeanUtil;
import com.demo.bookstore.dao.UserDao;
import com.demo.bookstore.dto.UserDTO;
import com.demo.bookstore.entity.Book;
import com.demo.bookstore.entity.User;
import com.demo.bookstore.security.JwtTokenUtil;
import com.demo.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    private UserDao userDao;

    @Value("${jwt.prefix}")
    private String tokenHead;

    @Autowired
    public UserServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDao = userDao;
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
            throw  new BadRequestException("username and password cannot be null!");
        }
        if (userDao.findByUsername(userDTO.getUsername()) != null) {
            throw new BadRequestException("username has been registered!");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = BeanUtil.copyProperties(userDTO, new User());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user = userDao.save(user);
        return BeanUtil.copyProperties(user, userDTO);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public UserDTO find(String username) {
        User user = userDao.findByUsername(username);
        UserDTO dto = new UserDTO();
        dto.setRealName(user.getRealName());
        dto.setMobile(user.getMobile());
        dto.setUsername(user.getUsername());
        dto.setAddress(user.getAddress());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
