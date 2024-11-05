package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.UsersEntity;
import cl.prestabanco.api.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/register-user")
    public ResponseEntity<UsersEntity> registerUser(@RequestBody Map<String, Object> jsonMap) {
        try {
            System.out.println("User: " + jsonMap);
            // the user is saved in database
            UsersEntity response = usersService.saveUser(
                    (String) jsonMap.get("rutUser"),
                    (String) jsonMap.get("nameUser"),
                    (String) jsonMap.get("firstLastnameUser"),
                    (String) jsonMap.get("secondLastNameUser"),
                    (String) jsonMap.get("emailUser"),
                    (String) jsonMap.get("phoneUser"),
                    (String) jsonMap.get("birthdayUser"),
                    (String) jsonMap.get("statusUser"),
                    (String) jsonMap.get("passwordUser"),
                    (String) jsonMap.get("typeUser"),
                    (Integer) jsonMap.get("idAddress")
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsersEntity> login(@RequestBody Map<String, Object> jsonMap) {
        try {
            System.out.println("User: " + jsonMap);
            // the user is saved in database
            UsersEntity response = usersService.login(
                    (String) jsonMap.get("rutUser"),
                    (String) jsonMap.get("passwordUser")
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
