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
    private Long id_address;
    @Column(nullable = false)
    private String street_address;
    @Column(nullable = false)
    private Integer number_address;
    @Column(nullable = false)
    private String commune_address;
    @Column(nullable = false)
    private String region_address;
    @Column(nullable = false)
    private String country_address;

}
