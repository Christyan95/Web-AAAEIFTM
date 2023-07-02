package web.aaaeiftm.repository.helper.produtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.aaaeiftm.filter.ProdutosFilter;
import web.aaaeiftm.model.Produto;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.repository.pagination.PaginacaoUtil;

public class ProdutosQueriesImpl implements ProdutosQueries {

    @PersistenceContext
    private EntityManager manager;

    public Page<Produto> filtrar(ProdutosFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Produto> criteriaQuery = builder.createQuery(Produto.class);
        Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
        TypedQuery<Produto> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();
    
        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(produtoRoot.<Long>get("codigo"), filtro.getCodigo()));
        }
    
        if (filtro.getNome() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    produtoRoot.<String>get("nome"), filtro.getNome()));
        }
    
        if (filtro.getDescricao() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    produtoRoot.<String>get("descricao"), filtro.getDescricao()));
        }
    
        predicateList.add(builder.equal(produtoRoot.<Status>get("status"), Status.ATIVO));
    
        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);
    
        criteriaQuery.select(produtoRoot).where(predArray);
    
        PaginacaoUtil.prepararOrdem(produtoRoot, criteriaQuery, builder, pageable);
    
        typedQuery = manager.createQuery(criteriaQuery);
    
        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);
    
        List<Produto> produtos = typedQuery.getResultList();
    
        long totalProdutos = getTotalProdutos(filtro);
        return new PageImpl<>(produtos, pageable, totalProdutos);
    }

    private Long getTotalProdutos(ProdutosFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Produto> produtoRoot = criteriaQuery.from(Produto.class);
        List<Predicate> predicateList = new ArrayList<>();
    
        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(produtoRoot.<Long>get("codigo"), filtro.getCodigo()));
        }
    
        if (filtro.getNome() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    produtoRoot.<String>get("nome"), filtro.getNome()));
        }
    
        if (filtro.getDescricao() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    produtoRoot.<String>get("descricao"), filtro.getDescricao()));
        }
    
        predicateList.add(builder.equal(produtoRoot.<Status>get("status"), Status.ATIVO));
    
        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);
    
        criteriaQuery.select(builder.count(produtoRoot)).where(predArray);
    
        return manager.createQuery(criteriaQuery).getSingleResult();
    }
}
