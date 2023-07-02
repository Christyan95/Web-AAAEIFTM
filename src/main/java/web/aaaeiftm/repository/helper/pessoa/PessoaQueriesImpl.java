package web.aaaeiftm.repository.helper.pessoa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import web.aaaeiftm.filter.PessoaFilter;
import web.aaaeiftm.model.Pessoa;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.repository.pagination.PaginacaoUtil;

public class PessoaQueriesImpl implements PessoaQueries {
    
    @PersistenceContext
    private EntityManager manager;
    private static final String DATA_NASCIMENTO = "dataNascimento";

    @Override
    public Page<Pessoa> filtrar(PessoaFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = builder.createQuery(Pessoa.class);
        Root<Pessoa> p = criteriaQuery.from(Pessoa.class);
        TypedQuery<Pessoa> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(p.<Long>get("codigo"), filtro.getCodigo()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(p.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(
                    builder.lower(p.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }

        if (filtro.getDataNascimentoInicial() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    p.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoInicial()));
        }

        if (filtro.getDataNascimentoFinal() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    p.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoFinal()));
        }

        predicateList.add(builder.equal(p.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(p).where(predArray);

        PaginacaoUtil.prepararOrdem(p, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);

        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Pessoa> pessoas = typedQuery.getResultList();
        
        long totalPessoas = getTotalPessoas(filtro);
        return new PageImpl<>(pessoas, pageable, totalPessoas); 
    }

    private Long getTotalPessoas(PessoaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Pessoa> p = criteriaQuery.from(Pessoa.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(p.<Long>get("codigo"), filtro.getCodigo()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(p.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(
                    builder.lower(p.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }

        if (filtro.getDataNascimentoInicial() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    p.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoInicial()));
        }

        if (filtro.getDataNascimentoFinal() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    p.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoFinal()));
        }

        predicateList.add(builder.equal(p.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(p)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }
}
