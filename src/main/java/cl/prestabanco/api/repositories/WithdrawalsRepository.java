package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.WithdrawalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalsRepository extends JpaRepository<WithdrawalsEntity, Long> {
}
