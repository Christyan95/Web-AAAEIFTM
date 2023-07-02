package web.aaaeiftm.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 2L;
	
	@Id
	@SequenceGenerator(name="gerador2", sequenceName="pessoa_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador2", strategy=GenerationType.SEQUENCE)

	private Long codigo;

    @NotBlank(message = "O nome da pessoa é obrigatório")
	private String nome;

    @NotBlank(message = "O CPF da pessoa é obrigatório")
	private String cpf;

	@Column(name = "data_nascimento")
	@NotNull(message = "A data de nascimento da pessoa é obrigatória")
	private LocalDate dataNascimento;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ATIVO;
}