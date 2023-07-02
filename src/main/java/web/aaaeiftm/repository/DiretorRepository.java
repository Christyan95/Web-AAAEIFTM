package web.aaaeiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.aaaeiftm.model.Diretor;
import web.aaaeiftm.repository.helper.diretor.DiretorQueries;

public interface DiretorRepository extends JpaRepository<Diretor, Long>, DiretorQueries {
    
    Diretor findByNomeIgnoreCase(String nome);
}
