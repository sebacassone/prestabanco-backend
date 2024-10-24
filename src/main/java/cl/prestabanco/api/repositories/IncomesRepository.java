package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.IncomesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomesRepository extends JpaRepository<IncomesEntity, Long> {
}
