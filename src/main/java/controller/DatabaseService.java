package controller;

import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.concurrent.atomic.AtomicReference;

public class DatabaseService {

    private DatabaseConnection dc;
    private SessionFactory sessionFactory;

    public DatabaseService(DatabaseConnection dc) {
        this.dc = dc;
        this.sessionFactory = dc.getSessionFactory();
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
}
