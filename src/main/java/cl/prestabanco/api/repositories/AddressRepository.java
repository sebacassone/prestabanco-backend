package cl.prestabanco.api.repositories;

import cl.prestabanco.api.models.AddressesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressesEntity, Long> {
}
