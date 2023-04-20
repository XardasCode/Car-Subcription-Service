package com.csub.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordManagerTest {
    @InjectMocks
    private PasswordManager passwordManager;

    @DisplayName("encryptPassword must return encrypted password")
    @Test
    void encryptPassword() {
        String actual = passwordManager.encryptPassword("enscryptTest");
        assertNotEquals("enscryptTest",actual);
    }
    @DisplayName("encryptPassword must return true if password and encrypted password are equal")
    @Test
    void checkPasswordMustBeEquals() {
        String password  = "enscryptTest";
        String enscryptedPassword = passwordManager.encryptPassword(password);
        boolean actual = passwordManager.checkPassword(password,enscryptedPassword);
        assertEquals(true,actual);
    }
    @DisplayName("encryptPassword must return false if password and encrypted password are not equal")
    @Test
    void checkPasswordMustBeNotEquals() {
        String password  = "enscryptTest";
        String enscryptedPassword = passwordManager.encryptPassword(password);
        boolean actual = passwordManager.checkPassword("password",enscryptedPassword);
        assertEquals(false,actual);
    }
}