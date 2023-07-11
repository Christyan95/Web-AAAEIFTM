package web.aaaeiftm.filter;

import java.time.LocalDate;

import lombok.Data;
import web.aaaeiftm.model.Status;

@Data
public class DiretorFilter {
    private Long codigo;
    private String nome;
    private String cpf;
    private LocalDate dataNascimentoInicial;
    private LocalDate dataNascimentoFinal;
    private Status status;
}
