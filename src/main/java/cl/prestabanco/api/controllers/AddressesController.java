package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.AddressesEntity;
import cl.prestabanco.api.services.AddressesService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@CrossOrigin("*")
public class AddressesController {

    @Autowired
    private AddressesService addressesService;

    @PostMapping("/register-address")
    public ResponseEntity<AddressesEntity> registerAddress(@RequestBody AddressesEntity address) {
        try {
            // the address is saved in database
            AddressesEntity response = addressesService.findAddressAndSave(address);
            if (response == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
