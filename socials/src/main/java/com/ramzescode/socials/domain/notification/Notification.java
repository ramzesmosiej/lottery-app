package com.ramzescode.socials.domain.notification;

import com.ramzescode.socials.domain.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "N")
public abstract class Notification {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notification_sequence")
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "notification_date")
    private LocalDateTime notificationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    @Override
    public String
    toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", notificationDate=" + notificationDate +
                ", author=" + author +
                '}';
    }
}
