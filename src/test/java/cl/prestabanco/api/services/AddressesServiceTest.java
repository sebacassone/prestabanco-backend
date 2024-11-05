package cl.prestabanco.api.services;

import cl.prestabanco.api.models.AddressesEntity;
import cl.prestabanco.api.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.cdimascio.dotenv.Dotenv;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AddressesServiceTest {
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private AddressesService addressesService;

    @BeforeAll
    public static void setup() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
    }

    @Test
    void whenAddressIsNull_thenThrowException() {
        // Given
        AddressesEntity address = null;
        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenCommuneIsVoid_thenThrowException() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setCommuneAddress(null);
        address.setStreetAddress("Same Street");
        address.setNumberAddress(123);
        address.setRegionAddress("Same Region");
        address.setCountryAddress("Same Country");

        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenStreetIsVoid_thenThrowException() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setCommuneAddress("Same Commune");
        address.setStreetAddress(null);
        address.setNumberAddress(123);
        address.setRegionAddress("Same Region");
        address.setCountryAddress("Same Country");

        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenNumberIsVoid_thenThrowException() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setCommuneAddress("Same Commune");
        address.setStreetAddress("Same Street");
        address.setNumberAddress(null);
        address.setRegionAddress("Same Region");
        address.setCountryAddress("Same Country");

        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenRegionIsVoid_thenThrowException() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setCommuneAddress("Same Commune");
        address.setStreetAddress("Same Street");
        address.setNumberAddress(123);
        address.setRegionAddress(null);
        address.setCountryAddress("Same Country");

        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenCountryIsVoid_thenThrowException() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setCommuneAddress("Same Commune");
        address.setStreetAddress("Same Street");
        address.setNumberAddress(123);
        address.setRegionAddress("Same Region");
        address.setCountryAddress(null);

        // When
        AddressesEntity  result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenExistingAddress_thenReturnExistingAddress() {
        // Given
        AddressesEntity existingAddress = new AddressesEntity();
        existingAddress.setIdAddress(1);
        existingAddress.setStreetAddress("Same Street");
        existingAddress.setNumberAddress(123);
        existingAddress.setCommuneAddress("Same Commune");
        existingAddress.setRegionAddress("Same Region");
        existingAddress.setCountryAddress("Same Country");

        AddressesEntity address = new AddressesEntity();
        address.setStreetAddress("Same Street");
        address.setNumberAddress(123);
        address.setCommuneAddress("Same Commune");
        address.setRegionAddress("Same Region");
        address.setCountryAddress("Same Country");

        // When
        when(addressRepository.findByStreetAddressAndNumberAddressAndCommuneAddressAndRegionAddressAndCountryAddress( anyString(), anyInt(), anyString(), anyString(), anyString())).thenReturn(existingAddress);
        // Actually calling the real method on the service
        AddressesEntity result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isEqualTo(existingAddress);
    }

    @Test
    void whenNotExistingAddress_thenReturnExistingAddress() {
        // Given
        AddressesEntity address = new AddressesEntity();
        address.setStreetAddress("Same Street");
        address.setNumberAddress(123);
        address.setCommuneAddress("Same Commune");
        address.setRegionAddress("Same Region");
        address.setCountryAddress("Same Country");

        // When
        when(addressRepository.findByStreetAddressAndNumberAddressAndCommuneAddressAndRegionAddressAndCountryAddress( anyString(), anyInt(), anyString(), anyString(), anyString())).thenReturn(null);
        when(addressRepository.save(any(AddressesEntity.class))).thenAnswer(i -> {
            AddressesEntity addressEntity = i.getArgument(0);
            addressEntity.setIdAddress(1);
            return addressEntity;
        });
        // Actually calling the real method on the service
        AddressesEntity result = addressesService.findAddressAndSave(address);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getIdAddress()).isEqualTo(1L);
        assertThat(result.getStreetAddress()).isEqualTo("Same Street");
        assertThat(result.getNumberAddress()).isEqualTo(123);
        assertThat(result.getCommuneAddress()).isEqualTo("Same Commune");
        assertThat(result.getRegionAddress()).isEqualTo("Same Region");
        assertThat(result.getCountryAddress()).isEqualTo("Same Country");
    }

    @Test
    void whenIdAddressIsNull_thenThrowException() {
        // Given
        Integer idAddress = null;
        // When
        AddressesEntity  result = addressesService.findAddressAndSave(idAddress);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenExistingIdAddress_thenReturnExistingAddress() {
        // Given
        AddressesEntity existingAddress = new AddressesEntity();
        existingAddress.setIdAddress(1);
        existingAddress.setStreetAddress("Same Street");
        existingAddress.setNumberAddress(123);
        existingAddress.setCommuneAddress("Same Commune");
        existingAddress.setRegionAddress("Same Region");
        existingAddress.setCountryAddress("Same Country");

        // When
        when(addressRepository.findById(anyInt())).thenReturn(java.util.Optional.of(existingAddress));
        // Actually calling the real method on the service
        AddressesEntity result = addressesService.findAddressAndSave(1);

        // Then
        assertThat(result).isEqualTo(existingAddress);
    }

    @Test
    void whenIdAddressIsZero_thenThrowException() {
        // Given
        Integer idAddress = 0;
        // When
        AddressesEntity  result = addressesService.findAddressAndSave(idAddress);

        // Then
        assertThat(result).isEqualTo(null);
    }

    @Test
    void whenIdAddressIsNegative_thenThrowException() {
        // Given
        Integer idAddress = -1;
        // When
        AddressesEntity  result = addressesService.findAddressAndSave(idAddress);

        // Then
        assertThat(result).isEqualTo(null);
    }
}
