package cl.prestabanco.api.services;

import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.repositories.EvaluationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationsService {
    private final EvaluationsRepository evaluationsRepository;
    private final UsersService usersService;
    private final IncomesService incomesService;
    private final DebtsService debtsService;
    private final JobsService jobsService;

    @Autowired
    public EvaluationsService(EvaluationsRepository evaluationsRepository, UsersService usersService, IncomesService incomesService, DebtsService debtsService, JobsService jobsService) {
        this.evaluationsRepository = evaluationsRepository;
        this.usersService = usersService;
        this.incomesService = incomesService;
        this.debtsService = debtsService;
        this.jobsService = jobsService;
    }


    public EvaluationsEntity findEvaluation(Integer id) {
        return evaluationsRepository.findById(id).orElse(null);
    }

    public EvaluationsEntity saveEvaluation(EvaluationsEntity evaluation) {
        if (evaluation == null) {
            return null;
        }
        return evaluationsRepository.save(evaluation);
    }

    public EvaluationsEntity makeEvaluation(Integer idUser, Float quotaLoan, Object maximumAmount) {
        // New evaluation
        EvaluationsEntity evaluation = new EvaluationsEntity();

        // Find the user and find the incomes of the user
        Float averageSalary = incomesService.avarageSalary(idUser);
        if (averageSalary == 0) {
            return null;
        }
        // Calculate the quota income ratio
        Float quotaIncome = quotaLoan/averageSalary * 100;
        Boolean hasUnpaidDebtsOrMorocities = debtsService.hasUnpaidDebtsOrMorocities(idUser);
        Boolean seniorityEvaluation = jobsService.hasSeniority(idUser);

        return evaluation;
    }
}
