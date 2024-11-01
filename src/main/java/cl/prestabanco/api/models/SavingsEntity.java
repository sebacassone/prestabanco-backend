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
    private Long idSaving;
    @Column(nullable = false)
    private Float balanceSaving;
    @Column(nullable = false)
    private String stateSaving;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date seniorityAccountSaving;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "idUser")
    private UsersEntity userSaving;
}
