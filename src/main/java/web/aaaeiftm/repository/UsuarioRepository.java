package web.aaaeiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.aaaeiftm.model.Usuario;
import web.aaaeiftm.repository.helper.usuario.UsuarioQueries;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioQueries {
    
    Usuario findByNomeUsuarioIgnoreCase(String nomeUsuario);
}
