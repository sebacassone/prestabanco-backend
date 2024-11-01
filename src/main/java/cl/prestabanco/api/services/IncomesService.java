package cl.prestabanco.api.services;

import cl.prestabanco.api.models.IncomesEntity;
import cl.prestabanco.api.repositories.IncomesRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomesService {
    @Autowired
    private IncomesRepository incomesRepository;
    @Autowired
    private JobsService jobsService;

    public IncomesEntity saveIncomes(Object amount, String Date, Object idJob ) {
        // Create a income object
        IncomesEntity income = new IncomesEntity();
        income.setAmountIncome(functions.transformIntegertoFloat(amount));
        income.setDateIncome(functions.transformStringtoDate(Date));
        income.setJobIncome(jobsService.findJob(functions.transformLong(idJob)));
        return incomesRepository.save(income);
    }
}
