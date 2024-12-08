package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.DocumentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentsRepository extends JpaRepository<DocumentsEntity, Integer> {
    List<DocumentsEntity> findByIdRequest(Integer idRequest);
}
