package br.org.cadi.dashboard;

import br.org.cadi.auth.User;
import br.org.cadi.dashboard.dto.DashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Endpoints for the initial user home page")
public class DashboardController {

    private final DashboardService service;

    @Operation(summary = "Get dashboard data", description = "Returns aggregated data (notifications, mural, classes) tailored to the logged-in user's role.")
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.getDashboardData(user));
    }
}
