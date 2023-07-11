package web.aaaeiftm.filter;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioFilter {
    
    private Long codigo;
	private String nome;
	private String nomeUsuario;
	private String email;
	private String cpf;
	private LocalDate dataNascimentoInicial;
    private LocalDate dataNascimentoFinal;
	private String telefone;
}
