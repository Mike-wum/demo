package com.demo.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import javax.annotation.PostConstruct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {
    @Autowired
    protected TestRestTemplate restTemplate;
    @LocalServerPort
    protected int port;

    @Value("${jwt.header}")
    protected String tokenHeader;

    @Value("${jwt.prefix}")
    protected String tokenPrefix;

    protected String token;

    @PostConstruct
    public void init() {
        token = tokenPrefix + "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsIm5iZiI6MTczMjQxNTE2OSwicGVybWlzc2lvbiI6Im5vLXVzZSIsImV4cCI6MTczMzAxOTk2OSwiaWF0IjoxNzMyNDE1MTY5LCJ1c2VybmFtZSI6ImFkbWluIn0.sz0lDrECG1T-p5J_5LVaeoDlJXn_2WW4o6kWZeFZkz5Xzb1ihfmNAktcMWHYNtEKBNFOiIIaFYsMLvQP8GWtaA";
    }
}
