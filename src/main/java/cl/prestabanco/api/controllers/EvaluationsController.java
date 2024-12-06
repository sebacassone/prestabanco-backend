package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.services.EvaluationsService;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/evaluations")
@CrossOrigin("*")
public class EvaluationsController {
    @Autowired
    private EvaluationsService evaluationsService;


    @PostMapping("/make-evaluation")
    public ResponseEntity<EvaluationsEntity> makeEvaluation(@RequestBody Map<String, Object> JsonMap) {
        try {
            System.out.println(JsonMap);
            EvaluationsEntity response = evaluationsService.makeEvaluation(
                    (Integer) JsonMap.get("idUser"),
                    (Double) JsonMap.get("quotaLoan"),
                    functions.transformIntegertoDouble((Integer) JsonMap.get("maximumAmount")),
                    (String) JsonMap.get("typeLoan")
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
