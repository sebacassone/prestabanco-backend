package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.DocumentsEntity;
import cl.prestabanco.api.services.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentsController {

    private final DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @PostMapping("/upload/{idRequest}")
    public ResponseEntity<DocumentsEntity> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idRequest") Integer idRequest) {
        try {
            DocumentsEntity response = documentsService.uploadFile(file, idRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
