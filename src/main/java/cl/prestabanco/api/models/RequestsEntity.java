package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idRequest;
    @Column(nullable = false)
    private String stateRequest;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "idLean")
    @JsonIgnore
    private LoansEntity leanRequest;
    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonIgnore
    private UsersEntity userRequest;
    @OneToOne
    @JoinColumn(name = "idEvaluation")
    @JsonIgnore
    private EvaluationsEntity evaluationRequest;
}
