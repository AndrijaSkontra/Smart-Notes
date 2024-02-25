package controller.services;

import controller.DatabaseConnection;
import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for managing services that query data,
 * and sending the connection to those services.
 * This class is a singleton. Invoke getInstance() to get the instance.
 */
public class DatabaseServiceSingleton {

    private static volatile DatabaseServiceSingleton instance;
    private DatabaseConnection dc;
    private SessionFactory sessionFactory;
    private UserService userService;
    private UserNoteService userNoteService;
    private UserSubscriptionService userSubscriptionService;

    private DatabaseServiceSingleton(DatabaseConnection dc) {
        this.dc = dc;
        this.sessionFactory = dc.getSessionFactory();
        this.userService = new UserService(sessionFactory);
        this.userNoteService = new UserNoteService(sessionFactory);
        this.userSubscriptionService = new UserSubscriptionService(sessionFactory);
    }

    public static DatabaseServiceSingleton getInstance(DatabaseConnection dc) {
        if (instance == null) {
            synchronized (DatabaseServiceSingleton.class) {
                if (instance == null) {
                    instance = new DatabaseServiceSingleton(dc);
                }
            }
        }
        return instance;
    }

    public void addUserToDatabase(User user) throws ConstraintViolationException {
        userService.addUserToDatabase(user);
    }

    public void addUserNoteToDatabase(UserNote userNote) {
        userNoteService.addUserNoteToDatabase(userNote);
    }

    public User getUserFromDatabaseById(long id) {
        return userService.getUserFromDatabaseById(id);
    }

    public User getUserFromDatabaseByUsername(String username) {
        return userService.getUserFromDatabaseByUsername(username);
    }

    public UserNote getUserNoteFromDatabase(long id) {
        return userNoteService.getUserNoteFromDatabase(id);
    }

    public void deleteUserNoteFromDatabase(UserNote userNote) {
        userNoteService.deleteUserNoteFromDatabase(userNote);
    }

    public List<User> getAllUserSubscriptions(User user) {
        return userSubscriptionService.getAllUserSubscriptions(user);
    }

    public ArrayList<UserNote> getAListOfUserNotes(User user) {
        return userNoteService.getAListOfUserNotes(user);
    }

    public List<User> getAvailableToSubscribeUsers(User user) {
        return userSubscriptionService.getAvailableToSubscribeUsers(user);
    }

    public void subscribeUser(User user, User subscribedToUser) {
        userSubscriptionService.subscribeUser(user, subscribedToUser);
    }

    public void unsubscribeUser(User user, User selectedUser) {
        userSubscriptionService.unsubscribeUser(user, selectedUser);
    }

    public ArrayList<UserNote> getAListOfSubscribedUserNotes(User user) {
        return userSubscriptionService.getAListOfSubscribedUserNotes(user);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

    public List<User> getAllSubscribedToUser(User user) {
        return userSubscriptionService.getAllSubscribedToUser(user);
    }

    public ArrayList<UserNote> getAListOfUserNotificationNotes(User user) {
        return userNoteService.getAListOfUserNotificationNotes(user);
    }
}