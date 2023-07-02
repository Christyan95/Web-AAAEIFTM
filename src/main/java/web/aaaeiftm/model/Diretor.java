package web.aaaeiftm.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "diretor")
public class Diretor implements Serializable {

	private static final long serialVersionUID = 4L; // gere um outro valor

	@Id
	@SequenceGenerator(name="gerador4", sequenceName="diretor_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador4", strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotBlank(message = "O nome do diretor é obrigatório")
	private String nome;

    @NotBlank(message = "O CPF do diretor é obrigatório")
	private String cpf;

    @Column(name = "data_nascimento")
	@NotNull(message = "A data de nascimento do diretor é obrigatória")
	private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
	private Status status = Status.ATIVO;

	@ManyToMany
	@JoinTable(name = "diretor_area", joinColumns = @JoinColumn(name = "codigo_diretor"), inverseJoinColumns = @JoinColumn(name = "codigo_area"))
	@Size(min = 1, message = "O diretor deve ter ao menos uma area no sistema")
	private List<Area> areas = new ArrayList<>();
}