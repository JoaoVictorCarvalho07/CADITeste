package br.org.cadi.communication;

import br.org.cadi.auth.User;
import br.org.cadi.communication.dto.NotificationRequest;
import br.org.cadi.communication.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Endpoints for user notifications and messaging")
public class NotificationController {

    private final NotificationService service;

    @Operation(summary = "Send notification", description = "Sends a notification to a list of users and publishes a WhatsApp event to RabbitMQ. Requires ADMIN, SECRETARIA, or PROFESSOR role")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIA', 'PROFESSOR')")
    @PostMapping
    public ResponseEntity<Void> sendNotification(@Valid @RequestBody NotificationRequest request) {
        service.sendNotification(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "List user notifications")
    @GetMapping
    public List<NotificationResponse> getMyNotifications() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getUserNotifications(user.getId());
    }

    @Operation(summary = "Mark notification as read")
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
        return ResponseEntity.ok().build();
    }
}
