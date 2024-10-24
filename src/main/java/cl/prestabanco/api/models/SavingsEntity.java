package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "savings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_saving;
    @Column(nullable = false)
    private Float balance_saving;
    @Column(nullable = false)
    private String state_saving;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date seniority_account_saving;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "id_user")
    private UsersEntity user_saving;
}
