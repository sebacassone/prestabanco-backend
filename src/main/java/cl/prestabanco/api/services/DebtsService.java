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
    @Autowired
    public DebtsService(DebtsRepository debtsRepository) {
        this.debtsRepository = debtsRepository;
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
}
