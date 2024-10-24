package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.MorocitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MorocitiesRepository extends JpaRepository<MorocitiesEntity, Long> {
}
