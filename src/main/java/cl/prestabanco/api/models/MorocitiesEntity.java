package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "morocities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MorocitiesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer idMorocity;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateStartMorocity;
    @Column(nullable = false)
    private Float amountOverdueMorocity;

    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "idDebt")
    @JsonIgnore
    private DebtsEntity debtMorocity;
}
