package com.ramzescode.socials.domain.notification;

import com.ramzescode.socials.domain.AppUser;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "U")
public class SingleUserNotification extends Notification {
    @ManyToOne
    private AppUser receiver;

    public AppUser getReceiver() {
        return receiver;
    }

    public void setReceiver(AppUser receiver) {
        this.receiver = receiver;
    }

    public SingleUserNotification() {
        setNotificationDate(LocalDateTime.now());
    }

    public SingleUserNotification(AppUser receiver) {
        this.receiver = receiver;
    }

}
