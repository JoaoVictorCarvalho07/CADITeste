package br.org.cadi.communication;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationConsumer {

    private final EmailService emailService;

    public EmailNotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email.queue")
    public void handleEmail(EmailMessageEvent event) {
        if (event.isHtml()) {
            emailService.sendHtmlEmail(event.getTo(), event.getSubject(), event.getBody());
        } else {
            emailService.sendHtmlEmail(event.getTo(), event.getSubject(), event.getBody());
        }
    }
}