package cl.prestabanco.api.services;

import cl.prestabanco.api.models.IncomesEntity;
import cl.prestabanco.api.repositories.AddressRepository;
import cl.prestabanco.api.repositories.IncomesRepository;
import cl.prestabanco.api.utils.functions.functions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomesService {
    private final IncomesRepository incomesRepository;
    private final JobsService jobsService;

    @Autowired
    public IncomesService(IncomesRepository incomesRepository, JobsService jobsService) {
        this.incomesRepository = incomesRepository;
        this.jobsService = jobsService;
    }

    /**
     * Save a income in the database
     * @param amount - Amount of the income
     * @param Date - Date of the income
     * @param idJob - Id of the job
     * @return
     */
    public IncomesEntity saveIncomes(Object amount, String Date, Integer idJob ) {
        // Create a income object
        IncomesEntity income = new IncomesEntity();
        income.setAmountIncome(functions.transformIntegertoFloat(amount));
        income.setDateIncome(functions.transformStringtoDate(Date));
        income.setJobIncome(jobsService.findJob(idJob));
        return incomesRepository.save(income);
    }

    /**
     * Calculate the average salary of a user
     * @param idUser - Id of the user
     * @return
     */
    public Float avarageSalary (Integer idUser) {
        List<IncomesEntity> incomes = incomesRepository.findByJobIncomeUserJobIdUser(idUser);
        if(incomes.isEmpty()) {
            return 0f;
        }

        Float sum = 0f;
        for (IncomesEntity income : incomes) {
            sum += income.getAmountIncome();
        }
        return sum / incomes.size();
    }
}
