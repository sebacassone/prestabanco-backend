package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.IncomesEntity;
import cl.prestabanco.api.services.IncomesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/incomes")
@CrossOrigin("*")
public class IncomesController {
    @Autowired
    private IncomesService incomesService;

    @PostMapping("/register-incomes")
    public ResponseEntity<List<IncomesEntity>> setIncomes(@RequestBody List<Map<String, Object>> incomesList) {
        try {
            // Convertir y guardar cada usuario
            List<IncomesEntity> responseList = incomesList.stream().map(jsonMap -> {
                try {
                    return incomesService.saveIncomes(
                            jsonMap.get("amountIncome"),
                            (String) jsonMap.get("dateIncome"),
                            jsonMap.get("jobIncome")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
