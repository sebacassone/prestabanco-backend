package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    UsersEntity findByRutUserAndPasswordUser(String rutUser, String passwordUser);
}
