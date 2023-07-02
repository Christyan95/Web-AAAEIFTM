package web.aaaeiftm.repository.helper.produtos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.aaaeiftm.filter.ProdutosFilter;
import web.aaaeiftm.model.Produto;

public interface ProdutosQueries {
    
    Page<Produto> filtrar(ProdutosFilter filtro, Pageable pageable);
}
