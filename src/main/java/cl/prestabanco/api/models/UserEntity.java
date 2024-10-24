package cl.prestabanco.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id_user;
    @Column(unique = true, nullable = false)
    private String document_user;
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
    // Forign Key
    @ManyToOne
    @JoinColumn(name = "id_address")
    private AddressEntity address_user;
}
