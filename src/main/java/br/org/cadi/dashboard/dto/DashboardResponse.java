package br.org.cadi.dashboard.dto;

import br.org.cadi.academic.dto.TurmaResponse;
import br.org.cadi.communication.dto.MuralPostResponse;
import br.org.cadi.communication.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response object for the user's initial dashboard page")
public class DashboardResponse {

    @Schema(description = "Last 5 notifications for the user")
    private List<NotificationResponse> recentNotifications;

    @Schema(description = "Mural posts visible to the user")
    private List<MuralPostResponse> muralPosts;

    @Schema(description = "Classes assigned to or enrolled by the user")
    private List<TurmaResponse> classes;

    @Schema(description = "Welcome message personalized for the user role")
    private String welcomeMessage;
}
