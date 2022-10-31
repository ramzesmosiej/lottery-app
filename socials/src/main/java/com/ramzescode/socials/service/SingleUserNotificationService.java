package com.ramzescode.socials.service;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.FollowingRelationship;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.enumeration.NotificationType;
import com.ramzescode.socials.domain.notification.SingleUserNotification;
import com.ramzescode.socials.repository.SingleUserNotificationRepository;
import org.springframework.stereotype.Service;

import static com.ramzescode.socials.domain.enumeration.NotificationType.GET_FOLLOWED;
import static com.ramzescode.socials.domain.enumeration.NotificationType.POST_LIKED;
import static com.ramzescode.socials.domain.notification.SingleUserNotificationFactory.createNotification;

@Service
public class SingleUserNotificationService {

    private final SingleUserNotificationRepository singleUserNotificationRepository;

    public SingleUserNotificationService(SingleUserNotificationRepository singleUserNotificationRepository) {
        this.singleUserNotificationRepository = singleUserNotificationRepository;
    }

    private void notifyReceiverWithNotificationType(Object notificationSubject, NotificationType notificationType) {

        var singleUserNotification = switch (notificationType) {
            // followers related
            case GET_FOLLOWED  -> createNotification((FollowingRelationship) notificationSubject,
                    notificationType);
            // post_related
            case POST_LIKED -> createNotification((Post) notificationSubject,
                    notificationType);
            // account related
            case ACCOUNT_DELETED -> createNotification((AppUser) notificationSubject,
                    notificationType);
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        };
        saveAndSend(singleUserNotification);
    }

    public void notifyUserAboutNewFollower(FollowingRelationship followingRelationship) {
        notifyReceiverWithNotificationType(followingRelationship, GET_FOLLOWED);
    }

    public void notifyUserAboutNewPostLike(Post likedPost) {
        notifyReceiverWithNotificationType(likedPost, POST_LIKED);
    }

    public void notifyUserAboutBlockingAccount() {
        // TODO("finish method")
    }

    private void saveAndSend(SingleUserNotification singleUserNotification) {
        singleUserNotificationRepository.save(singleUserNotification);
        // TODO("add logic to send email")
    }

}
