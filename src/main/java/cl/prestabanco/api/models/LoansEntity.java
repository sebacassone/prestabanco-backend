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
    private Long id_loan;
    @Column(nullable = false)
    private Float amount_loan;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date_concession;
    @Column(nullable = false)
    private String type_loan;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiration_date_loan;
    @Column(nullable = false)
    private Float quota_loan;
    @Column(nullable = false)
    private String state_loan;
    @Column(nullable = false)
    private Float interest_loan;
    @Column(nullable = false)
    private Float administration_amount_loan;
    @Column(nullable = false)
    private Float total_amount_loan;
    @Column(nullable = false)
    private Float secure_amount_loan;
}
