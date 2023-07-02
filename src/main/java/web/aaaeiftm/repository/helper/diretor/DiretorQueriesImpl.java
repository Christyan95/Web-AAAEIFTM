package web.aaaeiftm.repository.helper.diretor;

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
import web.aaaeiftm.filter.DiretorFilter;
import web.aaaeiftm.model.Diretor;
import web.aaaeiftm.model.Status;
import web.aaaeiftm.repository.pagination.PaginacaoUtil;

public class DiretorQueriesImpl implements DiretorQueries {
    
    @PersistenceContext
    private EntityManager manager;
    private static final String DATA_NASCIMENTO = "dataNascimento";

    @Override
    public Page<Diretor> filtrar(DiretorFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Diretor> criteriaQuery = builder.createQuery(Diretor.class);
        Root<Diretor> d = criteriaQuery.from(Diretor.class);
        TypedQuery<Diretor> typedQuery;
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(d.<Long>get("codigo"), filtro.getCodigo()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(d.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(
                    builder.lower(d.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }

        if (filtro.getDataNascimentoInicial() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    d.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoInicial()));
        }

        if (filtro.getDataNascimentoFinal() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    d.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoFinal()));
        }

        predicateList.add(builder.equal(d.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(d).where(predArray);

        PaginacaoUtil.prepararOrdem(d, criteriaQuery, builder, pageable);

        typedQuery = manager.createQuery(criteriaQuery);

        PaginacaoUtil.prepararIntervalo(typedQuery, pageable);

        List<Diretor> diretores = typedQuery.getResultList();
        
        long totalDiretores = getTotalDiretores(filtro);
        return new PageImpl<>(diretores, pageable, totalDiretores); 
    }

    private Long getTotalDiretores(DiretorFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Diretor> d = criteriaQuery.from(Diretor.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (filtro.getCodigo() != null) {
            predicateList.add(builder.equal(d.<Long>get("codigo"), filtro.getCodigo()));
        }

        if (StringUtils.hasText(filtro.getNome())) {
            predicateList.add(builder.like(
                    builder.lower(d.<String>get("nome")),
                    "%" + filtro.getNome().toLowerCase() + "%"));
        }

        if (StringUtils.hasText(filtro.getCpf())) {
            predicateList.add(builder.like(
                    builder.lower(d.<String>get("cpf")),
                    "%" + filtro.getCpf().toLowerCase() + "%"));
        }

        if (filtro.getDataNascimentoInicial() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(
                    d.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoInicial()));
        }

        if (filtro.getDataNascimentoFinal() != null) {
            predicateList.add(builder.lessThanOrEqualTo(
                    d.<LocalDate>get(DATA_NASCIMENTO), filtro.getDataNascimentoFinal()));
        }

        predicateList.add(builder.equal(d.<Status>get("status"), Status.ATIVO));

        Predicate[] predArray = new Predicate[predicateList.size()];
        predicateList.toArray(predArray);

        criteriaQuery.select(builder.count(d)).where(predArray);

        return manager.createQuery(criteriaQuery).getSingleResult();

    }
}
