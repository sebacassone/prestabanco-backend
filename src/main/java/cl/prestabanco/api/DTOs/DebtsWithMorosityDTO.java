package cl.prestabanco.api.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebtsWithMorosityDTO {
    private Integer idUser;
    private String rutUser;
    private String emailUser;
    private Integer idDebt;
    private Double amountDebt;
    private String stateDebt;
    private Double morosityValue;
}
