package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.SavingCapacityEvaluationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingCapacityEvaluationsRepository extends JpaRepository<SavingCapacityEvaluationsEntity, Integer> {
}
