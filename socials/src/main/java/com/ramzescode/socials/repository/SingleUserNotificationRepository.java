package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleUserNotificationRepository extends JpaRepository<Notification, Long> {

}
