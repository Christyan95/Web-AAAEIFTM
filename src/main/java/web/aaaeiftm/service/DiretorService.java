package web.aaaeiftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.aaaeiftm.model.Diretor;
import web.aaaeiftm.repository.DiretorRepository;

@Service
public class DiretorService {

    @Autowired
    private DiretorRepository diretorRepository;

    @Transactional
    public void salvar(Diretor diretor) {
        diretorRepository.save(diretor);
    }

    @Transactional
    public void alterar(Diretor diretor) {
        diretorRepository.save(diretor);
    }
}
