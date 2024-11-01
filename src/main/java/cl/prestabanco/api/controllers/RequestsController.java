package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.services.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestsController {
    @Autowired
    private RequestsService requestsService;

    @PostMapping("/save-request")
    public ResponseEntity<RequestsEntity> saveRequest(@RequestBody Map<String, Object> jsonMap){
        try {
            System.out.println("Request: " + jsonMap);
            // the request is saved in database
            RequestsEntity response = requestsService.saveRequest(
                    (String) jsonMap.get("typeRequest"),
                    (String) jsonMap.get("stateRequest"),
                    jsonMap.get("leanRequest"),
                    jsonMap.get("userRequest")
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
