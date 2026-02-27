package br.org.cadi.psychosocial;

import br.org.cadi.auth.User;
import br.org.cadi.psychosocial.dto.ProntuarioRequest;
import br.org.cadi.psychosocial.dto.ProntuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/psychosocial")
@RequiredArgsConstructor
@Tag(name = "Psychosocial", description = "Endpoints for clinical records and therapeutic notes. Highly restricted access.")
public class PsychosocialController {

    private final PsychosocialService service;

    @Operation(summary = "Register clinical notes", description = "Adds a new entry to a patient's clinical history. Requires PSICOLOGO role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notes successfully registered"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Requires Psychologist role")
    })
    @PreAuthorize("hasRole('PSICOLOGO')")
    @PostMapping("/prontuarios")
    public ResponseEntity<ProntuarioResponse> createProntuario(@Valid @RequestBody ProntuarioRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(service.createProntuario(request, user.getId()));
    }

    @Operation(summary = "Consult clinical history", description = "Returns the history of clinical notes for a specific student. Requires PSICOLOGO or ADMIN role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "History successfully retrieved"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('PSICOLOGO', 'ADMIN')")
    @GetMapping("/prontuarios/paciente/{patientId}")
    public List<ProntuarioResponse> getPatientHistory(@PathVariable Long patientId) {
        return service.getPatientHistory(patientId);
    }
}
