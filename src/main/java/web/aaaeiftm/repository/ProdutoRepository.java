package web.aaaeiftm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.aaaeiftm.model.Produto;
import web.aaaeiftm.repository.helper.produtos.ProdutosQueries;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutosQueries {

}
