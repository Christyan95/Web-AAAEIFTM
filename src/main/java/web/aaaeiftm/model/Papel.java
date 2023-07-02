package web.aaaeiftm.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "papel")
public class Papel implements Serializable {

	private static final long serialVersionUID = 3L; // gere um outro valor

	@Id
	@SequenceGenerator(name="gerador3", sequenceName="papel_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador3", strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotBlank(message = "O nome do papel é obrigatório")
	private String nome;
}