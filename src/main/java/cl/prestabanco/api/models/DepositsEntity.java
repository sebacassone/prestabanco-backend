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
    private Long id_deposit;
    @Column(nullable = false)
    private Float amount_deposit;
    @Column(nullable = false)
    private Date date_deposit;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "id_saving")
    private SavingsEntity saving_deposit;
}
