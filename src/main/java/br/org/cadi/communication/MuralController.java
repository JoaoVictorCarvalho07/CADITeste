package br.org.cadi.communication;

import br.org.cadi.academic.MatriculaRepository;
import br.org.cadi.auth.User;
import br.org.cadi.communication.dto.MuralPostRequest;
import br.org.cadi.communication.dto.MuralPostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mural")
@RequiredArgsConstructor
@Tag(name = "Mural", description = "Endpoints for the institutional mural system")
public class MuralController {

    private final MuralService service;
    private final MatriculaRepository matriculaRepository;

    @Operation(summary = "Create a mural post", description = "Creates a new announcement on the mural. Requires ADMIN or SECRETARIA role")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECRETARIA')")
    @PostMapping
    public ResponseEntity<MuralPostResponse> createPost(@Valid @RequestBody MuralPostRequest request) {
        return ResponseEntity.ok(service.createPost(request));
    }

    @Operation(summary = "List visible posts", description = "Returns mural posts visible to the current user based on their roles and classes")
    @GetMapping
    public List<MuralPostResponse> getMyMural() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> roleNames = user.getRole() != null ? List.of(user.getRole().getName()) : List.of();

        List<Long> turmaIds = matriculaRepository.findByStudentUserId(user.getId()).stream()
                .map(m -> m.getTurma().getId())
                .collect(Collectors.toList());

        return service.getVisiblePosts(roleNames, turmaIds);
    }
}
