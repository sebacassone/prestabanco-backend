package cl.prestabanco.api.services;

import cl.prestabanco.api.models.AddressesEntity;
import cl.prestabanco.api.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressesService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressesService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressesEntity findAddressAndSave(AddressesEntity address) {
        if (address == null) {
            return null;
        }
        if (address.getStreetAddress() == null || address.getNumberAddress() == null || address.getCommuneAddress() == null || address.getRegionAddress() == null || address.getCountryAddress() == null) {
            return null;
        }

        // Find address by street, number, commune, region and country in case it already exists
        AddressesEntity addressEntity = addressRepository.findByStreetAddressAndNumberAddressAndCommuneAddressAndRegionAddressAndCountryAddress(
                address.getStreetAddress(),
                address.getNumberAddress(),
                address.getCommuneAddress(),
                address.getRegionAddress(),
                address.getCountryAddress()
        );
        // If address does not exist, save it
        if (addressEntity == null) {
            addressEntity = addressRepository.save(address);
        }
        return addressEntity;
    }

    public AddressesEntity findAddressAndSave(Integer idAddress) {
        if (idAddress == null || idAddress == 0 || idAddress < 0) {
            return null;
        }

        // Find address by id in case it already exists
        AddressesEntity addressEntity = addressRepository.findById(idAddress).orElse(null);
        System.out.println("addressEntity: " + addressEntity);
        // If address does not exist, save it
        return addressEntity;
    }

}
