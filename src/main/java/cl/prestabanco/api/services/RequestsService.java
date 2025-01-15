package cl.prestabanco.api.services;

import cl.prestabanco.api.DTOs.RequestsWithTypeLoan;
import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.repositories.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestsService {
    private final RequestsRepository requestsRepository;
    private final UsersService usersService;
    private final LoansService loansService;
    private final EvaluationsService evaluationsService;
    @Autowired
    public RequestsService(RequestsRepository requestsRepository, UsersService usersService, LoansService loansService, EvaluationsService evaluationsService) {
        this.requestsRepository = requestsRepository;
        this.usersService = usersService;
        this.loansService = loansService;
        this.evaluationsService = evaluationsService;
    }

    public RequestsEntity saveRequest(String stateRequest, Integer leanRequest, Integer userRequest) {
        if (stateRequest == null || leanRequest == null || userRequest == null) {
            return null;
        } else if (userRequest <= 0 || leanRequest <= 0) {
            return null;
        }

        // Create a request object
        RequestsEntity request = new RequestsEntity();
        request.setStateRequest(stateRequest);
        request.setLeanRequest(loansService.findLoan(leanRequest));
        request.setUserRequest(usersService.findUser(userRequest));

        System.out.println("Request: " + request);
        return requestsRepository.save(request);

    }

    public List<RequestsWithTypeLoan> getAllRequests() {
        List<RequestsEntity> requests = requestsRepository.findAll();
        return getRequestsEntities(requests);
    }

    private List<RequestsWithTypeLoan> getRequestsEntities(List<RequestsEntity> requests) {
        List<RequestsWithTypeLoan> requestsWithTypeLoan = new ArrayList<>();
        for (RequestsEntity request : requests) {
            System.out.println("Request: " + request);
            RequestsWithTypeLoan requestWithTypeLoan = new RequestsWithTypeLoan();
            requestWithTypeLoan.setIdRequest(request.getIdRequest());
            requestWithTypeLoan.setStateRequest(request.getStateRequest());
            requestWithTypeLoan.setTypeLoan(request.getLeanRequest().getTypeLoan());
            String[] DocumentsRequired = switch (request.getLeanRequest().getTypeLoan()) {
                case "Primera Vivienda" -> new String[]{"Comprobante de Ingresos", "Certificado de Avalúo", "Historial crediticio"};
                case "Segunda Vivienda" -> new String[]{"Comprobante de Ingresos", "Certificado de Avalúo", "Historial crediticio", "Escritura de primera propiedad"};
                case "Propiedades Comerciales" -> new String[]{"Estado financiero del negocio", "Comprobante de ingresos", "Certificado de Avalúo", "Plan de negocios"};
                case "Remodelación" -> new String[]{"Comprobante de Ingresos", "Presupuesto de la remodelación", "Certificado de Avalúo Actualizado"};
                default -> new String[0];
            };
            requestWithTypeLoan.setDocumentsRequired(DocumentsRequired);
            requestWithTypeLoan.setLeanRequest(request.getLeanRequest());
            if (request.getIdEvaluation() != null) {
                requestWithTypeLoan.setEvaluation(evaluationsService.findEvaluation(request.getIdEvaluation()));
            }

            requestsWithTypeLoan.add(requestWithTypeLoan);
        }
        return requestsWithTypeLoan;
    }

    public RequestsEntity updateRequest(Integer idRequest, String stateRequest, Integer idEvaluation) {
        RequestsEntity request = requestsRepository.findById(idRequest).orElse(null);
        if (request == null) {
            return null;
        }
        request.setStateRequest(stateRequest);
        if (idEvaluation != null){
            request.setIdEvaluation(idEvaluation);
        }
        return requestsRepository.save(request);
    }

    public List<RequestsWithTypeLoan> getRequestByIdUser(Integer idUser) {
        if (idUser <= 0) {
            return null;
        } else if (usersService.findUser(idUser) == null) {
            return null;
        }
        // Get all requests from a user and return the type of loan and the documents required
        List<RequestsEntity> requests = requestsRepository.findRequestsByIdUser(idUser);
        if (requests == null) {
            return null;
        }
        return getRequestsEntities(requests);
    }
}
