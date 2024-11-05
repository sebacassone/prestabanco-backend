package cl.prestabanco.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "deposits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idDeposit;
    @Column(nullable = false)
    private Float amountDeposit;
    @Column(nullable = false)
    private Date dateDeposit;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "idSaving")
    private SavingsEntity savingDeposit;
}
