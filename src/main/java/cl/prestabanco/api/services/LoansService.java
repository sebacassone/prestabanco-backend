package cl.prestabanco.api.services;

import cl.prestabanco.api.models.LoansEntity;
import cl.prestabanco.api.repositories.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoansService {
    @Autowired
    private LoansRepository loansRepository;

    private static Double calculateQuota(Integer amount, Float interestRate, Integer numberOfPayments) {
        double monthlyRate = interestRate / 12;
        double quota = amount * (monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments)) / (Math.pow(1 + monthlyRate, numberOfPayments) - 1);
        return quota;
    }

    public LoansEntity calculateLoan(LoansEntity loan) {
        System.out.println(loan);
        // variables are extracted
        Integer amount = loan.getAmountLoan();
        Float interestRate;
        Integer numberOfPayments = loan.getNumberOfPaymentsLoan();
        String typeLoan = loan.getTypeLoan();
        // Select the interest rate according to the type of loan
        if (typeLoan.equals("Primera Vivienda")) {
            interestRate = 0.035f;
        } else if (typeLoan.equals("Segunda Vivienda")) {
            interestRate = 0.04f;
        } else if (typeLoan.equals("Propiedades Comerciales")) {
            interestRate = 0.05f;
        } else if (typeLoan.equals("Remodalación")){
            interestRate = 0.045f;
        } else {
            interestRate = 0.00f;
        }
        // Calculate the monthly fee
        Double quoata = calculateQuota(amount, interestRate, numberOfPayments);
        System.out.println(quoata);
        System.out.println(amount);
        System.out.println(interestRate);
        System.out.println(numberOfPayments);

        // Set the values in the loan object
        loan.setInterestLoan(interestRate);
        loan.setSecureAmountLoan(amount * 0.0003f);
        loan.setAdministrationAmountLoan(amount * 0.01f);
        loan.setQuotaLoan(quoata.floatValue() + 20000 + (amount * 0.0003f));
        loan.setTotalAmountLoan(loan.getQuotaLoan() + loan.getAdministrationAmountLoan());
        return loan;
    }

    public LoansEntity findLoan(Long idLoan) {
        return loansRepository.findById(idLoan).orElse(null);
    }

    public LoansEntity saveLoan(LoansEntity loan) {
        return loansRepository.save(loan);
    }
}
