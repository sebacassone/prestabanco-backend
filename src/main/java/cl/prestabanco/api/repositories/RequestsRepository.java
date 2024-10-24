package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.RequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<RequestsEntity, Long> {
}
