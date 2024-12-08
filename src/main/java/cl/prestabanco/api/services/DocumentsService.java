package cl.prestabanco.api.services;

import cl.prestabanco.api.models.DocumentsEntity;
import org.springframework.http.MediaType;
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

    @Generated
    public DocumentsEntity getDocumentById(Integer idDocument) {
        return documentsRepository.findById(idDocument).orElse(null);
    }

    @Generated
    public String determineMimeType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return MediaType.APPLICATION_PDF_VALUE;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case "png":
                return MediaType.IMAGE_PNG_VALUE;
            case "txt":
                return MediaType.TEXT_PLAIN_VALUE;
            case "doc":
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default:
                return null; // Si no se encuentra, será tratado como binario
        }
    }
}
