package com.duoc.backend;

import com.duoc.backend.dto.LoginRequest;
import com.duoc.backend.dto.UserRegistrationRequest;
import com.duoc.backend.dto.UserResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DTOTest {

    @Test
    void testLoginRequest() {
        LoginRequest req = new LoginRequest();
        req.setUsername("admin");
        req.setPassword("1234");
        assertEquals("admin", req.getUsername());
        assertEquals("1234", req.getPassword());

        LoginRequest req2 = new LoginRequest("user", "pass");
        assertEquals("user", req2.getUsername());
    }

    @Test
    void testUserRegistrationRequest() {
        UserRegistrationRequest req = new UserRegistrationRequest();
        req.setUsername("admin");
        req.setEmail("a@b.com");
        req.setPassword("1234");
        assertEquals("admin", req.getUsername());
        assertEquals("a@b.com", req.getEmail());
        assertEquals("1234", req.getPassword());
    }

    @Test
    void testUserResponse() {
        UserResponse res = new UserResponse(1, "admin", "a@b.com");
        assertEquals(1, res.getId());
        assertEquals("admin", res.getUsername());
        assertEquals("a@b.com", res.getEmail());
    }
}