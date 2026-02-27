package br.org.cadi.communication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsAppService {

    private final WebClient.Builder webClientBuilder;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String token;

    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;

    public void sendMessage(String phone, String message) {

        WebClient webClient = webClientBuilder.baseUrl(apiUrl).build();

        String endpoint = "/" + phoneNumberId + "/messages";

        webClient.post()
                .uri(endpoint)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(buildBody(phone, message))
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("WhatsApp sent successfully: {}", response))
                .doOnError(error -> log.error("WhatsApp error: {}", error.getMessage()))
                .block(); // síncrono (simples pro seu caso)
    }

    private String buildBody(String phone, String message) {
        return """
            {
              "messaging_product": "whatsapp",
              "to": "%s",
              "type": "text",
              "text": {
                "body": "%s"
              }
            }
            """.formatted(phone, message);
    }
}