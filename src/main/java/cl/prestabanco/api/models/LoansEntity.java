package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoansEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idLoan;
    @Column(nullable = false)
    private Integer amountLoan;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateConcession;
    @Column(nullable = false)
    private String typeLoan;
    @Column(nullable = false)
    private Integer numberOfPaymentsLoan;
    @Column(nullable = false)
    private Float quotaLoan;
    @Column(nullable = false)
    private String stateLoan;
    @Column(nullable = false)
    private Float interestLoan;
    @Column(nullable = false)
    private Float administrationAmountLoan;
    @Column(nullable = false)
    private Float totalAmountLoan;
    @Column(nullable = false)
    private Float secureAmountLoan;
}
