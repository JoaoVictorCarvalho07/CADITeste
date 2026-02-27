package br.org.cadi.finance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FinanceService {

    private final FinancialTransactionRepository transactionRepository;
    private final ProjectRepository projectRepository;

    public List<FinancialTransaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public FinancialTransaction saveTransaction(FinancialTransaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findProjectById(Long id) {
        return projectRepository.findById(id);
    }
}
