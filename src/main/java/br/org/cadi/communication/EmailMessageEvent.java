package br.org.cadi.communication;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class EmailMessageEvent {
    private String to;
    private String subject;
    private String body;     // pode ser texto ou HTML
    private boolean html;    // true se body for HTML
}