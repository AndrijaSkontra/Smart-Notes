package controller;

import model.User;
import model.UserNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseServiceSingletonTest {

    private DatabaseServiceSingleton databaseServiceSingleton;

    @BeforeEach
    public void setUp() {
        UsersDatabaseConnection udc = new UsersDatabaseConnection();
        // databaseServiceSingleton = new DatabaseServiceSingleton(udc);
    }

    @Test
    public void testAddAndGetUser() {
        User user = new User("TestUser", "TestPassword");
        databaseServiceSingleton.addUserToDatabase(user);
        User retrievedUser = databaseServiceSingleton.getUserFromDatabaseById(user.getId());
        assertNotNull(retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testAddAndGetUserNote() {
        User user = new User("noviuser1", "TestPassword");
        UserNote userNote = new UserNote();
        userNote.setContent("TestNote");
        userNote.setDateMade(LocalDateTime.now());
        userNote.setUser(user);
        userNote.setDateMade(LocalDateTime.now());

        databaseServiceSingleton.addUserToDatabase(user);
        databaseServiceSingleton.addUserNoteToDatabase(userNote);
        User retrievedUser = databaseServiceSingleton.getUserFromDatabaseByUsername(user.getUsername());
        assertNotNull(retrievedUser);
        UserNote retrievedNote = databaseServiceSingleton.getUserNoteFromDatabase(userNote.getId());
        assertNotNull(retrievedNote);
        assertEquals(userNote.getContent(), retrievedNote.getContent());
}

}