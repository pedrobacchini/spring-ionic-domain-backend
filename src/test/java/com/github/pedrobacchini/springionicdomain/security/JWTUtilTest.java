package com.github.pedrobacchini.springionicdomain.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Test
    public void getUsernameTestInValid() {
        String username = jwtUtil.getUsername("sahdkjghasjkgh");
        Assert.assertNull(username);
    }

    @Test
    public void getUsernameTestValid() {
        String token = jwtUtil.generateToken("name@email.com");
        String username = jwtUtil.getUsername(token);
        Assert.assertEquals(username, "name@email.com");
    }

    @Test
    public void generateToken() {
        String token = jwtUtil.generateToken("name@email.com");
        Assert.assertNotNull(token);
    }

    @Test
    public void tokenValid() {
        String token = jwtUtil.generateToken("name@email.com");
        boolean isValid = jwtUtil.tokenValid(token);
        Assert.assertTrue(isValid);
    }

    @Test
    public void tokenInvalid() {
        boolean isValid = jwtUtil.tokenValid("dasjhdjkashjk");
        Assert.assertFalse(isValid);
    }
}