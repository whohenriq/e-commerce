package br.edu.ifrn.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.e_commerce.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCPF(String cpf);
    boolean existsByEmail(String email);
    boolean existsByIdAndOrdersIsNotEmpty(Long id);
}
