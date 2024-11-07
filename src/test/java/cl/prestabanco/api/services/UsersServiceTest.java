package cl.prestabanco.api.services;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.repositories.UsersRepository;
import cl.prestabanco.api.models.AddressesEntity;
import cl.prestabanco.api.utils.functions.functions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import io.github.cdimascio.dotenv.Dotenv;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UsersServiceTest {

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private AddressesService addressesService;

    @Autowired
    private UsersService usersService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    // Test for saveUser when the parameters are valid
    @Test
    void whenValidUser_thenSaveUser() {
        // Given: valid parameters
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // Create a mock address
        AddressesEntity address = new AddressesEntity();
        address.setIdAddress(1);
        when(addressesService.findAddressAndSave(anyInt())).thenReturn(address);

        // Create user entity
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
        user.setAddressUser(address);

        // When
        when(usersRepository.save(any(UsersEntity.class))).thenReturn(user);
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRutUser()).isEqualTo(rut);
        assertThat(result.getNameUser()).isEqualTo(nameUser);
        assertThat(result.getAddressUser()).isEqualTo(address);
    }

    // Test for saveUser when any parameter is null
    @Test
    void whenAnyParameterIsNull_thenReturnNull() {
        // Given: one parameter is null
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(null, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    // Test for saveUser when any parameter is empty
    @Test
    void whenAnyParameterIsEmpty_thenReturnNull() {
        // Given: one parameter is empty
        String rut = "";  // Empty rut
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    // Test for saveUser when idAddress is less than or equal to zero
    @Test
    void whenIdAddressIsInvalid_thenReturnNull() {
        // Given: invalid idAddress
        Integer idAddress = 0;  // Invalid idAddress

        // When
        UsersEntity result = usersService.saveUser("12345678-9", "John", "Doe", "Smith", "john.doe@example.com", "123456789", "1990-01-01", "ACTIVE", "password123", "ADMIN", idAddress);

        // Then
        assertThat(result).isNull();
    }

    // Test for findUser when the user exists
    @Test
    void whenUserExists_thenReturnUser() {
        // Given: an existing user
        UsersEntity user = new UsersEntity();
        user.setIdUser(1);
        user.setRutUser("12345678-9");

        when(usersRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));

        // When
        UsersEntity result = usersService.findUser(1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRutUser()).isEqualTo("12345678-9");
    }

    // Test for findUser when the user does not exist
    @Test
    void whenUserDoesNotExist_thenReturnNull() {
        // Given: user is not found
        when(usersRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // When
        UsersEntity result = usersService.findUser(999);  // Non-existing user

        // Then
        assertThat(result).isNull();
    }

    // Test for login when credentials are correct
    @Test
    void whenLoginIsSuccessful_thenReturnUser() {
        // Given: valid credentials
        String rutUser = "12345678-9";
        String passwordUser = "password123";
        UsersEntity user = new UsersEntity();
        user.setRutUser(rutUser);
        user.setPasswordUser(passwordUser);

        when(usersRepository.findByRutUserAndPasswordUser(rutUser, passwordUser)).thenReturn(user);

        // When
        UsersEntity result = usersService.login(rutUser, passwordUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRutUser()).isEqualTo(rutUser);
    }

    // Test for login when credentials are incorrect
    @Test
    void whenLoginFails_thenReturnNull() {
        // Given: incorrect credentials
        String rutUser = "12345678-9";
        String passwordUser = "wrongpassword";

        when(usersRepository.findByRutUserAndPasswordUser(rutUser, passwordUser)).thenReturn(null);

        // When
        UsersEntity result = usersService.login(rutUser, passwordUser);

        // Then
        assertThat(result).isNull();
    }

    // Test for login when rutUser or passwordUser is null
    @Test
    void whenLoginPasswordAreNull_thenReturnNull() {
        // Given: credentials are null
        String passwordUser = "wrongpassword";

        // When
        UsersEntity result = usersService.login(null, passwordUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenLoginRutAreNull_thenReturnNull() {
        // Given: credentials are null
        String rutUser = "12345678-9";

        // When
        UsersEntity result = usersService.login(rutUser, null);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenLoginRutAreEmpty_thenReturnNull() {
        // Given: credentials are empty
        String rutUser = "";
        String passwordUser = "123";

        // When
        UsersEntity result = usersService.login(rutUser, passwordUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenLoginPasswordAreEmpty_thenReturnNull() {
        // Given: credentials are empty
        String rutUser = "2.215.151-6";
        String passwordUser = "";

        // When
        UsersEntity result = usersService.login(rutUser, passwordUser);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenNameUserIsNull_thenReturnNull() {
        // Given: `nameUser` is null
        String nameUser = null;
        String rut = "12345678-9";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenFirstLastnameUserIsNull_thenReturnNull() {
        // Given: `firstLastnameUser` is null
        String firstLastnameUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenSecondLastnameUserIsNull_thenReturnNull() {
        // Given: `secondLastnameUser` is null
        String secondLastnameUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenEmailUserIsNull_thenReturnNull() {
        // Given: `emailUser` is null
        String emailUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenPhoneUserIsNull_thenReturnNull() {
        // Given: `phoneUser` is null
        String phoneUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenBirthdayUserIsNull_thenReturnNull() {
        // Given: `birthdayUser` is null
        String birthdayUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenStatusUserIsNull_thenReturnNull() {
        // Given: `statusUser` is null
        String statusUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenPasswordUserIsNull_thenReturnNull() {
        // Given: `passwordUser` is null
        String passwordUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenTypeUserIsNull_thenReturnNull() {
        // Given: `typeUser` is null
        String typeUser = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenIdAddressIsNull_thenReturnNull() {
        // Given: `idAddress` is null
        Integer idAddress = null;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenRutIsEmpty_thenReturnNull() {
        // Given: `rut` is empty
        String rut = "";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenNameUserIsEmpty_thenReturnNull() {
        // Given: `nameUser` is empty
        String nameUser = "";
        String rut = "12345678-9";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenFirstLastnameUserIsEmpty_thenReturnNull() {
        // Given: `firstLastnameUser` is empty
        String firstLastnameUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenSecondLastnameUserIsEmpty_thenReturnNull() {
        // Given: `secondLastnameUser` is empty
        String secondLastnameUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenEmailUserIsEmpty_thenReturnNull() {
        // Given: `emailUser` is empty
        String emailUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenPhoneUserIsEmpty_thenReturnNull() {
        // Given: `phoneUser` is empty
        String phoneUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenBirthdayUserIsEmpty_thenReturnNull() {
        // Given: `birthdayUser` is empty
        String birthdayUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenStatusUserIsEmpty_thenReturnNull() {
        // Given: `statusUser` is empty
        String statusUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String passwordUser = "password123";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenPasswordUserIsEmpty_thenReturnNull() {
        // Given: `passwordUser` is empty
        String passwordUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String typeUser = "ADMIN";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenTypeUserIsEmpty_thenReturnNull() {
        // Given: `typeUser` is empty
        String typeUser = "";
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        Integer idAddress = 1;

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenIdAddressIsZero_thenReturnNull() {
        // Given: `idAddress` is 0
        Integer idAddress = 0;
        String rut = "12345678-9";
        String nameUser = "John";
        String firstLastnameUser = "Doe";
        String secondLastnameUser = "Smith";
        String emailUser = "john.doe@example.com";
        String phoneUser = "123456789";
        String birthdayUser = "1990-01-01";
        String statusUser = "ACTIVE";
        String passwordUser = "password123";
        String typeUser = "ADMIN";

        // When
        UsersEntity result = usersService.saveUser(rut, nameUser, firstLastnameUser, secondLastnameUser, emailUser, phoneUser, birthdayUser, statusUser, passwordUser, typeUser, idAddress);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void whenAgeIsLessThan70_thenReturnTrue() {
        // Given
        Integer idUser = 1;
        Integer age = 30;

        // When
        when(usersRepository.getAge(idUser)).thenReturn(age);

        Boolean result = usersService.applicantsAge(idUser);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void whenAgeIs70_thenReturnFalse() {
        // Given
        Integer idUser = 1;
        Integer age = 70;

        // When
        when(usersRepository.getAge(idUser)).thenReturn(age);

        Boolean result = usersService.applicantsAge(idUser);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void whenAgeIsGreaterThan70_thenReturnFalse() {
        // Given
        Integer idUser = 1;
        Integer age = 75;

        // When
        when(usersRepository.getAge(idUser)).thenReturn(age);

        Boolean result = usersService.applicantsAge(idUser);

        // Then
        assertThat(result).isFalse();
    }

}
