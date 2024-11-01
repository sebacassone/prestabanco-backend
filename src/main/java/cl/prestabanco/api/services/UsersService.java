package cl.prestabanco.api.services;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.UsersRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AddressesService addressesService;

    public UsersEntity saveUser(String rut, String nameUser, String firstLastnameUser, String secondLastnameUser, String emailUser, String phoneUser, String birthdayUser, String statusUser, String passwordUser, String typeUser, Object idAddress) {
        // Create a user object
        UsersEntity user = new UsersEntity();
        user.setRutUser(rut);
        user.setNameUser(nameUser);
        user.setFirstLastnameUser(firstLastnameUser);
        user.setSecondLastnameUser(secondLastnameUser);
        user.setEmailUser(emailUser);
        user.setPhoneUser(phoneUser);
        user.setBirthdayUser(functions.transformStringtoDate(birthdayUser));
        user.setStatusUser(statusUser);
        user.setPasswordUser(passwordUser);
        user.setTypeUser(typeUser);
        user.setAddressUser(addressesService.findAddressAndSave(functions.transformLong(idAddress)));

        System.out.println("User: " + user);
        return usersRepository.save(user);

    }

    public UsersEntity findUser(Long idUser) {
        return usersRepository.findById(idUser).orElse(null);
    }

    public UsersEntity login(String rutUser, String passwordUser) {
        return usersRepository.findByRutUserAndPasswordUser(rutUser, passwordUser);
    }
}
