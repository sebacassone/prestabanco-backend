package cl.prestabanco.api.services;

import cl.prestabanco.api.models.DocumentsEntity;
import cl.prestabanco.api.repositories.DocumentsRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DocumentsService {

    private final DocumentsRepository documentsRepository;

    @Autowired
    @Generated
    public DocumentsService(DocumentsRepository documentsRepository, RequestsService requestsService) {
        this.documentsRepository = documentsRepository;
    }

    @Generated
    public DocumentsEntity uploadFile(MultipartFile file, Integer idRequest) throws IOException {
        // Validación del archivo
        if (file.isEmpty()) {
            return null;
        }

        // Crear la entidad y retornarla
        DocumentsEntity document = new DocumentsEntity();
        document.setIdRequest(idRequest);
        document.setFileName(file.getOriginalFilename());
        document.setFileContent(file.getBytes());
        document.setUploadDateDocument(new Date());
        documentsRepository.save(document);
        return document;
    }

    @Generated
    public List<DocumentsEntity> getDocumentsByIdRequest(Integer idRequest) {
        return documentsRepository.findByIdRequest(idRequest);
    }
}
