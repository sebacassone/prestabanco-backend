package cl.prestabanco.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evaluations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvaluation;
    @Column(nullable = false)
    private Boolean quotaIncomeRatio;
    @Column(nullable = false)
    private Boolean customerCredit;
    @Column(nullable = false)
    private Boolean seniorityEvaluation;
    @Column(nullable = false)
    private Boolean debtIncomeRatio;
    @Column(nullable = false)
    private Boolean maximumFinancingAmount;
    @Column(nullable = false)
    private Boolean ageApplicant;
}
