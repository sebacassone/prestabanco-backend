package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.IncomesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomesRepository extends JpaRepository<IncomesEntity, Integer> {
    List<IncomesEntity> findByJobIncomeUserJobIdUser(Integer idUser);
}
