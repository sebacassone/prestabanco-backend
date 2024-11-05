package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.JobsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobsRepository extends JpaRepository<JobsEntity, Integer> {
    @Query(
            value = "SELECT u.id_user, u.name_user, j.type_job, j.seniority_job, i.amount_income, i.date_income\n" +
                    "FROM users u\n" +
                    "JOIN jobs j ON u.id_user = j.id_user\n" +
                    "LEFT JOIN incomes i ON j.id_job = i.id_job \n" +
                    "WHERE ((j.type_job = 'independiente' AND i.date_income <= CURRENT_DATE - INTERVAL '2 years')\n" +
                    "OR (j.type_job = 'dependiente' AND j.seniority_job >= CURRENT_DATE - INTERVAL '1 years')) " +
                    "AND u.id_user = :idUser",
            nativeQuery = true
    )
    JobsEntity hasSeniority(@Param("idUser") Integer idUser);
}
