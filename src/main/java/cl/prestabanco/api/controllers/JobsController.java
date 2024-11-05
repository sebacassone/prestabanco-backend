package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.JobsEntity;
import cl.prestabanco.api.services.JobsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin("*")
public class JobsController {
    @Autowired
    private JobsService jobsService;

    @PostMapping("/register-job")
    public ResponseEntity<JobsEntity> registerJob(@RequestBody Map<String, Object> jsonMap) {
        try {
            // the job is saved in database
            JobsEntity response = jobsService.saveJob(
                    (String) jsonMap.get("typeJob"),
                    (String) jsonMap.get("seniorityJob"),
                    (Integer) jsonMap.get("userJob")
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
