package br.org.cadi.communication;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }
    }

    public String templateDoacao(String nome, Double valor) {
        return """
        <html>
        <body style="font-family: Arial; background:#f4f4f4; padding:20px;">
            <div style="background:white; padding:20px; border-radius:8px;">
                <h2 style="color:#2E7D32;">Obrigado pela sua doação ❤️</h2>
                <p>Olá %s,</p>
                <p>Recebemos sua doação no valor de <strong>R$ %.2f</strong>.</p>
                <p>Você está ajudando a transformar vidas no CADI.</p>
                <br>
                <small>Instituição CADI</small>
            </div>
        </body>
        </html>
        """.formatted(nome, valor);
    }
}