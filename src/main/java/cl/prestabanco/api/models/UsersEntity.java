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
    private Long id_user;
    @Column(unique = true, nullable = false)
    private String rut_user;
    @Column(nullable = false)
    private String name_user;
    @Column(nullable = false)
    private String first_lastname_user;
    @Column(nullable = false)
    private String second_lastname_user;
    @Column(nullable = false)
    private String email_user;
    @Column(nullable = false)
    private String phone_user;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthday_user;
    @Column(nullable = false)
    private String status_user;
    @Column(nullable = false)
    private String type_user;
    // Foreign Key
    @ManyToOne
    @JoinColumn(name = "id_address")
    @JsonIgnore
    private AddressesEntity address_user;
}
