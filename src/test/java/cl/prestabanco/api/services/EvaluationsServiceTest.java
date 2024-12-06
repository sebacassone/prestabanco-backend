package cl.prestabanco.api.services;

import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.repositories.EvaluationsRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EvaluationsServiceTest {

    @Mock
    private EvaluationsRepository evaluationsRepository;

    @Mock
    private IncomesService incomesService;

    @Mock
    private DebtsService debtsService;

    @Mock
    private JobsService jobsService;

    @Mock
    private LoansService loansService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private EvaluationsService evaluationsService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenAllBooleansAreTrue_thenSaveAndReturnEvaluation() {
        // Given: A valid EvaluationsEntity with all Boolean fields set to true
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setQuotaIncomeRatio(true);
        evaluation.setCustomerCredit(true);
        evaluation.setSeniorityEvaluation(true);
        evaluation.setDebtIncomeRatio(true);
        evaluation.setMaximumFinancingAmount(true);
        evaluation.setAgeApplicant(true);

        // When: Saving the entity
        when(evaluationsRepository.save(evaluation)).thenReturn(evaluation);
        EvaluationsEntity result = evaluationsService.saveEvaluation(evaluation);

        // Then: The result should match the given entity
        assertThat(result).isNotNull();
        assertThat(result.getQuotaIncomeRatio()).isTrue();
        assertThat(result.getCustomerCredit()).isTrue();
        assertThat(result.getSeniorityEvaluation()).isTrue();
        assertThat(result.getDebtIncomeRatio()).isTrue();
        assertThat(result.getMaximumFinancingAmount()).isTrue();
        assertThat(result.getAgeApplicant()).isTrue();
    }

    @Test
    void whenAllBooleansAreFalse_thenSaveAndReturnEvaluation() {
        // Given: A valid EvaluationsEntity with all Boolean fields set to false
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setQuotaIncomeRatio(false);
        evaluation.setCustomerCredit(false);
        evaluation.setSeniorityEvaluation(false);
        evaluation.setDebtIncomeRatio(false);
        evaluation.setMaximumFinancingAmount(false);
        evaluation.setAgeApplicant(false);

        // When: Saving the entity
        when(evaluationsRepository.save(evaluation)).thenReturn(evaluation);
        EvaluationsEntity result = evaluationsService.saveEvaluation(evaluation);

        // Then: The result should match the given entity
        assertThat(result).isNotNull();
        assertThat(result.getQuotaIncomeRatio()).isFalse();
        assertThat(result.getCustomerCredit()).isFalse();
        assertThat(result.getSeniorityEvaluation()).isFalse();
        assertThat(result.getDebtIncomeRatio()).isFalse();
        assertThat(result.getMaximumFinancingAmount()).isFalse();
        assertThat(result.getAgeApplicant()).isFalse();
    }

    @Test
    void whenSomeBooleansAreTrueAndOthersFalse_thenSaveAndReturnEvaluation() {
        // Given: A valid EvaluationsEntity with mixed Boolean values
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setQuotaIncomeRatio(true);
        evaluation.setCustomerCredit(false);
        evaluation.setSeniorityEvaluation(true);
        evaluation.setDebtIncomeRatio(false);
        evaluation.setMaximumFinancingAmount(true);
        evaluation.setAgeApplicant(false);

        // When: Saving the entity
        when(evaluationsRepository.save(evaluation)).thenReturn(evaluation);
        EvaluationsEntity result = evaluationsService.saveEvaluation(evaluation);

        // Then: The result should match the given entity
        assertThat(result).isNotNull();
        assertThat(result.getQuotaIncomeRatio()).isTrue();
        assertThat(result.getCustomerCredit()).isFalse();
        assertThat(result.getSeniorityEvaluation()).isTrue();
        assertThat(result.getDebtIncomeRatio()).isFalse();
        assertThat(result.getMaximumFinancingAmount()).isTrue();
        assertThat(result.getAgeApplicant()).isFalse();
    }

    @Test
    void whenAllBooleansAreNull_thenReturnNull() {
        // Given: A valid EvaluationsEntity with all Boolean fields set to null
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setQuotaIncomeRatio(null);
        evaluation.setCustomerCredit(null);
        evaluation.setSeniorityEvaluation(null);
        evaluation.setDebtIncomeRatio(null);
        evaluation.setMaximumFinancingAmount(null);
        evaluation.setAgeApplicant(null);

        // When: Trying to save the entity
        when(evaluationsRepository.save(evaluation)).thenThrow(new IllegalArgumentException("All fields cannot be null"));

        // Then: The entity cannot be saved (since all fields are non-nullable)
        try {
            evaluationsService.saveEvaluation(evaluation);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("All fields cannot be null");
        }
    }

    @Test
    void whenSomeBooleansAreNull_thenReturnNull() {
        // Given: A valid EvaluationsEntity with some Boolean fields set to null
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setQuotaIncomeRatio(true);
        evaluation.setCustomerCredit(null);
        evaluation.setSeniorityEvaluation(true);
        evaluation.setDebtIncomeRatio(false);
        evaluation.setMaximumFinancingAmount(true);
        evaluation.setAgeApplicant(false);

        // When: Trying to save the entity with a null field
        when(evaluationsRepository.save(evaluation)).thenThrow(new IllegalArgumentException("All fields cannot be null"));

        // Then: The entity cannot be saved (because some fields are null)
        try {
            evaluationsService.saveEvaluation(evaluation);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("All fields cannot be null");
        }
    }

    @Test
    void whenEvaluationIsNotFound_thenReturnNull() {
        // Given: A non-existing evaluation ID
        Integer evaluationId = 999;

        // When: The repository does not find the evaluation
        when(evaluationsRepository.findById(evaluationId)).thenReturn(java.util.Optional.empty());

        // When
        EvaluationsEntity result = evaluationsService.findEvaluation(evaluationId);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenEvaluationIsNull_thenReturnNull() {
        // When
        EvaluationsEntity result = evaluationsService.findEvaluation(null);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenAverageSalaryIsZero_thenReturnNull() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 1000.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Primera Vivienda";

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(0.0);
        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenQuotaIncomeRatioIsGreaterThan35_thenSetQuotaIncomeRatioFalse() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 5000.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Primera Vivienda";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getQuotaIncomeRatio()).isFalse();
    }

    @Test
    void whenQuotaIncomeRatioIsLessThanOrEqualTo35_thenSetQuotaIncomeRatioTrue() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Primera Vivienda";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getQuotaIncomeRatio()).isTrue();
    }

    @Test
    void whenHasUnpaidDebtsOrMorocities_thenSetCustomerCreditFalse() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Segunda Vivienda";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(true);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getCustomerCredit()).isFalse();
    }

    @Test
    void whenHasSeniority_thenSetSeniorityEvaluationTrue() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Propiedades Comerciales";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getSeniorityEvaluation()).isTrue();
    }

    @Test
    void whenRelationDebtsIncomeTrue_thenSetDebtIncomeRatioTrue() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Remodelación";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getDebtIncomeRatio()).isTrue();
    }

    @Test
    void whenMaximumFinancingAmountFalse_thenSetMaximumFinancingAmountFalse() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Segunda Vivienda";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(false);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getMaximumFinancingAmount()).isFalse();
    }

    @Test
    void whenApplicantsAgeFalse_thenSetAgeApplicantFalse() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Propiedades Comerciales";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(false);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result.getAgeApplicant()).isFalse();
    }

    @Test
    void whenAllConditionsAreMet_thenReturnEvaluation() {
        // Given
        Integer idUser = 1;
        Double quotaLoan = 3500.0;
        Double maximumAmountPercentage = 50.0;
        String typeLoan = "Primera Vivienda";
        Double averageSalary = 10000.0;

        // When
        when(incomesService.avarageSalary(idUser)).thenReturn(averageSalary);
        when(debtsService.hasUnpaidDebtsOrMorocities(idUser)).thenReturn(false);
        when(jobsService.hasSeniority(idUser)).thenReturn(true);
        when(debtsService.relationDebtsIncome(idUser, quotaLoan)).thenReturn(true);
        when(loansService.maximumFinancingAmount(typeLoan, maximumAmountPercentage)).thenReturn(true);
        when(usersService.applicantsAge(idUser)).thenReturn(true);

        EvaluationsEntity result = evaluationsService.makeEvaluation(idUser, quotaLoan, maximumAmountPercentage, typeLoan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getQuotaIncomeRatio()).isTrue();
        assertThat(result.getCustomerCredit()).isTrue();
        assertThat(result.getSeniorityEvaluation()).isTrue();
        assertThat(result.getDebtIncomeRatio()).isTrue();
        assertThat(result.getMaximumFinancingAmount()).isTrue();
        assertThat(result.getAgeApplicant()).isTrue();
    }

    @Test
    void whenEvaluationIsNullSave_thenReturnNull() {
        // Given

        // When
        EvaluationsEntity result = evaluationsService.saveEvaluation(null);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenEvaluationIsNotNull_thenSaveAndReturnEvaluation() {
        // Given
        EvaluationsEntity evaluation = new EvaluationsEntity();
        evaluation.setIdEvaluation(1);

        // When
        when(evaluationsRepository.save(evaluation)).thenReturn(evaluation);
        EvaluationsEntity result = evaluationsService.saveEvaluation(evaluation);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdEvaluation()).isEqualTo(1);
        verify(evaluationsRepository, times(1)).save(evaluation);
    }
}

