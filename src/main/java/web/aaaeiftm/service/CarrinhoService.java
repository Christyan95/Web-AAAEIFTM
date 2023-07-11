package web.aaaeiftm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.aaaeiftm.model.Carrinho;
import web.aaaeiftm.model.Produto;
import web.aaaeiftm.repository.CarrinhoRepository;
import web.aaaeiftm.repository.ProdutoRepository;

@Service
public class CarrinhoService {
    
    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void salvar(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
        Produto produto = carrinho.getProduto();
        produtoRepository.save(produto);
    }
}
