package cl.prestabanco.api.DTOs;

import cl.prestabanco.api.models.EvaluationsEntity;
import cl.prestabanco.api.models.LoansEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsWithTypeLoan {
    private Integer idRequest;
    private String stateRequest;
    private String typeLoan;
    private String[] documentsRequired;
    private EvaluationsEntity evaluation;
    private LoansEntity leanRequest;
}
