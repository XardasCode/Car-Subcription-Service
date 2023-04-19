package com.csub.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordManagerTest {
    @InjectMocks
    private PasswordManager passwordManager;

    @Test
    void encryptPassword() {
        String actual = passwordManager.encryptPassword("enscryptTest");
        assertNotEquals("enscryptTest",actual);
    }
    @Test
    void checkPasswordMustBeEquals() {
        String password  = "enscryptTest";
        String enscryptedPassword = passwordManager.encryptPassword(password);
        boolean actual = passwordManager.checkPassword(password,enscryptedPassword);
        assertEquals(true,actual);
    }
    @Test
    void checkPasswordMustBeNotEquals() {
        String password  = "enscryptTest";
        String enscryptedPassword = passwordManager.encryptPassword(password);
        boolean actual = passwordManager.checkPassword("password",enscryptedPassword);
        assertEquals(false,actual);
    }
}