package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing a user in the database.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
    private String username;

    @Setter
    private String password;

    @Setter
    @Getter
    @Column(name = "has_read_all_notes")
    private boolean readAllNotes;

    @Getter
    @Setter
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserNote> userNoteSet;

    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "notification_user_notes")
    private Set<UserNote> userNotificationNotes = new HashSet<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.readAllNotes = true;
        userNoteSet = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;

        return user.getUsername().equals(this.getUsername());
    }
}
