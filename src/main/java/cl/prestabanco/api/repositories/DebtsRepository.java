package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.DebtsEntity;
import cl.prestabanco.api.DTOs.DebtsWithMorosityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtsRepository extends JpaRepository<DebtsEntity, Integer> {

    @Query(
            value = "SELECT s.id_user, s.rut_user, s.email_user, d.id_debt, d.amount_debt, d.state_debt, NULL " +
                    "FROM users s " +
                    "INNER JOIN debts d ON s.id_user = d.id_user " +
                    "WHERE d.state_debt = 'inpago' AND s.id_user = :idUser", nativeQuery = true
    )
    List<DebtsWithMorosityDTO> findUnpaidDebtsWithoutMorosity(@Param("idUser") Integer idUser);

    @Query(
            value = "SELECT s.id_user, s.rut_user, s.email_user, d.id_debt, d.amount_debt, d.state_debt, m.amount_overdue_morocity " +
                    "FROM users s " +
                    "INNER JOIN debts d ON s.id_user = d.id_user " +
                    "INNER JOIN morocities m ON d.id_debt = m.id_debt " +
                    "WHERE d.state_debt = 'inpago' AND s.id_user = :idUser +" +
                    "AND NOT EXISTS (" +
                        "SELECT 1 " +
                        "FROM users s1 "+
                        "INNER JOIN debts d1 ON s1.id_user = d1.id_user " +
                        "WHERE d1.state_debt = 'inpago' AND s1.id_user = :idUser" +
                    ")", nativeQuery = true
    )
    List<DebtsWithMorosityDTO> findUnpaidDebtsWithMorosity(@Param("idUser") Integer idUser);

    @Query(
            value = "SELECT SUM(amount_debt) " +
                    "FROM users s " +
                    "INNER JOIN debts d ON s.id_user = d.id_user " +
                    "WHERE s.id_user = :idUser", nativeQuery = true
    )
    Float getSumAmountDebt (@Param("idUser") Integer idUser);
}
