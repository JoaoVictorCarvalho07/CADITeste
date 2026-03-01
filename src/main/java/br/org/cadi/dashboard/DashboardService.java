package br.org.cadi.dashboard;

import br.org.cadi.academic.MatriculaRepository;
import br.org.cadi.academic.TurmaRepository;
import br.org.cadi.academic.TurmaService;
import br.org.cadi.academic.dto.TurmaResponse;
import br.org.cadi.auth.Role;
import br.org.cadi.auth.User;
import br.org.cadi.communication.MuralService;
import br.org.cadi.communication.NotificationService;
import br.org.cadi.communication.dto.MuralPostResponse;
import br.org.cadi.communication.dto.NotificationResponse;
import br.org.cadi.dashboard.dto.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final NotificationService notificationService;
    private final MuralService muralService;
    private final MatriculaRepository matriculaRepository;
    private final TurmaRepository turmaRepository;
    private final TurmaService turmaService;

    public DashboardResponse getDashboardData(User user) {
        String roleName = user.getRole() != null ? user.getRole().getName() : "ROLE_USER";

        // Fetch standard notifications and mural posts
        List<NotificationResponse> notifications = notificationService.getUserNotifications(user.getId())
                .stream().limit(5).collect(Collectors.toList());

        List<String> roleNames = user.getRole() != null ? List.of(user.getRole().getName()) : List.of();

        // Find Turma IDs for the user (students)
        List<Long> studentTurmaIds = matriculaRepository.findByStudentUserId(user.getId()).stream()
                .map(m -> m.getTurma().getId())
                .collect(Collectors.toList());

        // Find Turma IDs for professors
        List<Long> professorTurmaIds = turmaRepository.findByProfessorUserId(user.getId()).stream()
                .map(t -> t.getId())
                .collect(Collectors.toList());

        List<Long> allVisibleTurmaIds = new ArrayList<>(studentTurmaIds);
        allVisibleTurmaIds.addAll(professorTurmaIds);

        List<MuralPostResponse> muralPosts = muralService.getVisiblePosts(roleNames, allVisibleTurmaIds);

        // Role-specific class list
        List<TurmaResponse> classes = new ArrayList<>();
        if (roleName.equals("ROLE_ALUNO")) {
            classes = studentTurmaIds.stream()
                    .map(id -> turmaService.findById(id).orElse(null))
                    .filter(t -> t != null)
                    .collect(Collectors.toList());
        } else if (roleName.equals("ROLE_PROFESSOR")) {
            classes = professorTurmaIds.stream()
                    .map(id -> turmaService.findById(id).orElse(null))
                    .filter(t -> t != null)
                    .collect(Collectors.toList());
        }

        return DashboardResponse.builder()
                .recentNotifications(notifications)
                .muralPosts(muralPosts)
                .classes(classes)
                .welcomeMessage(generateWelcomeMessage(user, roleName))
                .build();
    }

    private String generateWelcomeMessage(User user, String role) {
        String base = "Olá, " + user.getUsername() + "! ";
        return switch (role) {
            case "ROLE_ADMIN" -> base + "Bem-vindo ao painel administrativo do CADI.";
            case "ROLE_PROFESSOR" -> base + "Aqui estão suas turmas e avisos pedagógicos.";
            case "ROLE_ALUNO" -> base + "Confira seu mural e horários das aulas.";
            case "ROLE_FINANCEIRO" -> base + "Acesse o controle financeiro e orçamentário.";
            case "ROLE_SECRETARIA" -> base + "Gerencie cadastros e a rotina da instituição.";
            default -> base + "Bem-vindo ao sistema CADI.";
        };
    }
}
