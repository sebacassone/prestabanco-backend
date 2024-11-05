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
    private Integer idDebt;
    @Column(nullable = false)
    private Float amountDebt;
    @Column(nullable = false)
    private String typeDebt;
    @Column(nullable = false)
    private String creditorDebt;
    @Column(nullable = false)
    private String stateDebt;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date expirationDateDebt;
    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private UsersEntity userDebt;
}
