package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.LoansEntity;
import cl.prestabanco.api.services.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin("*")
public class LoansController {
    @Autowired
    private LoansService loansService;

    @PostMapping("/calculate-loan")
    public ResponseEntity<LoansEntity> calculateLoan(@RequestBody LoansEntity loan) {
        try {
            LoansEntity response = loansService.calculateLoan(loan);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<LoansEntity> saveLoan(@RequestBody LoansEntity loan) {
        try {
            LoansEntity response = loansService.saveLoan(loan);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
