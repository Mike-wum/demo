package com.demo.bookstore;

import com.demo.bookstore.dto.UserDTO;
import com.demo.bookstore.security.JwtAuthenticationRequest;
import com.demo.bookstore.security.JwtAuthenticationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests extends BaseTest {
    @Test
    public void testRegister() {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("myaddr");
        userDTO.setEmail("myemail");
        userDTO.setUsername("admin");
        userDTO.setPassword("123456");
        userDTO.setMobile("131123456");
        userDTO.setRealName("myrealname");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/user/auth/register",
                HttpMethod.POST,
                entity,
                UserDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAddress()).isEqualTo("myaddr");
        assertThat(response.getBody().getEmail()).isEqualTo("myemail");
        assertThat(response.getBody().getUsername()).isEqualTo("admin");
        assertThat(response.getBody().getMobile()).isEqualTo("131123456");
        assertThat(response.getBody().getRealName()).isEqualTo("myrealname");
    }

    @Test
    public void testLogin() {
        JwtAuthenticationRequest request = new JwtAuthenticationRequest("admin","123456");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JwtAuthenticationRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/user/auth/login",
                HttpMethod.POST,
                entity,
                JwtAuthenticationResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println("token:" + response.getBody().getToken());
    }

    @Test
    public void testQuery() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(tokenHeader, token);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(
                "http://localhost:" + port + "/bookstore/api/user/name/admin",
                HttpMethod.GET,
                entity,
                UserDTO.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAddress()).isEqualTo("myaddr");
        assertThat(response.getBody().getEmail()).isEqualTo("myemail");
        assertThat(response.getBody().getUsername()).isEqualTo("admin");
        assertThat(response.getBody().getMobile()).isEqualTo("131123456");
        assertThat(response.getBody().getRealName()).isEqualTo("myrealname");
    }
}
