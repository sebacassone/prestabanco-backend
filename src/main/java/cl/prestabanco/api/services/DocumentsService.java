package cl.prestabanco.api.services;

import cl.prestabanco.api.models.DocumentsEntity;
import cl.prestabanco.api.repositories.DocumentsRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class DocumentsService {

    @Value("${file.upload-dir:storage}")
    private String uploadDir;

    private final DocumentsRepository documentsRepository;
    private final RequestsService requestsService;

    @Autowired
    @Generated
    public DocumentsService(DocumentsRepository documentsRepository, RequestsService requestsService) {
        this.documentsRepository = documentsRepository;
        this.requestsService = requestsService;
    }

    @Generated
    public DocumentsEntity uploadFile(MultipartFile file, Integer idRequest) throws IOException {
        // Validación del archivo
        if (file.isEmpty()) {
            return null;
        }

        // Validación de la extensión del archivo (solo PDF)
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (!fileName.endsWith(".pdf")) {
            return null;
        }
        // Crear la carpeta 'storage' si no existe
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // Guardar el archivo en el directorio
        Path targetLocation = path.resolve(fileName);
        file.transferTo(targetLocation);

        // Crear la entidad y retornarla
        DocumentsEntity document = new DocumentsEntity();
        document.setUrlDocument(targetLocation.toString());
        document.setUploadDateDocument(new Date());
        document.setRequestsDocument(requestsService.getRequestById(idRequest));
        documentsRepository.save(document);

        return document;
    }
}
