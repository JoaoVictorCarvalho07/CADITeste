package br.org.cadi.communication;

import br.org.cadi.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WhatsAppConsumer {

    private final WhatsAppService whatsAppService;
    public WhatsAppConsumer(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }


    @RabbitListener(queues = RabbitMQConfig.WHATSAPP_QUEUE)
    public void consumeWhatsAppMessage(WhatsAppMessageEvent event) {
        log.info("Processing WhatsApp message for user: {}", event.getUsername());
        log.info("Message content: {}", event.getContent());

        // Simulated WhatsApp API integration
        try {
            simulateWhatsAppApiCall(event);
//            whatsAppService.sendMessage(event.getPhoneNumber(), event.getContent());
            log.info("WhatsApp message sent successfully to {}", event.getUsername());
        } catch (Exception e) {
            log.error("Failed to send WhatsApp message to {}: {}", event.getUsername(), e.getMessage());
//            throw e;
        }
    }

    private void simulateWhatsAppApiCall(WhatsAppMessageEvent event) throws InterruptedException {
        // Simulating network delay
        Thread.sleep(1000);
        if (event.getUsername().contains("error")) {
            throw new RuntimeException("WhatsApp API connection timeout");
        }
    }


}
