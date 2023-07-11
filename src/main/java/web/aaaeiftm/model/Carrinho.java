package web.aaaeiftm.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "carrinho")
public class Carrinho{

    @Id
    // @SequenceGenerator(name = "gerador7", sequenceName = "carrinho_codigo_seq", allocationSize = 1)
    // @GeneratedValue(generator = "gerador7", strategy = GenerationType.SEQUENCE)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    // @OneToOne
	// @JoinColumn(name = "codigo_usuario")
    // private Usuario usuario;

    @OneToOne
	@JoinColumn(name = "codigo_pessoa")
    private Pessoa pessoa;

    @OneToOne
	@JoinColumn(name = "codigo_produto")
    private Produto produto;
}