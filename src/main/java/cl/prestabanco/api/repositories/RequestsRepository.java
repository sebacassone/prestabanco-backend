package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.RequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestsRepository extends JpaRepository<RequestsEntity, Integer> {
    @Query(value = "SELECT * FROM requests WHERE id_user = :id_user", nativeQuery = true)
    List<RequestsEntity> findRequestsByIdUser(@Param("id_user") Integer id_user);
}
