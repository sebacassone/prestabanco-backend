package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.JobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<JobsEntity, Long> {
}
