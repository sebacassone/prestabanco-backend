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
    private Long id_withdrawal;
    @Column(nullable = false)
    private Float amount_withdrawal;
    @Column(nullable = false)
    private Date date_withdrawal;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "id_saving")
    private SavingsEntity saving_withdrawal;
}
