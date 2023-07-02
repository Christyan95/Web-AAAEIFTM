package web.aaaeiftm.repository.helper.diretor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.aaaeiftm.filter.DiretorFilter;
import web.aaaeiftm.model.Diretor;

public interface DiretorQueries {
    
    Page<Diretor> filtrar(DiretorFilter filtro, Pageable pageable);
}
