package cl.prestabanco.api.services;

import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.repositories.RequestsRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RequestsService {
    @Autowired
    private RequestsRepository requestsRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private LoansService loansService;

    public RequestsEntity saveRequest(String typeRequest, String stateRequest, Object leanRequest, Object userRequest) {
        // Create a request object
        RequestsEntity request = new RequestsEntity();
        request.setTypeRequest(typeRequest);
        request.setStateRequest(stateRequest);
        request.setLeanRequest(loansService.findLoan(functions.transformLong(leanRequest)));
        request.setUserRequest(usersService.findUser(functions.transformLong(userRequest)));

        System.out.println("Request: " + request);
        return requestsRepository.save(request);

    }
}
