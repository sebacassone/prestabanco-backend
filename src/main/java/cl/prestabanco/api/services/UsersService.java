package cl.prestabanco.api.services;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public UsersEntity saveUser(UsersEntity user) {
        return usersRepository.save(user);
    }
}
