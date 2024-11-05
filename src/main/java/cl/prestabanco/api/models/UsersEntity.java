package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer idUser;
    @Column(unique = true, nullable = false)
    private String rutUser;
    @Column(nullable = false)
    private String nameUser;
    @Column(nullable = false)
    private String firstLastnameUser;
    @Column(nullable = false)
    private String secondLastnameUser;
    @Column(nullable = false)
    private String emailUser;
    @Column(nullable = false)
    private String phoneUser;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthdayUser;
    @Column(nullable = false)
    private String statusUser;
    @Column(nullable = false)
    private String passwordUser;
    @Column(nullable = false)
    private String typeUser;
    // Foreign Key
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAddress")
    @JsonIgnore
    private AddressesEntity addressUser;
}
