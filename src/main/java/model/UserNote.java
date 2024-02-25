package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a UserNote in the database.
 */
@Entity
@Table(name = "users_notes")
public class UserNote {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Getter
    @Setter
    @Column(name = "date_made")
    private LocalDateTime dateMade;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Getter
    @Setter
    @ManyToMany(mappedBy = "userNotificationNotes", fetch = FetchType.EAGER)
    private Set<User> userSet = new HashSet<>();

    public UserNote() {
    }

    @Override
    public String toString() {
        return "UserNote{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", dateMade=" + dateMade +
                ", user=" + user.getUsername() +
                '}';
    }
}
