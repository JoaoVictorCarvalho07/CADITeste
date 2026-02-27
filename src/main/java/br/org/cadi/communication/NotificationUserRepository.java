package br.org.cadi.communication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, Long> {
    List<NotificationUser> findByUserId(Long userId);
    List<NotificationUser> findByUserIdAndReadFalse(Long userId);
}
