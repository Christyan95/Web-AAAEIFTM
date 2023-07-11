package web.aaaeiftm.repository.helper.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.aaaeiftm.filter.UsuarioFilter;
import web.aaaeiftm.model.Usuario;

public interface UsuarioQueries {
    
    Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);
}
