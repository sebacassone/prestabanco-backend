package cl.prestabanco.api.controllers;

import cl.prestabanco.api.DTOs.RequestsWithTypeLoan;
import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.services.RequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/requests")
@CrossOrigin("*")
public class RequestsController {
    @Autowired
    private RequestsService requestsService;

    @PostMapping("/save-request")
    public ResponseEntity<RequestsEntity> saveRequest(@RequestBody Map<String, Object> jsonMap){
        try {
            System.out.println("Request: " + jsonMap);
            // the request is saved in database
            RequestsEntity response = requestsService.saveRequest(
                    (String) jsonMap.get("stateRequest"),
                    (Integer) jsonMap.get("leanRequest"),
                    (Integer) jsonMap.get("userRequest")
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-all-requests")
    public ResponseEntity<List<RequestsWithTypeLoan>> getAllRequests() {
        try {
            List<RequestsWithTypeLoan> response = requestsService.getAllRequests();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update-state-request/{idRequest}")
    public ResponseEntity<RequestsEntity> updateStateRequest(@PathVariable("idRequest") Integer idRequest, @RequestBody Map<String, Object> jsonMap) {
        try {
            RequestsEntity response = requestsService.updateStateRequest(idRequest, (String) jsonMap.get("stateRequest"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-request/{idUser}")
    public ResponseEntity<List<RequestsWithTypeLoan>> getRequestById(@PathVariable("idUser") Integer idUser){
        try {
            List<RequestsWithTypeLoan> response = requestsService.getRequestByIdUser(idUser);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
