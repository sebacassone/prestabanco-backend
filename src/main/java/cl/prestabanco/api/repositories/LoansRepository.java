package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.LoansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoansRepository extends JpaRepository<LoansEntity, Integer> {
    @Query(value = "SELECT * FROM loans l INNER JOIN requests r ON l.id_loan = r.id_lean WHERE r.id_request = 1", nativeQuery = true)
    LoansEntity findLoanByIdRequest(@Param("id_request") Integer id_request);
}
