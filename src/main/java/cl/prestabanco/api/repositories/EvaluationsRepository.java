package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.EvaluationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationsRepository extends JpaRepository<EvaluationsEntity, Integer> {
}
