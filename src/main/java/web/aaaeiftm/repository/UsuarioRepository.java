package web.aaaeiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.aaaeiftm.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByNomeUsuarioIgnoreCase(String nomeUsuario);
}
