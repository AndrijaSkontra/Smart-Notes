package controller;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import java.util.concurrent.atomic.AtomicReference;

public class UserService {

    private SessionFactory sessionFactory;

    public UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUserToDatabase(User user) throws ConstraintViolationException {
        sessionFactory.inTransaction(session -> session.persist(user));
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

    public boolean isUserDataValid(User user, String password) {
        return user.getPassword().equals(password);
    }
}