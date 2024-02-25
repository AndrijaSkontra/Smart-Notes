package controller;

import model.User;
import model.UserNote;
import model.UserSubscription;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static java.lang.Boolean.TRUE;
import static org.hibernate.cfg.JdbcSettings.*;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;

/**
 * Class responsible for creating a session factory
 * that connects to specific local MySQL database.
 * This class is a singleton.
 */
public class UsersDatabaseConnection implements DatabaseConnection{

    public static volatile UsersDatabaseConnection instance;

    private UsersDatabaseConnection() {
    }

    public static UsersDatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (UsersDatabaseConnection.class) {
                if (instance == null) {
                    instance = new UsersDatabaseConnection();
                }
            }
        }
        return instance;
    }

    @Override
    public SessionFactory getSessionFactory() {
        var sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserNote.class)
                .addAnnotatedClass(UserSubscription.class)
                .setProperty(URL, "jdbc:mysql://localhost:3306/smart_notes")
                .setProperty(USER, "root")
                .setProperty(PASS, "Kokafaca1!")
                // .setProperty(HBM2DDL_AUTO, "update")

                .setProperty(SHOW_SQL, TRUE.toString())
                .setProperty(FORMAT_SQL, TRUE.toString())
                .setProperty(HIGHLIGHT_SQL, TRUE.toString())
                .buildSessionFactory();
        // sessionFactory.getSchemaManager().exportMappedObjects(true);
        return sessionFactory;
    }
}
