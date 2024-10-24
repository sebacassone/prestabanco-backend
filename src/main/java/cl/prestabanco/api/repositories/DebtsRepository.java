package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.DebtsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtsRepository extends JpaRepository<DebtsEntity, Long> {
}
