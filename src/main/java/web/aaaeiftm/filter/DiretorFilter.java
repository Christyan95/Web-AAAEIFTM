package web.aaaeiftm.filter;

import java.time.LocalDate;

import jakarta.transaction.Status;
import lombok.Data;

@Data
public class DiretorFilter {
    private Long codigo;
    private String nome;
    private String cpf;
    private LocalDate dataNascimentoInicial;
    private LocalDate dataNascimentoFinal;
    private Status status;
}
