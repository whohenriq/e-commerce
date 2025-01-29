package br.edu.ifrn.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.e_commerce.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
    boolean existsByClienteId(Long clientId);
    Endereco findByClienteId(Long clientId);
    Optional<Endereco> findByIdAndClienteId(Long id, Long clientId); 
    
}
