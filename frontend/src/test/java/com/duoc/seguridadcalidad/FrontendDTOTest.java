package com.duoc.seguridadcalidad;

import com.duoc.seguridadcalidad.dto.AuthRequest;
import com.duoc.seguridadcalidad.dto.AuthResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FrontendDTOTest {

    @Test
    void testAuthRequest() {
        AuthRequest req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("1234");
        
        assertEquals("admin", req.getUsername());
        assertEquals("1234", req.getPassword());
    }

    @Test
    void testAuthResponse() {
        AuthResponse res = new AuthResponse("testToken");
        assertEquals("testToken", res.getToken());
    }
}