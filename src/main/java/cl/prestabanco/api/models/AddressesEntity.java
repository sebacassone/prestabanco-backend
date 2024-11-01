package cl.prestabanco.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long idAddress;
    @Column(nullable = false)
    private String streetAddress;
    @Column(nullable = false)
    private Integer numberAddress;
    @Column(nullable = false)
    private String communeAddress;
    @Column(nullable = false)
    private String regionAddress;
    @Column(nullable = false)
    private String countryAddress;

}
