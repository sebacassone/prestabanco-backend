package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.SavingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<SavingsEntity, Long> {
}
