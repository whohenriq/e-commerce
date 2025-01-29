package br.edu.ifrn.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifrn.e_commerce.domain.Cliente;
import br.edu.ifrn.e_commerce.domain.Endereco;
import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoResponseDTO;
import br.edu.ifrn.e_commerce.exception.ResourceNotFoundException;
import br.edu.ifrn.e_commerce.mapper.EnderecoMapper;
import br.edu.ifrn.e_commerce.repository.ClienteRepository;
import br.edu.ifrn.e_commerce.repository.EnderecoRepository;
import jakarta.validation.ValidationException;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    public EnderecoResponseDTO  create(Long clienteId, EnderecoRequestDTO enderecoRequestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + clienteId + " não encontrado"));

        if (enderecoRepository.existsByClienteId(clienteId)) {
            throw new ValidationException("Cliente já possui um endereço cadastrado");
        }

        var endereco = enderecoMapper.toEntity(enderecoRequestDTO);
        endereco.setCliente(cliente);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return enderecoMapper.toResponseDTO(enderecoSalvo);
    }

    public EnderecoResponseDTO listAddressesByClient(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com ID " + clienteId + " não encontrado"));

        Endereco endereco = enderecoRepository.findByClienteId(cliente.getId());
      
        return enderecoMapper.toResponseDTO(endereco);
    }

    public EnderecoResponseDTO update(Long clienteId, Long enderecoId, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço com ID " + enderecoId + " não encontrado"));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException("O endereço não pertence ao cliente informado");
        }

        enderecoMapper.updateEntityFromDTO(enderecoRequestDTO, endereco);
        Endereco enderecoAtualizado = enderecoRepository.save(endereco);
        return enderecoMapper.toResponseDTO(enderecoAtualizado);
    }

    public void delete(Long clienteId, Long enderecoId) {
        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço com ID " + enderecoId + " não encontrado"));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new IllegalArgumentException("O endereço não pertence ao cliente informado");
        }

        enderecoRepository.delete(endereco);
    }
}
