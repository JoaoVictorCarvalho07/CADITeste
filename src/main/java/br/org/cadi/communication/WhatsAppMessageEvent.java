package br.org.cadi.communication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppMessageEvent {
    private String username;
    private String phoneNumber;
    private String content;
}
