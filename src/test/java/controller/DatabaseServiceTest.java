package controller;

import model.User;
import model.UserNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseServiceTest {

    private DatabaseService databaseService;

    @BeforeEach
    public void setUp() {
        UsersDatabaseConnection udc = new UsersDatabaseConnection();
        databaseService = new DatabaseService(udc);
    }

    @Test
    public void testAddAndGetUser() {
        User user = new User("TestUser", "TestPassword");
        databaseService.addUserToDatabase(user);
        User retrievedUser = databaseService.getUserFromDatabaseById(user.getId());
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

        databaseService.addUserToDatabase(user);
        databaseService.addUserNoteToDatabase(userNote);
        User retrievedUser = databaseService.getUserFromDatabaseByUsername(user.getUsername());
        assertNotNull(retrievedUser);
        UserNote retrievedNote = databaseService.getUserNoteFromDatabase(userNote.getId());
        assertNotNull(retrievedNote);
        assertEquals(userNote.getContent(), retrievedNote.getContent());
}

}