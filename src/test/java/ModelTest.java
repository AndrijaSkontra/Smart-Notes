import model.User;
import model.UserNote;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;

public class ModelTest {

    public static void main(String[] args) {
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .build();
        try {
            SessionFactory sessionFactory =
                    new MetadataSources(registry)
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(UserNote.class)
                            .buildMetadata()
                            .buildSessionFactory();
            sessionFactory.inTransaction(session -> {
                User userAndrija = new User();
                userAndrija.setUsername("Andrija");
                userAndrija.setPassword("Nova sifra");

                session.persist(userAndrija);

                UserNote userNote = new UserNote();
                userNote.setUser(userAndrija);
                userNote.setContent("Ovo su neke moje biljeske...");
                userNote.setDateMade(LocalDateTime.now());

                session.persist(new UserNote());
            });
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
