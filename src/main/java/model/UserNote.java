package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_notes")
public class UserNote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @Column(name = "date_made")
    private LocalDateTime dateMade;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserNote() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateMade() {
        return dateMade;
    }

    public void setDateMade(LocalDateTime dateMade) {
        this.dateMade = dateMade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
