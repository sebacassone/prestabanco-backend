package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "debts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebtsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_debt;
    @Column(nullable = false)
    private Float amount_debt;
    @Column(nullable = false)
    private String type_debt;
    @Column(nullable = false)
    private String creditor_debt;
    @Column(nullable = false)
    private String state_debt;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expiration_date_debt;
    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UsersEntity user_debt;
}
