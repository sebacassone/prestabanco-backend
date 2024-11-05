package cl.prestabanco.api.repositories;

import cl.prestabanco.api.DTOs.DebtsWithMorosityDTO;
import cl.prestabanco.api.models.MorocitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MorocitiesRepository extends JpaRepository<MorocitiesEntity, Integer> {

}
