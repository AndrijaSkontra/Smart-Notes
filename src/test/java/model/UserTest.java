package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("Andrija", "Kokafaca");
        Assertions.assertEquals("Andrija", user.getUsername());
        Assertions.assertEquals("Kokafaca", user.getPassword());
    }
}
