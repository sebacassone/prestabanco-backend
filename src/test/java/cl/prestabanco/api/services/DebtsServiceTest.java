package cl.prestabanco.api.services;

import cl.prestabanco.api.DTOs.DebtsWithMorosityDTO;
import cl.prestabanco.api.repositories.DebtsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.github.cdimascio.dotenv.Dotenv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DebtsServiceTest {

    @MockBean
    private DebtsRepository debtsRepository;

    @Autowired
    private DebtsService debtsService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenUserIdIsNull_thenReturnFalse() {
        // Given
        Integer userId = null;

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenUserIdIsZero_thenReturnFalse() {
        // Given
        Integer userId = 0;

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenNoDebts_thenReturnFalse() {
        // Given
        Integer userId = 1;
        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenReturn(new ArrayList<>());
        when(debtsRepository.findUnpaidDebtsWithMorosity(userId)).thenReturn(new ArrayList<>());

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenUnpaidDebtsWithoutMorosity_thenReturnTrue() {
        // Given
        Integer userId = 1;
        DebtsWithMorosityDTO debt = new DebtsWithMorosityDTO();
        List<DebtsWithMorosityDTO> debtsWithoutMorosity = new ArrayList<>();
        debtsWithoutMorosity.add(debt);
        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenReturn(debtsWithoutMorosity);
        when(debtsRepository.findUnpaidDebtsWithMorosity(userId)).thenReturn(new ArrayList<>());

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenUnpaidDebtsWithMorosity_thenReturnTrue() {
        // Given
        Integer userId = 1;
        DebtsWithMorosityDTO debt = new DebtsWithMorosityDTO();
        List<DebtsWithMorosityDTO> debtsWithMorosity = new ArrayList<>();
        debtsWithMorosity.add(debt);
        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenReturn(new ArrayList<>());
        when(debtsRepository.findUnpaidDebtsWithMorosity(userId)).thenReturn(debtsWithMorosity);

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenDebtsWithAndWithoutMorosity_thenReturnTrue() {
        // Given
        Integer userId = 1;
        DebtsWithMorosityDTO debtWithoutMorosity = new DebtsWithMorosityDTO();
        DebtsWithMorosityDTO debtWithMorosity = new DebtsWithMorosityDTO();
        List<DebtsWithMorosityDTO> debtsWithoutMorosity = new ArrayList<>();
        List<DebtsWithMorosityDTO> debtsWithMorosity = new ArrayList<>();
        debtsWithoutMorosity.add(debtWithoutMorosity);
        debtsWithMorosity.add(debtWithMorosity);

        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenReturn(debtsWithoutMorosity);
        when(debtsRepository.findUnpaidDebtsWithMorosity(userId)).thenReturn(debtsWithMorosity);

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenRepositoryReturnsNullForDebts_thenReturnFalse() {
        // Given
        Integer userId = 1;
        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenReturn(null);
        when(debtsRepository.findUnpaidDebtsWithMorosity(userId)).thenReturn(null);

        // When
        Boolean result = debtsService.hasUnpaidDebtsOrMorocities(userId);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenRepositoryThrowsException_thenHandleGracefully() {
        // Given
        Integer userId = 1;
        when(debtsRepository.findUnpaidDebtsWithoutMorosity(userId)).thenThrow(new RuntimeException("Database error"));

        // When
        try {
            debtsService.hasUnpaidDebtsOrMorocities(userId);
        } catch (Exception e) {
            // Then
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("Database error");
        }
    }
}
