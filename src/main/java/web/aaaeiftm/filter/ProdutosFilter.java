package web.aaaeiftm.filter;

import jakarta.transaction.Status;
import lombok.Data;

@Data
public class ProdutosFilter {
    
    private Long codigo;
    private String nome;
    private String descricao;
    private Status status;
}
