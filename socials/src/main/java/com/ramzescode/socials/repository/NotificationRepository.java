package com.ramzescode.socials.repository;

import com.ramzescode.socials.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
