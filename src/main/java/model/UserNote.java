package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users_notes")
public class UserNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16383)
    private String content;

    @Column(name = "date_made")
    private LocalDateTime dateMade;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
}
