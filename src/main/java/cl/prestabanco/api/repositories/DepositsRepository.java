package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.DepositsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositsRepository extends JpaRepository<DepositsEntity, Integer> {
}
