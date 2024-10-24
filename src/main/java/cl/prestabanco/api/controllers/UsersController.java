package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/registrar-usuario")
    public ResponseEntity<UsersEntity> registerUser(@RequestBody UsersEntity user) {
        try {
            // the user is saved in database
            UsersEntity response = usersService.saveUser(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
