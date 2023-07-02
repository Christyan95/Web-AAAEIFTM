package web.aaaeiftm.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 5L;
	
	@Id
	@SequenceGenerator(name="gerador5", sequenceName="produto_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador5", strategy = GenerationType.SEQUENCE)
	private Long codigo;

	@NotBlank(message = "O nome do produto é obrigatório")
	private String nome;

	@NotBlank(message = "A descrição do produto é obrigatório")
	private String descricao;

	@Enumerated(EnumType.STRING)
	private Status status = Status.ATIVO;
}
