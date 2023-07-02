package web.aaaeiftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.aaaeiftm.model.Produto;
import web.aaaeiftm.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void salvar(Produto produto) {
        produtoRepository.save(produto);
    }

    @Transactional
    public void alterar(Produto produto) {
        produtoRepository.save(produto);
    }
}
