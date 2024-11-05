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
}

