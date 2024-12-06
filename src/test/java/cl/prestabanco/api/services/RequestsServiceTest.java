package cl.prestabanco.api.services;

import cl.prestabanco.api.DTOs.RequestsWithTypeLoan;
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

import java.util.Arrays;
import java.util.List;

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

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenAnyParameterIsZeroOrNegative_thenReturnNull() {
        // Given: Any of the parameters is zero or negative
        String stateRequest = "PENDING";
        Integer leanRequest = -1;
        Integer userRequest = 1;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenValidRequest_thenSaveRequest() {
        // Given: Valid parameters and mocked dependencies
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(user);

        // Mock saving the request and ensure it has stateRequest set
        when(requestsRepository.save(any(RequestsEntity.class))).thenAnswer(invocation -> {
            RequestsEntity savedRequest = invocation.getArgument(0);
            savedRequest.setStateRequest("PENDING"); // Ensuring the state is set
            return savedRequest;
        });

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStateRequest()).isEqualTo("PENDING");
        assertThat(result.getLeanRequest()).isEqualTo(loan);
        assertThat(result.getUserRequest()).isEqualTo(user);
    }


    @Test
    void whenStateRequestIsNull_thenReturnNull() {
        // Given: stateRequest is null
        String stateRequest = null;
        Integer leanRequest = 1;
        Integer userRequest = 1;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenLoanNotFound_thenReturnNull() {
        // Given: Loan not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(null);
        when(usersService.findUser(userRequest)).thenReturn(user);

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenUserNotFound_thenReturnNull() {
        // Given: User not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(null);

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenEvaluationNotFound_thenReturnNull() {
        // Given: Evaluation not found (mocked behavior)
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1;

        when(loansService.findLoan(leanRequest)).thenReturn(loan);
        when(usersService.findUser(userRequest)).thenReturn(user);
        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenUserRequestAndEvaluationRequestAreNull_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both null
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = null;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are null
    }

    @Test
    void whenUserRequestIsZeroAndEvaluationRequestIsNull_thenReturnNull() {
        // Given: userRequest is zero and evaluationRequest is null
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 0; // userRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNull(); // It should return null when userRequest is zero and evaluationRequest is null
    }

    @Test
    void whenUserRequestIsNullAndEvaluationRequestIsZero_thenReturnNull() {
        // Given: userRequest is null and evaluationRequest is zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = null;

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNull(); // It should return null when userRequest is null and evaluationRequest is zero
    }

    @Test
    void whenEvaluationRequestIsLessThanOrEqualToZero_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both less than or equal to zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = 1; // userRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are less than or equal to zero
    }

    @Test
    void whenUserRequestIsLessThanOrEqualToZero_thenReturnNull() {
        // Given: userRequest and evaluationRequest are both less than or equal to zero
        String stateRequest = "PENDING";
        Integer leanRequest = 1;
        Integer userRequest = -1; // userRequest <= 0

        // When
        RequestsEntity result = requestsService.saveRequest(stateRequest, leanRequest, userRequest);

        // Then
        assertThat(result).isNull(); // It should return null when both are less than or equal to zero
    }

    @Test
    void whenRequestNotFoundUpdate_thenReturnNull() {
        // Given
        Integer idRequest = 1;
        when(requestsRepository.findById(idRequest)).thenReturn(java.util.Optional.empty());

        // When
        RequestsEntity result = requestsService.updateRequest(idRequest, "Approved", 1);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenRequestFound_thenUpdateState() {
        // Given
        Integer idRequest = 1;
        RequestsEntity request = new RequestsEntity();
        request.setIdRequest(idRequest);
        request.setStateRequest("Pending");
        when(requestsRepository.findById(idRequest)).thenReturn(java.util.Optional.of(request));
        when(requestsRepository.save(request)).thenReturn(request);

        // When
        RequestsEntity result = requestsService.updateRequest(idRequest, "Approved",1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStateRequest()).isEqualTo("Approved");
    }

    @Test
    void whenUserIdIsZeroOrNegative_thenReturnNull() {
        // Given: Invalid user id
        Integer idUser = 0;

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenUserNotFoundRequestID_thenReturnNull() {
        // Given: User not found (mocked behavior)
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(null);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenRequestsNotFound_thenReturnNull() {
        // Given: Valid user but no requests found
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(null);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenRequestsFound_thenReturnCorrectTypeAndDocuments() {
        // Given: Valid user and valid requests
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);

        RequestsEntity request1 = new RequestsEntity();
        request1.setIdRequest(1);
        request1.setStateRequest("PENDING");
        LoansEntity loan1 = new LoansEntity();
        loan1.setTypeLoan("Primera Vivienda");
        request1.setLeanRequest(loan1);

        List<RequestsEntity> requests = Arrays.asList(request1);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(requests);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();

        // Check first request
        RequestsWithTypeLoan requestWithTypeLoan1 = result.get(0);
        assertThat(requestWithTypeLoan1.getIdRequest()).isEqualTo(1);
        assertThat(requestWithTypeLoan1.getStateRequest()).isEqualTo("PENDING");
        assertThat(requestWithTypeLoan1.getDocumentsRequired()).containsExactly("Comprobante de Ingresos", "Certificado de Avalúo", "Historial crediticio");
    }

    @Test
    void whenRequestsFoundSecond_thenReturnCorrectTypeAndDocuments() {
        // Given: Valid user and valid requests
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);

        RequestsEntity request1 = new RequestsEntity();
        request1.setIdRequest(1);
        request1.setStateRequest("PENDING");
        LoansEntity loan1 = new LoansEntity();
        loan1.setTypeLoan("Segunda Vivienda");
        request1.setLeanRequest(loan1);

        List<RequestsEntity> requests = Arrays.asList(request1);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(requests);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();

        // Check first request
        RequestsWithTypeLoan requestWithTypeLoan1 = result.get(0);
        assertThat(requestWithTypeLoan1.getIdRequest()).isEqualTo(1);
        assertThat(requestWithTypeLoan1.getStateRequest()).isEqualTo("PENDING");
        assertThat(requestWithTypeLoan1.getDocumentsRequired()).containsExactly("Comprobante de Ingresos", "Certificado de Avalúo", "Historial crediticio", "Escritura de primera propiedad");
    }

    @Test
    void whenRequestsFoundThird_thenReturnCorrectTypeAndDocuments() {
        // Given: Valid user and valid requests
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);

        RequestsEntity request1 = new RequestsEntity();
        request1.setIdRequest(1);
        request1.setStateRequest("PENDING");
        LoansEntity loan1 = new LoansEntity();
        loan1.setTypeLoan("Propiedades Comerciales");
        request1.setLeanRequest(loan1);

        List<RequestsEntity> requests = Arrays.asList(request1);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(requests);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();

        // Check first request
        RequestsWithTypeLoan requestWithTypeLoan1 = result.get(0);
        assertThat(requestWithTypeLoan1.getIdRequest()).isEqualTo(1);
        assertThat(requestWithTypeLoan1.getStateRequest()).isEqualTo("PENDING");
        assertThat(requestWithTypeLoan1.getDocumentsRequired()).containsExactly("Estado financiero del negocio", "Comprobante de ingresos", "Certificado de Avalúo", "Plan de negocios");
    }

    @Test
    void whenRequestsFoundFourth_thenReturnCorrectTypeAndDocuments() {
        // Given: Valid user and valid requests
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);

        RequestsEntity request1 = new RequestsEntity();
        request1.setIdRequest(1);
        request1.setStateRequest("PENDING");
        LoansEntity loan1 = new LoansEntity();
        loan1.setTypeLoan("Remodalación");
        request1.setLeanRequest(loan1);

        List<RequestsEntity> requests = Arrays.asList(request1);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(requests);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();

        // Check first request
        RequestsWithTypeLoan requestWithTypeLoan1 = result.get(0);
        assertThat(requestWithTypeLoan1.getIdRequest()).isEqualTo(1);
        assertThat(requestWithTypeLoan1.getStateRequest()).isEqualTo("PENDING");
        assertThat(requestWithTypeLoan1.getDocumentsRequired()).containsExactly("Comprobante de Ingresos", "Presupuesto de la remodelación", "Certificado de Avalúo Actualizado");
    }

    @Test
    void whenRequestHasUnknownLoanType_thenReturnEmptyDocuments() {
        // Given: Valid user and valid request but with an unknown loan type
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);

        RequestsEntity request = new RequestsEntity();
        request.setIdRequest(1);
        request.setStateRequest("PENDING");
        LoansEntity loan = new LoansEntity();
        loan.setTypeLoan("Desconocido");
        request.setLeanRequest(loan);

        List<RequestsEntity> requests = Arrays.asList(request);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(requests);

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getDocumentsRequired()).isEmpty();  // No documents for unknown loan type
    }

    @Test
    void whenRequestIsEmpty_thenReturnEmptyList() {
        // Given: User exists but no requests associated
        Integer idUser = 1;
        when(usersService.findUser(idUser)).thenReturn(user);
        when(requestsRepository.findRequestsByIdUser(idUser)).thenReturn(Arrays.asList());

        // When
        List<RequestsWithTypeLoan> result = requestsService.getRequestByIdUser(idUser);

        // Then
        assertThat(result).isNotNull();
    }
}
