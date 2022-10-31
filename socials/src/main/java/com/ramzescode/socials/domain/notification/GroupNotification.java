package com.ramzescode.socials.domain.notification;

import com.ramzescode.socials.domain.enumeration.GroupNotificationTarget;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue(value = "group")
public class GroupNotification extends Notification {

    @Enumerated
    @Column(name = "target_group")
    private GroupNotificationTarget targetGroup;

    public GroupNotification(GroupNotificationTarget targetGroup) {
        this.targetGroup = targetGroup;
        setNotificationDate(LocalDateTime.now());
    }

    public GroupNotificationTarget getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(GroupNotificationTarget targetGroup) {
        this.targetGroup = targetGroup;
    }

    @Override
    public String toString() {
        return "GroupNotification{" +
                "targetGroup=" + targetGroup +
                '}';
    }
}
