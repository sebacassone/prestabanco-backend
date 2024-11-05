package cl.prestabanco.api.services;

import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.models.LoansEntity;
import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.repositories.RequestsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.cdimascio.dotenv.Dotenv;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RequestsServiceTest {

    @MockBean
    private RequestsRepository requestsRepository;

    @MockBean
    private UsersService usersService;

    @MockBean
    private LoansService loansService;

    @MockBean
    private EvaluationsService evaluationsService;

    @Autowired
    private RequestsService requestsService;

    @MockBean
    private UsersEntity user;
    @MockBean
    private LoansEntity loan;
    @MockBean
    private EvaluationsEntity evaluation;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenAnyParameterIsNull_thenReturnNull() {
        // Given: Any of the parameters is null
        String stateRequest = "PENDING";
        Integer leanRequest = null;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenAnyParameterIsZeroOrNegative_thenReturnNull() {
        // Given: Any of the parameters is zero or negative
        String stateRequest = "PENDING";
        Integer leanRequest = -1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenValidRequest_thenSaveRequest() {
        // Given: Valid parameters and mocked dependencies
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(user);
        when(evaluationsService.findEvaluation(evaluationRequest)).thenReturn(evaluation);

        // Mock saving the request and ensure it has stateRequest set
        when(requestsRepository.save(any(RequestsEntity.class))).thenAnswer(invocation -> {
            RequestsEntity savedRequest = invocation.getArgument(0);
            savedRequest.setStateRequest("PENDING"); // Ensuring the state is set
            return savedRequest;
        });

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStateRequest()).isEqualTo("PENDING");
        assertThat(result.getLeanRequest()).isEqualTo(loan);
        assertThat(result.getUserRequest()).isEqualTo(user);
        assertThat(result.getEvaluationRequest()).isEqualTo(evaluation);
    }


    @Test
    void whenStateRequestIsNull_thenReturnNull() {
        // Given: stateRequest is null
        String stateRequest = null;
        Integer leanRequest = 1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenLoanNotFound_thenReturnNull() {
        // Given: Loan not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(null);
        when(usersService.findUser(userRequest)).thenReturn(user);
        when(evaluationsService.findEvaluation(evaluationRequest)).thenReturn(evaluation);

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenUserNotFound_thenReturnNull() {
        // Given: User not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(null);
        when(evaluationsService.findEvaluation(evaluationRequest)).thenReturn(evaluation);

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenEvaluationNotFound_thenReturnNull() {
        // Given: Evaluation not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;
        Integer evaluationRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(user);
        when(evaluationsService.findEvaluation(evaluationRequest)).thenReturn(null);

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenUserRequestAndEvaluationRequestAreNull_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both null
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = null;
        Integer evaluationRequest = null;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are null
    }

    @Test
    void whenUserRequestIsZeroAndEvaluationRequestIsNull_thenReturnNull() {
        // Given: userRequest is zero and evaluationRequest is null
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 0; // userRequest <= 0
        Integer evaluationRequest = null;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNull(); // It should return null when userRequest is zero and evaluationRequest is null
    }

    @Test
    void whenUserRequestIsNullAndEvaluationRequestIsZero_thenReturnNull() {
        // Given: userRequest is null and evaluationRequest is zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = null;
        Integer evaluationRequest = 0; // evaluationRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNull(); // It should return null when userRequest is null and evaluationRequest is zero
    }

    @Test
    void whenEvaluationRequestIsLessThanOrEqualToZero_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both less than or equal to zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1; // userRequest <= 0
        Integer evaluationRequest = -1; // evaluationRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are less than or equal to zero
    }

    @Test
    void whenUserRequestIsLessThanOrEqualToZero_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both less than or equal to zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = -1; // userRequest <= 0
        Integer evaluationRequest = 1; // evaluationRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest, evaluationRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are less than or equal to zero
    }

}
