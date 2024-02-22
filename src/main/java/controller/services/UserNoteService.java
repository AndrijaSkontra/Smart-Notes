package controller.services;

import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class UserNoteService {

    private SessionFactory sessionFactory;

    public UserNoteService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addUserNoteToDatabase(UserNote userNote) {
        sessionFactory.inTransaction(session -> session.persist(userNote));
    }

    public UserNote getUserNoteFromDatabase(long id) {
        AtomicReference<UserNote> userNoteRef = new AtomicReference<>();
        sessionFactory.inTransaction(session -> userNoteRef.set(session.find(UserNote.class, id)));
        return userNoteRef.get();
    }

    public void deleteUserNoteFromDatabase(UserNote userNote) {
        sessionFactory.inTransaction(session -> session.remove(userNote));
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
}