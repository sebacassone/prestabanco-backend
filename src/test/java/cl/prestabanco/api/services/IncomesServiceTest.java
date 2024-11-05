package cl.prestabanco.api.services;

import cl.prestabanco.api.models.IncomesEntity;
import cl.prestabanco.api.repositories.IncomesRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class IncomesServiceTest {

    @MockBean
    private IncomesRepository incomesRepository;

    @MockBean
    private JobsService jobsService;

    @Autowired
    private IncomesService incomesService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenSaveIncome_thenReturnSavedIncome() {
        // Given
        IncomesEntity income = new IncomesEntity();
        income.setAmountIncome(1000.0f);
        income.setDateIncome(functions.transformStringtoDate("2023-11-04"));

        // Simula el guardado de la nueva entidad de ingreso
        when(incomesRepository.save(any(IncomesEntity.class))).thenAnswer(i -> {
            IncomesEntity savedIncome = i.getArgument(0);
            savedIncome.setIdIncome(1); // Asigna un ID simulado
            return savedIncome;
        });

        // Simulate job search with a specific ID
        when(jobsService.findJob(anyInt())).thenReturn(null); // Asegúrate de ajustar esto a tu lógica
        
        // When
        IncomesEntity result = incomesService.saveIncomes(1000, "2023-11-04", 1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdIncome()).isEqualTo(1);
        assertThat(result.getAmountIncome()).isEqualTo(1000.0f);
        assertThat(result.getDateIncome()).isEqualTo(functions.transformStringtoDate("2023-11-04"));
    }

    @Test
    void whenAvarageSalary_thenReturnCorrectAverage() {
        // Given
        List<IncomesEntity> incomes = new ArrayList<>();
        IncomesEntity income1 = new IncomesEntity();
        income1.setAmountIncome(1000.0f);
        IncomesEntity income2 = new IncomesEntity();
        income2.setAmountIncome(2000.0f);
        incomes.add(income1);
        incomes.add(income2);

        // Simula la búsqueda de los ingresos
        when(incomesRepository.findByJobIncomeUserJobIdUser(any(Integer.class))).thenReturn(incomes);

        // When
        Float average = incomesService.avarageSalary(1);

        // Then
        assertThat(average).isEqualTo(1500.0f); // El promedio de 1000 y 2000 es 1500
    }

    @Test
    void whenNoIncomes_thenReturnZeroAverage() {
        // Given
        // Simula que no se encuentran ingresos
        when(incomesRepository.findByJobIncomeUserJobIdUser(any(Integer.class))).thenReturn(new ArrayList<>());

        // When
        Float average = incomesService.avarageSalary(1);

        // Then
        assertThat(average).isEqualTo(0f); // Si no hay ingresos, el promedio debe ser 0
    }
}
