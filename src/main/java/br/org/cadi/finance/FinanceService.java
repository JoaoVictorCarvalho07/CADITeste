package br.org.cadi.finance;

import br.org.cadi.finance.dto.FinancialTransactionRequest;
import br.org.cadi.finance.dto.FinancialTransactionResponse;
import br.org.cadi.finance.dto.ProjectRequest;
import br.org.cadi.finance.dto.ProjectResponse;
import br.org.cadi.people.Person;
import br.org.cadi.people.PersonRepository;
import br.org.cadi.people.dto.PersonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinancialTransactionRepository transactionRepository;
    private final ProjectRepository projectRepository;
    private final PersonRepository personRepository;

    public List<FinancialTransactionResponse> findAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapTransactionToResponse)
                .collect(Collectors.toList());
    }

    public FinancialTransactionResponse saveTransaction(FinancialTransactionRequest request) {
        FinancialTransaction transaction = mapTransactionToEntity(request);
        if (request.getProjectId() != null) {
            projectRepository.findById(request.getProjectId()).ifPresent(transaction::setProject);
        }
        if (request.getPersonId() != null) {
            personRepository.findById(request.getPersonId()).ifPresent(transaction::setPerson);
        }
        return mapTransactionToResponse(transactionRepository.save(transaction));
    }

    public List<ProjectResponse> findAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapProjectToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse saveProject(ProjectRequest request) {
        Project project = mapProjectToEntity(request);
        return mapProjectToResponse(projectRepository.save(project));
    }

    public Optional<ProjectResponse> findProjectById(Long id) {
        return projectRepository.findById(id).map(this::mapProjectToResponse);
    }

    private ProjectResponse mapProjectToResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .financialGoal(project.getFinancialGoal())
                .active(project.isActive())
                .build();
    }

    private Project mapProjectToEntity(ProjectRequest request) {
        return Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .financialGoal(request.getFinancialGoal())
                .active(request.isActive())
                .build();
    }

    private FinancialTransactionResponse mapTransactionToResponse(FinancialTransaction transaction) {
        return FinancialTransactionResponse.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .value(transaction.getValue())
                .dateTime(transaction.getDateTime())
                .description(transaction.getDescription())
                .project(transaction.getProject() != null ? mapProjectToResponse(transaction.getProject()) : null)
                .person(transaction.getPerson() != null ? mapPersonToResponse(transaction.getPerson()) : null)
                .receiptUrl(transaction.getReceiptUrl())
                .build();
    }

    private FinancialTransaction mapTransactionToEntity(FinancialTransactionRequest request) {
        return FinancialTransaction.builder()
                .type(request.getType())
                .value(request.getValue())
                .dateTime(request.getDateTime())
                .description(request.getDescription())
                .receiptUrl(request.getReceiptUrl())
                .build();
    }

    private PersonResponse mapPersonToResponse(Person person) {
        return PersonResponse.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .type(person.getType())
                .build();
    }
}
