package web.aaaeiftm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Status;
import web.aaaeiftm.model.Pessoa;
import web.aaaeiftm.repository.helper.pessoa.PessoaQueries;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaQueries {
    
    public List<Pessoa> findByNomeContainingIgnoreCaseAndStatus(String nome, Status status);

    public Page<Pessoa> findByStatus(Status status, Pageable pageable);
    
    public List<Pessoa> findByStatus(Status status);

    public Optional<Pessoa> findByCodigoAndStatus(Long codigo, Status status);
}
