package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.LoansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<LoansEntity, Integer> {
}
