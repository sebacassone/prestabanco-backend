package cl.prestabanco.api.controllers;

import cl.prestabanco.api.models.DocumentsEntity;
import cl.prestabanco.api.services.DocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/documents")
@CrossOrigin("*")
public class DocumentsController {

    private final DocumentsService documentsService;

    @Autowired
    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @Transactional
    @PostMapping("/upload/{idRequest}")
    public ResponseEntity<List<DocumentsEntity>> uploadFiles(@RequestParam("files") List<MultipartFile> files, @PathVariable("idRequest") Integer idRequest) {
        try {
            List<DocumentsEntity> responses = files.stream()
                    .map(file -> {
                        try {
                            return documentsService.uploadFile(file, idRequest);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }) .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @Transactional(readOnly = true)
    @GetMapping("/get-document-by-id-request/{idRequest}")
    public ResponseEntity<List<DocumentsEntity>> getDocumentsByIdRequest(@PathVariable("idRequest") Integer idRequest) {
        try {
            List<DocumentsEntity> documents = documentsService.getDocumentsByIdRequest(idRequest);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/{idDocument}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("idDocument") Integer idDocument) {
        try {
            DocumentsEntity document = documentsService.getDocumentById(idDocument);

            // Crear recurso a partir del contenido del archivo
            ByteArrayResource resource = new ByteArrayResource(document.getFileContent());

            // Determinar el tipo MIME basado en la extensión del archivo
            String mimeType = documentsService.determineMimeType(document.getFileName());
            if (mimeType == null) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback genérico
            }

            System.out.println("MIME type: " + mimeType);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
