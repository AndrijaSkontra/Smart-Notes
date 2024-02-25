package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Class representing a UserSubscription in the database.
 */
@Entity
@Table(name = "user_subscriptions")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "subscribed_to_id")
    private User subscribedTo;

    @Override
    public String toString() {
        return "UserSubscription{" +
                "id=" + id +
                ", subscriber=" + subscriber + " his id == " + subscriber.getId() +
                ", subscribedTo=" + subscribedTo + " his id == " + subscribedTo.getId() +
                '}';
    }
}