import model.User;

import model.UserNote;
import org.hibernate.cfg.Configuration;

import static java.lang.Boolean.TRUE;
import static java.lang.System.out;
import static org.hibernate.cfg.AvailableSettings.*;
import java.time.LocalDateTime;

import static org.hibernate.cfg.JdbcSettings.*;

public class ModelTest {

    public static void main(String[] args) {
        var sessionFactory = new Configuration()
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserNote.class)

            .setProperty(URL, "jdbc:mysql://localhost:3306/smart_notes")
            .setProperty(USER, "root")
            .setProperty(PASS, "Kokafaca1!")
            .setProperty(HBM2DDL_AUTO, "update")

            .setProperty(SHOW_SQL, TRUE.toString())
            .setProperty(FORMAT_SQL, TRUE.toString())
            .setProperty(HIGHLIGHT_SQL, TRUE.toString())
            .buildSessionFactory();

        sessionFactory.getSchemaManager().exportMappedObjects(true);

        sessionFactory.inTransaction(session -> {
            User andrija = new User("Andrija", "Kokafaca3231");
            UserNote note = new UserNote();
            note.setContent("This is a note, but this is also a really big note more" +
                    "then 255 characters, so it will be stored in a CLOB column. Is this more then 25" +
                    "characters? I don't know, but I will keep typing just to be sure. I think this is"
                    + "more then 255 characters. I hope so. I will keep typing just to be sure. I think this is"
                    + "more then 255 characters. I hope so. I will keep typing just to be sure. I think this is");
            note.setDateMade(LocalDateTime.now());
            note.setUser(andrija);
            session.persist(andrija);
            session.persist(note);
            User user = session.find(User.class, 1L);
            out.println("This is new user");
            out.println(user);

            UserNote userNote = session.find(UserNote.class, 1L);
            out.println("This is new user note");
            out.println(userNote);

        });
    }
}
