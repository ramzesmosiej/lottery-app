package com.ramzescode.socials.domain.notification;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.enumeration.GroupNotificationTarget;
import com.ramzescode.socials.domain.enumeration.NotificationType;
import com.ramzescode.socials.repository.UserRepository;

import java.util.List;

public class GroupNotificationFactory {

    private final UserRepository userRepository;

    public GroupNotificationFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static GroupNotification createNotification(NotificationType notificationType, Post post, GroupNotificationTarget group) {
        GroupNotification groupNotification = new GroupNotification(group);
        switch (notificationType) {
            case LIKED_POST_WAS_UPDATED -> {
                groupNotification.setMessage("The post with an author: " + post.getUser().getUsername() + " got updated.");
            }
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        }
        return groupNotification;
    }

    public static GroupNotification createNotification(NotificationType notificationType, AppUser appUser, GroupNotificationTarget group) {
        GroupNotification groupNotification = new GroupNotification(group);
        switch (notificationType) {
            case FOLLOWED_PERSON_ADDED_POST -> {
                groupNotification.setMessage("The person you follow: " + appUser.getUsername() + "added a new post.");
            }
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        }
        return groupNotification;
    }

}