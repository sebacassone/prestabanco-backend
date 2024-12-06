package cl.prestabanco.api.services;

import cl.prestabanco.api.DTOs.DebtsWithMorosityDTO;
import cl.prestabanco.api.repositories.DebtsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DebtsService {
    private final DebtsRepository debtsRepository;
    private final IncomesService incomesService;

    @Autowired
    public DebtsService(DebtsRepository debtsRepository, IncomesService incomesService) {
        this.debtsRepository = debtsRepository;
        this.incomesService = incomesService;
    }

    public Boolean hasUnpaidDebtsOrMorocities(Integer idUser) {
        if (idUser == null || idUser <= 0) {
            return false;
        }
        // Variable to store the result
        Boolean hasUnpaidDebtsOrMorocities = false;

        // Recover debts without delinquency
        List<DebtsWithMorosityDTO> debtsWithoutMorosity = debtsRepository.findUnpaidDebtsWithoutMorosity(idUser);
        if (debtsWithoutMorosity == null) {
            debtsWithoutMorosity = new ArrayList<>();
        }

        // Recover delinquent debts
        List<DebtsWithMorosityDTO> debtsWithMorosity = debtsRepository.findUnpaidDebtsWithMorosity(idUser);
        if (debtsWithMorosity == null) {
            debtsWithMorosity = new ArrayList<>();
        }

        // Combines both lists
        debtsWithoutMorosity.addAll(debtsWithMorosity);

        if (!debtsWithoutMorosity.isEmpty()) {
            hasUnpaidDebtsOrMorocities = true;
        }

        return hasUnpaidDebtsOrMorocities;
    }

    public Boolean relationDebtsIncome (Integer idUser, Double quota){
        if (idUser <= 0 || quota <= 0){
            return null;
        }
        // Get debts from the user
        Float debtTotal = debtsRepository.getSumAmountDebt(idUser);
        Double avarageSalary = incomesService.avarageSalary(idUser);
        if (debtTotal == null) {
            debtTotal = 0f;
        }
        if (avarageSalary == 0) {
            return false;
        }
        return ((debtTotal + quota) / avarageSalary) <= 0.5f;
    }
}
