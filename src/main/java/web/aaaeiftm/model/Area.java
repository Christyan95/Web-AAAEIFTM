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
@Table(name = "area")
public class Area implements Serializable {

    private static final long serialVersionUID = 6L; // gere um outro valor

    @Id
    @SequenceGenerator(name = "gerador6", sequenceName = "area_codigo_seq", allocationSize = 1)
    @GeneratedValue(generator = "gerador6", strategy = GenerationType.SEQUENCE)
    private Long codigo;

    @NotBlank(message = "O nome da area é obrigatório")
    private String nome;
}
