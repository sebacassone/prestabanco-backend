package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByRutUserAndPasswordUser(String rutUser, String passwordUser);

    @Query(value = "SELECT  EXTRACT(YEAR FROM AGE(birthday_user))\n" +
            "FROM users\n" +
            "WHERE id_user = :idUser", nativeQuery = true)
    Integer getAge(@Param("idUser") Integer idUser);
}
