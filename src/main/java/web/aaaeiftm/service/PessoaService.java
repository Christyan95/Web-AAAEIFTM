package web.aaaeiftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.aaaeiftm.model.Pessoa;
import web.aaaeiftm.repository.PessoaRepository;

@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public void salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    @Transactional
    public void alterar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }
}
