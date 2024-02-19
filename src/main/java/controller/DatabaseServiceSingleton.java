package controller;

import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import view.NotesPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseServiceSingleton {

    private static volatile DatabaseServiceSingleton instance;
    private DatabaseConnection dc;
    private SessionFactory sessionFactory;

    private DatabaseServiceSingleton(DatabaseConnection dc) {
        this.dc = dc;
        this.sessionFactory = dc.getSessionFactory();
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

    public void addUserToDatabase(User user) {
        sessionFactory.inTransaction(session -> session.persist(user));
    }

    public void addUserNoteToDatabase(UserNote userNote) {
        sessionFactory.inTransaction(session -> session.persist(userNote));
    }

    public User getUserFromDatabaseById(long id) {
        AtomicReference<User> userRef = new AtomicReference<>();
        sessionFactory.inTransaction(session -> userRef.set(session.find(User.class, id)));
        return userRef.get();
    }

    public User getUserFromDatabaseByUsername(String username) {
    AtomicReference<User> userRef = new AtomicReference<>();
    sessionFactory.inTransaction(session -> {
        String hql = "FROM User U WHERE U.username = :user_name";
        Query query = session.createQuery(hql);
        query.setParameter("user_name", username);
        User user = (User) query.uniqueResult();
        userRef.set(user);
    });
    return userRef.get();
}

    public UserNote getUserNoteFromDatabase(long id) {
        AtomicReference<UserNote> userNoteRef = new AtomicReference<>();
        sessionFactory.inTransaction(session -> userNoteRef.set(session.find(UserNote.class, id)));
        return userNoteRef.get();
    }

    public boolean isUserDataValid(User user, String password) {
        return user.getPassword().equals(password);
    }

    public ArrayList<UserNote> getAListOfUserNotes(User user) {
        Long userId = user.getId();
        ArrayList<UserNote> singleNotesFromUser = new ArrayList<>();
        sessionFactory.inTransaction(session -> {
            String hql = "FROM UserNote U WHERE U.user.id = :user_Id";
            Query query = session.createQuery(hql);
            query.setParameter("user_Id", userId);
            singleNotesFromUser.addAll(query.list());
        });
        return singleNotesFromUser;
    }

    public void deleteUserNoteFromDatabase(UserNote userNote) {
        sessionFactory.inTransaction(session -> session.remove(userNote));
    }
}
