package com.ramzescode.socials.domain.notification;


import com.ramzescode.socials.domain.AppUser;
import com.ramzescode.socials.domain.FollowingRelationship;
import com.ramzescode.socials.domain.Post;
import com.ramzescode.socials.domain.enumeration.NotificationType;

public class SingleUserNotificationFactory {

    private static final String NEW_FOLLOWER_NOTIFICATION_TEXT = "You have a new follower";

    public static SingleUserNotification createNotification(FollowingRelationship followingRelationship, NotificationType notificationType) {
        AppUser follower = followingRelationship.getFollower();
        AppUser following = followingRelationship.getFollowing();
        SingleUserNotification singleUserNotification = new SingleUserNotification();
        switch (notificationType) {
            case GET_FOLLOWED -> {
                singleUserNotification.setReceiver(following);
                singleUserNotification.setMessage(NEW_FOLLOWER_NOTIFICATION_TEXT);
            }
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        }
        return singleUserNotification;
    }

    public static SingleUserNotification createNotification(Post post, NotificationType notificationType) {
        SingleUserNotification singleUserNotification = new SingleUserNotification();
        AppUser recipient = post.getUser();
        switch (notificationType) {
            case POST_LIKED -> {
                singleUserNotification.setReceiver(recipient);
                singleUserNotification.setMessage("Your post with the title: " + post.getTitle() + " got a new like" );
            }
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        }
        return singleUserNotification;
    }

    // can be performed by the user with role admin
    public static SingleUserNotification createNotification(AppUser userToBeBlocked, NotificationType notificationType) {
        SingleUserNotification singleUserNotification = new SingleUserNotification();
        switch (notificationType) {
            case ACCOUNT_DELETED -> {
                singleUserNotification.setReceiver(userToBeBlocked);
                singleUserNotification.setMessage("Your account will be deleted");
            }
            default -> throw new UnsupportedOperationException("Can not create notification for type : " + notificationType);
        }
        return singleUserNotification;
    }

}
