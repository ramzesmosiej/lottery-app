package com.ramzescode.socials.service;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.enumeration.GroupNotificationTarget;
import com.ramzescode.socials.domain.enumeration.NotificationType;
import com.ramzescode.socials.domain.notification.GroupNotification;
import com.ramzescode.socials.repository.GroupNotificationRepository;
import com.ramzescode.socials.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ramzescode.socials.domain.enumeration.GroupNotificationTarget.*;
import static com.ramzescode.socials.domain.enumeration.NotificationType.FOLLOWED_PERSON_ADDED_POST;
import static com.ramzescode.socials.domain.enumeration.NotificationType.LIKED_POST_WAS_UPDATED;
import static com.ramzescode.socials.domain.notification.GroupNotificationFactory.createNotification;

@Service
public class GroupNotificationService {

    private final Logger log = LoggerFactory.getLogger(GroupNotificationService.class);

    private final UserRepository userRepository;

    private final GroupNotificationRepository groupNotificationRepository;

    public GroupNotificationService(UserRepository userRepository, GroupNotificationRepository groupNotificationRepository) {
        this.userRepository = userRepository;
        this.groupNotificationRepository = groupNotificationRepository;
    }

    private void notifyGroupWithNotificationType(NotificationType notificationType, GroupNotificationTarget[] targetGroups, Object notificationSubject) {
        List<AppUser> targetGroup;
        GroupNotification groupNotification;
        for (GroupNotificationTarget group : targetGroups) {
            switch (notificationType) {
                // post_related
                case LIKED_POST_WAS_UPDATED -> {
                    groupNotification = createNotification(notificationType, (Post) notificationSubject, group);
                    targetGroup = createGroup(group, (Post) notificationSubject);
                }

                //follow related
                case FOLLOWED_PERSON_ADDED_POST -> {
                    groupNotification = createNotification(notificationType, (AppUser) notificationSubject, group);
                    targetGroup = createGroup(group, (AppUser) notificationSubject);
                }

                default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
            };
            saveAndSendToGroup(groupNotification, targetGroup);
        }

    }

    private List<AppUser> createGroup(GroupNotificationTarget group, Post notificationSubject) {
        return switch (group) {
            case USERS_THAT_LIKED_POST -> userRepository.findAllUsersThatLikedThePost(notificationSubject.getId());
            default -> throw new UnsupportedOperationException("Group unknown: " + group);
        };
    }

    private List<AppUser> createGroup(GroupNotificationTarget group, AppUser notificationSubject) {
        return switch (group) {
            case USERS_THAT_FOLLOW_THAT_USER -> userRepository.findAllUsersThatFollowThatUser(notificationSubject.getId());
            default -> throw new UnsupportedOperationException("Group unknown: " + group);
        };
    }

    private void saveAndSendToGroup(GroupNotification groupNotification, List<AppUser> targetGroup) {
        groupNotificationRepository.save(groupNotification);
        log.debug("Sending email to each user in the group : {}", targetGroup);
    }

    public void notifyFollowersAboutNewPost(AppUser user) {
        notifyGroupWithNotificationType(FOLLOWED_PERSON_ADDED_POST, new GroupNotificationTarget[]{USERS_THAT_FOLLOW_THAT_USER}, user);
    }

    public void notifyAboutPostUpdate(Post updatedPost) {
        notifyGroupWithNotificationType(LIKED_POST_WAS_UPDATED, new GroupNotificationTarget[]{USERS_THAT_LIKED_POST}, updatedPost);
    }
}
