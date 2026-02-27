package br.org.cadi.finance;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
@Tag(name = "Finance", description = "Endpoints for managing projects and financial transactions")
public class FinanceController {

    private final FinanceService service;

    @Operation(summary = "List all financial transactions", description = "Returns a list of all transactions. Requires FINANCEIRO or ADMIN role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('FINANCEIRO', 'ADMIN')")
    @GetMapping("/transactions")
    public List<FinancialTransaction> findAllTransactions() {
        return service.findAllTransactions();
    }

    @Operation(summary = "Register a new financial transaction", description = "Creates a new income or expense record. Requires FINANCEIRO or ADMIN role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('FINANCEIRO', 'ADMIN')")
    @PostMapping("/transactions")
    public ResponseEntity<FinancialTransaction> createTransaction(@RequestBody FinancialTransaction transaction) {
        return ResponseEntity.ok(service.saveTransaction(transaction));
    }

    @Operation(summary = "List all projects")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved projects")
    })
    @GetMapping("/projects")
    public List<Project> findAllProjects() {
        return service.findAllProjects();
    }

    @Operation(summary = "Create a new project")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Project successfully created"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(service.saveProject(project));
    }
}
