package br.org.cadi.communication;

import br.org.cadi.auth.User;
import br.org.cadi.auth.UserRepository;
import br.org.cadi.communication.dto.NotificationRequest;
import br.org.cadi.communication.dto.NotificationResponse;
import br.org.cadi.config.RabbitMQConfig;
import br.org.cadi.people.Person;
import br.org.cadi.people.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final PersonService personService;

    @Transactional
    public void sendNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);

        List<User> recipients = userRepository.findAllById(request.getRecipientIds());

        recipients.forEach(user -> {

            Person p  = personService.findByUserId(user.getId()).orElse(null);
            assert p != null;


            NotificationUser notificationUser = NotificationUser.builder()
                    .notification(notification)
                    .user(user)
                    .read(false)
                    .build();
            notificationUserRepository.save(notificationUser);

            // Publish event to RabbitMQ for asynchronous processing (e.g., WhatsApp integration)

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.NOTIFICATION_EXCHANGE,
                    "email.send",
                    new EmailMessageEvent(p.getEmail(), notification.getTitle(), notification.getMessage(),true)
            );
//            rabbitTemplate.convertAndSend(
//                    RabbitMQConfig.NOTIFICATION_EXCHANGE,
//                    "whatsapp.send",
//                    new WhatsAppMessageEvent(user.getUsername(), p.getNumber(), notification.getMessage())
//            );
        });
    }

    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationUserRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long notificationUserId) {
        notificationUserRepository.findById(notificationUserId).ifPresent(nu -> {
            nu.setRead(true);
            nu.setReadAt(LocalDateTime.now());
            notificationUserRepository.save(nu);
        });
    }

    private NotificationResponse mapToResponse(NotificationUser nu) {



        return NotificationResponse.builder()
                .id(nu.getId())
                .title(nu.getNotification().getTitle())
                .message(nu.getNotification().getMessage())
                .type(nu.getNotification().getType())
                .createdAt(nu.getNotification().getCreatedAt())
                .read(nu.isRead())
                .build();
    }
}
