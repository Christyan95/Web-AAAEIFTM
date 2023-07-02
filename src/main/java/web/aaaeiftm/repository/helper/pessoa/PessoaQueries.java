package web.aaaeiftm.repository.helper.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.aaaeiftm.filter.PessoaFilter;
import web.aaaeiftm.model.Pessoa;

public interface PessoaQueries {
    
    Page<Pessoa> filtrar(PessoaFilter filtro, Pageable pageable);
}
