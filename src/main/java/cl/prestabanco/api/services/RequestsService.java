package cl.prestabanco.api.services;

import cl.prestabanco.api.models.RequestsEntity;
import cl.prestabanco.api.repositories.RequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public RequestsEntity saveRequest(String stateRequest, Integer leanRequest, Integer userRequest, Integer evaluationRequest) {
        if (stateRequest == null || leanRequest == null || userRequest == null || evaluationRequest == null) {
            return null;
        } else if (userRequest <= 0 || leanRequest <= 0 || evaluationRequest <= 0) {
            return null;
        }

        // Create a request object
        RequestsEntity request = new RequestsEntity();
        request.setStateRequest(stateRequest);
        request.setLeanRequest(loansService.findLoan(leanRequest));
        request.setUserRequest(usersService.findUser(userRequest));
        request.setEvaluationRequest(evaluationsService.findEvaluation(evaluationRequest));

        System.out.println("Request: " + request);
        return requestsRepository.save(request);

    }
}
