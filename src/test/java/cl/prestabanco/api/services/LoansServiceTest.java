package cl.prestabanco.api.services;

import cl.prestabanco.api.models.LoansEntity;
import cl.prestabanco.api.repositories.LoansRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.cdimascio.dotenv.Dotenv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoansServiceTest {

    @MockBean
    private LoansRepository loansRepository;

    @Autowired
    private LoansService loansService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenCalculateLoanWithValidData_thenReturnValidLoan() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);  // Amount of the loan
        loan.setTypeLoan("Primera Vivienda");  // Loan type
        loan.setNumberOfPaymentsLoan(180);  // Number of payments (e.g., 15 years)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInterestLoan()).isEqualTo(0.035f);  // Interest rate for "Primera Vivienda"
        assertThat(result.getQuotaLoan()).isGreaterThan(0);  // Quota should be calculated
        assertThat(result.getSecureAmountLoan()).isGreaterThan(0);  // Secure amount should be calculated
        assertThat(result.getAdministrationAmountLoan()).isGreaterThan(0);  // Administration fee should be calculated
        assertThat(result.getTotalAmountLoan()).isGreaterThan(result.getQuotaLoan());  // Total amount should be more than the quota
    }

    @Test
    void whenLoanAmountIsZero_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(0);  // Invalid loan amount
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Loan is invalid due to amount being zero
    }

    @Test
    void whenLoanNumberOfPaymentsIsZero_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(0);  // Invalid number of payments

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Loan is invalid due to number of payments being zero
    }

    @Test
    void whenLoanTypeIsInvalid_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Invalid Type");  // Invalid loan type
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Loan is invalid due to unknown loan type
    }

    @Test
    void whenFindLoanWithValidId_thenReturnLoan() {
        // Given
        Integer loanId = 1;
        LoansEntity loan = new LoansEntity();
        loan.setIdLoan(loanId);
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");

        // Simulate loan is found in the repository
        when(loansRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));

        // When
        LoansEntity result = loansService.findLoan(loanId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdLoan()).isEqualTo(loanId);
        assertThat(result.getAmountLoan()).isEqualTo(100000);
        assertThat(result.getTypeLoan()).isEqualTo("Primera Vivienda");
    }

    @Test
    void whenFindLoanWithInvalidId_thenReturnNull() {
        // Given
        Integer loanId = 99;  // Non-existing loan ID

        // Simulate loan is not found in the repository
        when(loansRepository.findById(loanId)).thenReturn(java.util.Optional.empty());

        // When
        LoansEntity result = loansService.findLoan(loanId);

        // Then
        assertThat(result).isNull();  // Loan is not found
    }

    @Test
    void whenSaveLoanWithValidData_thenReturnSavedLoan() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180);

        // Simulate saving the loan in the repository
        when(loansRepository.save(any(LoansEntity.class))).thenAnswer(i -> i.getArgument(0));

        // When
        LoansEntity result = loansService.saveLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAmountLoan()).isEqualTo(100000);
        assertThat(result.getTypeLoan()).isEqualTo("Primera Vivienda");
        assertThat(result.getNumberOfPaymentsLoan()).isEqualTo(180);
    }

    @Test
    void whenSaveLoanWithNullLoan_thenReturnNull() {
        // Given
        // When
        LoansEntity result = loansService.saveLoan(null);

        // Then
        assertThat(result).isNull();  // Return null if the loan is null
    }

    @Test
    void whenSaveLoanWithInvalidData_thenReturnSavedLoan() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180);

        // Simulate saving the loan in the repository
        when(loansRepository.save(any(LoansEntity.class))).thenAnswer(i -> i.getArgument(0));

        // When
        LoansEntity result = loansService.saveLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAmountLoan()).isEqualTo(100000);
        assertThat(result.getTypeLoan()).isEqualTo("Primera Vivienda");
        assertThat(result.getNumberOfPaymentsLoan()).isEqualTo(180);
    }

    @Test
    void whenAmountIsNull_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(null);  // Amount is null
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Should return null due to invalid amount
    }

    @Test
    void whenNumberOfPaymentsIsNull_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(null);  // Number of payments is null

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Should return null due to invalid number of payments
    }

    @Test
    void whenTypeLoanIsNull_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan(null);  // Type of loan is null
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Should return null due to missing loan type
    }

    @Test
    void whenTypeLoanIsInvalid_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Invalid Type");  // Invalid loan type
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Should return null due to invalid loan type
    }

    @Test
    void whenLoanTypeIsNotInTheValidOptions_thenReturnNull() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Invalid Type");  // Invalid loan type, not in the valid list
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();  // Should return null for an invalid type
    }

    @Test
    void whenTypeLoanIsValid_thenReturnLoan() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");  // Valid loan type
        loan.setNumberOfPaymentsLoan(180);

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();  // Should return a valid loan entity
        assertThat(result.getInterestLoan()).isEqualTo(0.035f);  // Interest rate for "Primera Vivienda"
    }

    @Test
    void whenTypeLoanIsPrimeraVivienda_thenInterestRateShouldBe0_035() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInterestLoan()).isEqualTo(0.035f);  // Verifica la tasa de interés correcta
    }

    @Test
    void whenTypeLoanIsSegundaVivienda_thenInterestRateShouldBe0_04() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Segunda Vivienda");
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInterestLoan()).isEqualTo(0.04f);  // Verifica la tasa de interés correcta
    }

    @Test
    void whenTypeLoanIsPropiedadesComerciales_thenInterestRateShouldBe0_05() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Propiedades Comerciales");
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInterestLoan()).isEqualTo(0.05f);  // Verifica la tasa de interés correcta
    }

    @Test
    void whenTypeLoanIsRemodalacion_thenInterestRateShouldBe0_045() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Remodalación");
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getInterestLoan()).isEqualTo(0.045f);  // Verifica la tasa de interés correcta
    }

    @Test
    void whenTypeLoanIsInvalid_thenInterestRateShouldBe0_00() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan("Tipo Invalido");  // Tipo de préstamo no válido
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenTypeLoanIsNull_thenInterestRateShouldBe0_00() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setAmountLoan(100000);
        loan.setTypeLoan(null);  // Tipo de préstamo es null
        loan.setNumberOfPaymentsLoan(180); // 180 pagos (15 años)

        // When
        LoansEntity result = loansService.calculateLoan(loan);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenAmountPercentageIsNull_thenReturnFalse() {
        // Given
        String typeLoan = "Primera Vivienda";

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAmountPercentageIsLessThanOrEqualToZero_thenReturnFalse() {
        // Given
        String typeLoan = "Primera Vivienda";

        // When
        Boolean result1 = loansService.maximumFinancingAmount(typeLoan, 0f);
        Boolean result2 = loansService.maximumFinancingAmount(typeLoan, -0.1f);

        // Then
        assertThat(result1).isFalse();
        assertThat(result2).isFalse();
    }

    @Test
    void whenAmountPercentageIsGreaterThanOne_thenReturnFalse() {
        // Given
        String typeLoan = "Primera Vivienda";

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, 1.1f);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAmountPercentageIsValidAndTypeLoanIsPrimeraVivienda_thenReturnTrue() {
        // Given
        String typeLoan = "Primera Vivienda";
        Float amountPercentage = 0.75f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenAmountPercentageIsGreaterThanMaximumForPrimeraVivienda_thenReturnFalse() {
        // Given
        String typeLoan = "Primera Vivienda";
        Float amountPercentage = 0.85f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAmountPercentageIsValidAndTypeLoanIsSegundaVivienda_thenReturnTrue() {
        // Given
        String typeLoan = "Segunda Vivienda";
        Float amountPercentage = 0.65f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenAmountPercentageIsGreaterThanMaximumForSegundaVivienda_thenReturnFalse() {
        // Given
        String typeLoan = "Segunda Vivienda";
        Float amountPercentage = 0.75f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAmountPercentageIsValidAndTypeLoanIsPropiedadesComerciales_thenReturnTrue() {
        // Given
        String typeLoan = "Propiedades Comerciales";
        Float amountPercentage = 0.55f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenAmountPercentageIsGreaterThanMaximumForPropiedadesComerciales_thenReturnFalse() {
        // Given
        String typeLoan = "Propiedades Comerciales";
        Float amountPercentage = 0.65f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAmountPercentageIsValidAndTypeLoanIsRemodelacion_thenReturnTrue() {
        // Given
        String typeLoan = "Remodelación";
        Float amountPercentage = 0.45f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenAmountPercentageIsGreaterThanMaximumForRemodelacion_thenReturnFalse() {
        // Given
        String typeLoan = "Remodelación";
        Float amountPercentage = 0.55f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenTypeLoanIsInvalid_thenReturnFalse() {
        // Given
        String typeLoan = "Personal";
        Float amountPercentage = 0.5f;

        // When
        Boolean result = loansService.maximumFinancingAmount(typeLoan, amountPercentage);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenLoanExists_thenReturnLoan() {
        // Given
        LoansEntity loan = new LoansEntity();
        loan.setIdLoan(1);
        loan.setAmountLoan(50000);
        loan.setTypeLoan("Primera Vivienda");
        loan.setNumberOfPaymentsLoan(180);
        Integer idRequest = 100;
        when(loansRepository.findLoanByIdRequest(idRequest)).thenReturn(loan);

        // When
        LoansEntity result = loansService.getLoanByIdRequest(idRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdLoan()).isEqualTo(1);
        assertThat(result.getAmountLoan()).isEqualTo(50000);
        assertThat(result.getTypeLoan()).isEqualTo("Primera Vivienda");
    }

    @Test
    void whenLoanDoesNotExist_thenReturnNull() {
        // Given
        Integer idRequest = 100;
        when(loansRepository.findLoanByIdRequest(idRequest)).thenReturn(null);

        // When
        LoansEntity result = loansService.getLoanByIdRequest(idRequest);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenIdRequestIsNull_thenReturnNull() {
        // Given
        Integer idRequest = null;
        when(loansRepository.findLoanByIdRequest(anyInt())).thenReturn(null);

        // When
        LoansEntity result = loansService.getLoanByIdRequest(idRequest);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenIdRequestIsInvalid_thenReturnNull() {
        // Given
        Integer invalidIdRequest = 9999;  // Non-existing loan ID
        when(loansRepository.findLoanByIdRequest(invalidIdRequest)).thenReturn(null);

        // When
        LoansEntity result = loansService.getLoanByIdRequest(invalidIdRequest);

        // Then
        assertThat(result).isNull();
    }
}
