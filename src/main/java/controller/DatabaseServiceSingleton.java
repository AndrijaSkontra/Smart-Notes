package controller;

import model.User;
import model.UserNote;
import model.UserSubscription;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import view.NotesPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

    public void addUserToDatabase(User user) throws ConstraintViolationException {
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

    public void deleteUserNoteFromDatabase(UserNote userNote) {
        sessionFactory.inTransaction(session -> session.remove(userNote));
    }

    public List<User> getSubscribedUsers(User user) {
        return null;
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

    public List<User> getAvailableToSubscribeUsers(User user) {
        ArrayList<User> allUsersExceptUserInParam = new ArrayList<>();
        ArrayList<UserSubscription> userSubscriptionList = new ArrayList<>();

        sessionFactory.inTransaction(session -> {
            String hql = "FROM User U WHERE U.id != :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", user.getId());
            allUsersExceptUserInParam.addAll(query.list());
        });
        // this is not really a good way to do this, but it's a start
        sessionFactory.inTransaction(session -> {
            String hql = "FROM UserSubscription U";
            Query query = session.createQuery(hql);
            userSubscriptionList.addAll(query.list());
        });

        for (UserSubscription us : userSubscriptionList) {
            if (us.getSubscriber().equals(user)) {
                allUsersExceptUserInParam.remove(us.getSubscribedTo());
            }
        }

        return allUsersExceptUserInParam;
    }

    public void subscribeUser(User user, User subscribedToUser) {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriber(user);
        userSubscription.setSubscribedTo(subscribedToUser);
        sessionFactory.inTransaction(session -> session.persist(userSubscription));
    }
}
