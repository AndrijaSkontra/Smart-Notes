package controller;

import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
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
        User retrievedUser = databaseService.getUserFromDatabase(user.getId());
        assertNotNull(retrievedUser);
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
    }

    @Test
    public void testAddAndGetUserNote() {
        User user = new User("TestUser", "TestPassword");
        UserNote userNote = new UserNote();
        userNote.setContent("TestNote");
        userNote.setDateMade(LocalDateTime.now());
        userNote.setUser(user);
        userNote.setDateMade(LocalDateTime.now());

        databaseService.addUserToDatabase(user);
        databaseService.addUserNoteToDatabase(userNote);
        User retrievedUser = databaseService.getUserFromDatabase(user.getId());
        assertNotNull(retrievedUser);
        UserNote retrievedNote = databaseService.getUserNoteFromDatabase(userNote.getId());
        assertNotNull(retrievedNote);
        assertEquals(userNote.getContent(), retrievedNote.getContent());
}

}