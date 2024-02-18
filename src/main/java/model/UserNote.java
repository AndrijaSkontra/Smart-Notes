package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
