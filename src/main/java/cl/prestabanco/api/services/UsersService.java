package cl.prestabanco.api.services;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.UsersRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final AddressesService addressesService;
    @Autowired
    public UsersService(UsersRepository usersRepository, AddressesService addressesService) {
        this.usersRepository = usersRepository;
        this.addressesService = addressesService;
    }

    public UsersEntity saveUser(String rut, String nameUser, String firstLastnameUser, String secondLastnameUser, String emailUser, String phoneUser, String birthdayUser, String statusUser, String passwordUser, String typeUser, Integer idAddress) {
        if (rut == null || nameUser == null || firstLastnameUser == null || secondLastnameUser == null || emailUser == null || phoneUser == null || birthdayUser == null || statusUser == null || passwordUser == null || typeUser == null || idAddress == null) {
            return null;
        } else if (rut.isEmpty() || nameUser.isEmpty() || firstLastnameUser.isEmpty() || secondLastnameUser.isEmpty() || emailUser.isEmpty() || phoneUser.isEmpty() || birthdayUser.isEmpty() || statusUser.isEmpty() || passwordUser.isEmpty() || typeUser.isEmpty() || idAddress <= 0) {
            return null;
        }
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
        user.setAddressUser(addressesService.findAddressAndSave(idAddress));

        return usersRepository.save(user);

    }

    public UsersEntity findUser(Integer idUser) {
        return usersRepository.findById(idUser).orElse(null);
    }

    public UsersEntity login(String rutUser, String passwordUser) {
        if (rutUser == null || passwordUser == null) {
            return null;
        } else if (rutUser.isEmpty() || passwordUser.isEmpty()) {
            return null;
        }
        return usersRepository.findByRutUserAndPasswordUser(rutUser, passwordUser);
    }
}
