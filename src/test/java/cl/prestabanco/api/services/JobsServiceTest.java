package cl.prestabanco.api.services;

import cl.prestabanco.api.models.JobsEntity;
import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.JobsRepository;
import cl.prestabanco.api.utils.functions.functions;
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

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JobsServiceTest {

    @MockBean
    private JobsRepository jobsRepository;

    @MockBean
    private UsersService usersService;

    @Autowired
    private JobsService jobsService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenUserNotFound_thenReturnNull() {
        // Given
        Integer userJob = 1;
        String typeJob = "Developer";
        String seniorityJob = "Junior";

        // Simulate that the user is not found
        when(usersService.findUser(userJob)).thenReturn(null);

        // When
        JobsEntity result = jobsService.saveJob(typeJob, seniorityJob, userJob);

        // Then
        assertThat(result).isNull(); // If user not found, job should not be saved
    }

    @Test
    void whenSaveJob_thenReturnSavedJob() {
        // Given
        Integer userJob = 1;
        String typeJob = "Developer";
        String seniorityJob = "Junior";

        UsersEntity user = new UsersEntity();
        user.setIdUser(userJob);
        // Simulate that the user is found
        when(usersService.findUser(userJob)).thenReturn(user);

        // Simulate saving the job
        when(jobsRepository.save(any(JobsEntity.class))).thenAnswer(i -> {
            JobsEntity job = i.getArgument(0);
            job.setIdJob(1); // Simulate job ID being assigned
            return job;
        });

        // When
        JobsEntity result = jobsService.saveJob(typeJob, seniorityJob, userJob);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdJob()).isEqualTo(1); // Job ID should be assigned
        assertThat(result.getTypeJob()).isEqualTo(typeJob);
        assertThat(result.getSeniorityJob()).isEqualTo(functions.transformStringtoDate(seniorityJob));
    }

    @Test
    void whenFindJob_thenReturnJob() {
        // Given
        Integer jobId = 1;
        JobsEntity job = new JobsEntity();
        job.setIdJob(jobId);
        job.setTypeJob("Developer");
        job.setSeniorityJob(functions.transformStringtoDate("Junior"));

        // Simulate that the job is found
        when(jobsRepository.findById(jobId)).thenReturn(Optional.of(job));

        // When
        JobsEntity result = jobsService.findJob(jobId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdJob()).isEqualTo(jobId);
        assertThat(result.getTypeJob()).isEqualTo("Developer");
    }

    @Test
    void whenJobNotFound_thenReturnNull() {
        // Given
        Integer jobId = 99;

        // Simulate that the job is not found
        when(jobsRepository.findById(jobId)).thenReturn(Optional.empty());

        // When
        JobsEntity result = jobsService.findJob(jobId);

        // Then
        assertThat(result).isNull(); // If job not found, return null
    }

    @Test
    void whenJobIdIsNull_thenReturnNull() {
        // Given
        Integer jobId = null;

        // When
        JobsEntity result = jobsService.findJob(jobId);

        // Then
        assertThat(result).isNull(); // If jobId is null, return null
    }
}
