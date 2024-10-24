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
    private Long id_request;
    @Column(nullable = false)
    private String type_request;
    @Column(nullable = false)
    private String state_request;

    // Foreign Key
    @OneToOne
    @JoinColumn(name = "id_lean")
    @JsonIgnore
    private LoansEntity lean_request;
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private UsersEntity user_request;
    @ManyToMany(mappedBy = "requests_document")
    @JsonIgnore
    private List<DocumentsEntity> documents_request;

}
