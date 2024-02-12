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
                // use H2 in-memory database
                .setProperty(URL, "jdbc:mysql://localhost:3306/smart_notes")
                .setProperty(USER, "root")
                .setProperty(PASS, "Kokafaca1!")
                .setProperty(HBM2DDL_AUTO, "update")

                // use Agroal connection pool
                // .setProperty("hibernate.agroal.maxSize", "20")
                // display SQL in console
                .setProperty(SHOW_SQL, TRUE.toString())
                .setProperty(FORMAT_SQL, TRUE.toString())
                .setProperty(HIGHLIGHT_SQL, TRUE.toString())
                .buildSessionFactory();

        // export the inferred database schema
        sessionFactory.getSchemaManager().exportMappedObjects(true);

        // persist an entity
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

        });

        // query data using HQL
//        sessionFactory.inSession(session -> {
//            out.println(session.createSelectionQuery("select isbn||': '||title from Book").getSingleResult());
//        });

        // query data using criteria API
//        sessionFactory.inSession(session -> {
//            var builder = sessionFactory.getCriteriaBuilder();
//            var query = builder.createQuery(String.class);
//            var book = query.from(Book.class);
//            query.select(builder.concat(builder.concat(book.get(Book_.isbn), builder.literal(": ")),
//                    book.get(Book_.title)));
//            out.println(session.createSelectionQuery(query).getSingleResult());
//        });
    }
}
