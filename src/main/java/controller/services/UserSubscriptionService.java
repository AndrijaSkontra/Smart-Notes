package controller.services;

import model.User;
import model.UserNote;
import model.UserSubscription;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserSubscriptionService {

    private SessionFactory sessionFactory;

    public UserSubscriptionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void subscribeUser(User user, User subscribedToUser) {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setSubscriber(user);
        userSubscription.setSubscribedTo(subscribedToUser);
        sessionFactory.inTransaction(session -> session.persist(userSubscription));
    }

    public void unsubscribeUser(User user, User selectedUser) {
        Long userId = user.getId();
        Long selectedUserId = selectedUser.getId();

        sessionFactory.inTransaction(session -> {
            UserSubscription userSubscription = null;
            String hql = "FROM UserSubscription U WHERE U.subscriber.id = :user_id AND U.subscribedTo.id = :selected_user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", userId);
            query.setParameter("selected_user_id", selectedUserId);
            userSubscription = (UserSubscription) query.uniqueResult();
            session.remove(userSubscription);
        });
    }

    public List<User> getAllUserSubscriptions(User user) {
        ArrayList<UserSubscription> userSubscriptionList = new ArrayList<>();
        sessionFactory.inTransaction(session -> {
            String hql = "FROM UserSubscription U";
            Query query = session.createQuery(hql);
            userSubscriptionList.addAll(query.list());
        });

        ArrayList<User> subscribedToList = new ArrayList<>();
        for (UserSubscription userSubscription : userSubscriptionList) {
            if (userSubscription.getSubscriber().equals(user)) {
                subscribedToList.add(userSubscription.getSubscribedTo());
            }
        }
        return subscribedToList;
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

    public ArrayList<UserNote> getAListOfSubscribedUserNotes(User user) {
        ArrayList<UserNote> subscribedUsersNotes = new ArrayList<>();

        sessionFactory.inTransaction(session -> {
            String hql = "FROM UserSubscription U WHERE U.subscriber.id = :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", user.getId());
            List<UserSubscription> userSubscriptions = query.list();
            for (UserSubscription us : userSubscriptions) {
                String hql2 = "FROM UserNote U WHERE U.user.id = :subscribed_user_id";
                Query query2 = session.createQuery(hql2);
                query2.setParameter("subscribed_user_id", us.getSubscribedTo().getId());
                subscribedUsersNotes.addAll(query2.list());
            }
        });

        return subscribedUsersNotes;
    }

    public List<User> getAllSubscribedToUser(User user) {
        ArrayList<UserSubscription> userSubscriptionList = new ArrayList<>();
        sessionFactory.inTransaction(session -> {
            String hql = "FROM UserSubscription U WHERE U.subscribedTo.id = :user_id";
            Query query = session.createQuery(hql);
            query.setParameter("user_id", user.getId());
            userSubscriptionList.addAll(query.list());
        });

        ArrayList<User> subscribedToList = new ArrayList<>();
        for (UserSubscription userSubscription : userSubscriptionList) {
            subscribedToList.add(userSubscription.getSubscriber());
        }
        for (User user1 : subscribedToList) {
            System.out.println(user1.getUsername() + " --- yes");
        }
        return subscribedToList;
    }
}