package br.edu.ifrn.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.e_commerce.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
