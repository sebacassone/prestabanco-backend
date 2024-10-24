package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.DocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsRepository extends JpaRepository<DocumentsEntity, Long> {
}
