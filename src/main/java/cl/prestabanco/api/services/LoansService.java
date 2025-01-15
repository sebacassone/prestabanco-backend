package cl.prestabanco.api.services;

import cl.prestabanco.api.models.LoansEntity;
import cl.prestabanco.api.repositories.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoansService {
    private final LoansRepository loansRepository;
    @Autowired
    public LoansService(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    private static Double calculateQuota(Integer amount, Float interestRate, Integer numberOfPayments) {
        double monthlyRate = interestRate / 12;
        return amount * (monthlyRate * Math.pow(1 + monthlyRate, numberOfPayments)) / (Math.pow(1 + monthlyRate, numberOfPayments) - 1);
    }

    public LoansEntity calculateLoan(LoansEntity loan) {
        // variables are extracted
        Integer amount = loan.getAmountLoan();
        Float interestRate;
        Integer numberOfPayments = loan.getNumberOfPaymentsLoan();
        String typeLoan = loan.getTypeLoan();

        if (amount == null || numberOfPayments == null || typeLoan == null) {
            return null;
        } else if (amount <= 0 || numberOfPayments <= 0) {
            return null;
        }

        // Select the interest rate according to the type of loan
        interestRate = switch (typeLoan) {
            case "Primera Vivienda" -> 0.035f;
            case "Segunda Vivienda" -> 0.04f;
            case "Propiedades Comerciales" -> 0.05f;
            case "Remodelación" -> 0.045f;
            default -> 0.00f;
        };

        if (interestRate == 0.00f) {
            return null;
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

    public LoansEntity findLoan(Integer idLoan) {
        return loansRepository.findById(idLoan).orElse(null);
    }

    public LoansEntity saveLoan(LoansEntity loan) {
        if (loan == null) {
            return null;
        }
        return loansRepository.save(loan);
    }

    /**
     * Method to calculate the maximum financing amount
     * @param typeLoan - Type of loan
     * @param amountPercentage - Amount percentage
     */
    public Boolean maximumFinancingAmount(String typeLoan, Double amountPercentage) {
        Double maximumAmountPercentage = switch (typeLoan) {
            case "Primera Vivienda" -> 0.80;
            case "Segunda Vivienda" -> 0.70;
            case "Propiedades Comerciales" -> 0.60;
            case "Remodelación" -> 0.50;
            default -> 0.0;
        };

        if (maximumAmountPercentage == 0 || amountPercentage == null || amountPercentage <= 0 || amountPercentage > 1) {
            return false;
        }
        return amountPercentage <= maximumAmountPercentage;
    }


    public LoansEntity getLoanByIdRequest(Integer idRequest) {
        return loansRepository.findLoanByIdRequest(idRequest);
    }
}
