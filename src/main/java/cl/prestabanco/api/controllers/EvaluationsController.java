package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.services.EvaluationsService;
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

    /*
    @PostMapping("/make-evaluation")
    public ResponseEntity<EvaluationsEntity> makeEvaluation(@RequestBody Map<String, Object> JsonMap) {
        try {
            EvaluationsEntity response = evaluationsService.makeEvaluation(
                    JsonMap.get("idUser"),
                    (Float) JsonMap.get("quotaLoan"),
                    (Float) JsonMap.get("maximumAmount")
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }*/
}
