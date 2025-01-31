package br.edu.ifrn.e_commerce.specification;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.edu.ifrn.e_commerce.domain.Produto;

public class ProdutoSpecification {
    public static Specification<Produto> comNome(String nome) {
        return (root, query, criteriaBuilder) -> {
            if (nome == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Produto> comCategoria(Long categoriaId) {
        return (root, query, criteriaBuilder) -> {
            if (categoriaId == null) return criteriaBuilder.conjunction();
            return criteriaBuilder.equal(root.join("categorias").get("id"), categoriaId);
        };
    }

    public static Specification<Produto> comPrecoEntre(BigDecimal precoMin, BigDecimal precoMax) {
        return (root, query, criteriaBuilder) -> {
            if (precoMin == null && precoMax == null) return criteriaBuilder.conjunction();
            if (precoMin == null) return criteriaBuilder.lessThanOrEqualTo(root.get("preco"), precoMax);
            if (precoMax == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), precoMin);
            return criteriaBuilder.between(root.get("preco"), precoMin, precoMax);
        };
    }

}
