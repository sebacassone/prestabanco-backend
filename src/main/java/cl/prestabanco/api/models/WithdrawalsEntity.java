package cl.prestabanco.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "withdrawals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idWithdrawal;
    @Column(nullable = false)
    private Float amountWithdrawal;
    @Column(nullable = false)
    private Date dateWithdrawal;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "idSaving")
    private SavingsEntity savingWithdrawal;
}
