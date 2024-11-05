package cl.prestabanco.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saving_capacity_evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingCapacityEvaluationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSavingCapacityEvaluation;
    @Column(nullable = false)
    private Boolean minimumBalanceRequired;
    @Column(nullable = false)
    private Boolean consistentSavingsHistory;
    @Column(nullable = false)
    private Boolean periodicDeposits;
    @Column(nullable = false)
    private Boolean balanceYearsServiceRatio;
    @Column(nullable = false)
    private Boolean recentWithdrawals;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "idEvaluation")
    private EvaluationsEntity evaluationSavingCapacity;
}
