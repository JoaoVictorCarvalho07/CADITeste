package br.org.cadi.academic;

import br.org.cadi.academic.dto.AulaRequest;
import br.org.cadi.academic.dto.AulaResponse;
import br.org.cadi.academic.dto.PresencaRequest;
import br.org.cadi.academic.dto.PresencaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/frequencia")
@RequiredArgsConstructor
@Tag(name = "Frequência", description = "Endpoints for recording and consulting student frequency")
public class FrequenciaController {

    private final FrequenciaService service;

    @Operation(summary = "Register class session", description = "Creates a new class record (Aula). Requires PROFESSOR or ADMIN role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Class session successfully registered"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping("/aula")
    public ResponseEntity<AulaResponse> registerAula(@Valid @RequestBody AulaRequest aula) {
        return ResponseEntity.ok(service.saveAula(aula));
    }

    @Operation(summary = "Record attendance", description = "Records attendance for a specific class session. Requires PROFESSOR or ADMIN role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Attendance successfully recorded"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping("/presenca")
    public ResponseEntity<List<PresencaResponse>> recordAttendance(@Valid @RequestBody List<PresencaRequest> presencas) {
        return ResponseEntity.ok(service.savePresencas(presencas));
    }

    @Operation(summary = "Get student frequency", description = "Returns attendance history for a student")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved frequency history")
    })
    @GetMapping("/aluno/{studentId}")
    public List<PresencaResponse> getStudentFrequency(@PathVariable Long studentId) {
        return service.findByStudentId(studentId);
    }
}
