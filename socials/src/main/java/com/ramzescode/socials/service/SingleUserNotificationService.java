package com.ramzescode.socials.service;

import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.FollowingRelationship;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.enumeration.NotificationType;
import com.ramzescode.socials.domain.notification.SingleUserNotification;
import org.springframework.stereotype.Service;

import static com.ramzescode.socials.domain.notification.SingleUserNotificationFactory.createNotification;

@Service
public class SingleUserNotificationService {

    private void notifyReceiverWithNotificationType(Object notificationSubject, NotificationType notificationType, AppUser author) {

        SingleUserNotification singleUserNotification = switch (notificationType) {
            // followers related
            case GET_FOLLOWED -> createNotification((FollowingRelationship) notificationSubject,
                    notificationType);
            // post_related
            case POST_LIKED -> createNotification((Post) notificationSubject,
                    notificationType);
            // account related
            case ACCOUNT_BLOCKED -> createNotification((AppUser) notificationSubject,
                    notificationType, author);
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        };
        saveAndSend(singleUserNotification);
    }

    private void saveAndSend(SingleUserNotification singleUserNotification) {

    }

}
