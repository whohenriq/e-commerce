package br.edu.ifrn.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.e_commerce.domain.Cliente;
import br.edu.ifrn.e_commerce.domain.dtos.Cliente.ClienteRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Cliente.ClienteResponseDTO;
import br.edu.ifrn.e_commerce.exception.ResourceNotFoundException;
import br.edu.ifrn.e_commerce.mapper.ClienteMapper;
import br.edu.ifrn.e_commerce.repository.ClienteRepository;
import jakarta.validation.ValidationException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private ClienteMapper clienteMapper;

    public Cliente throwIfCustomerNotExists(Long id) {
        return clienteRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cliente com esse ID não encontrado"));
    }

    public void validateCpf(String cpf) {
        if (clienteRepository.existsByCpf(cpf)) {
            throw new ValidationException("CPF já cadastrado");
        }
    }

    public void validateEmail(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new ValidationException("Email já cadastrado");
        }
    }

    public ClienteResponseDTO create(ClienteRequestDTO clienteRequestDTO) {
        validateCpf(clienteRequestDTO.getCpf());
        validateEmail(clienteRequestDTO.getEmail());
        
        var cliente =  clienteMapper.toEntity(clienteRequestDTO);

        clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    public Page<ClienteResponseDTO> listAllCustomers(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Cliente> clientePage = clienteRepository.findAll(pageRequest);

        Page<ClienteResponseDTO> response = clientePage.map(clienteMapper::toResponseDTO); 
        return response;
    }

    public ClienteResponseDTO getById(Long id) {
        Cliente cliente = throwIfCustomerNotExists(id);
        return clienteMapper.toResponseDTO(cliente);
    }

    // public List<PedidoResponseDTO> getListOrdersToCustomer(Long id) {
    // 
    //     Cliente cliente = throwIfCustomerNotExists(id);
    //     // return pedidoMapper.toDTOList(cliente.getPedidos());
    // }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = throwIfCustomerNotExists(id);

        if (!cliente.getCpf().equals(clienteRequestDTO.getCpf())) {
            validateCpf(clienteRequestDTO.getCpf());
        }

        if (!cliente.getEmail().equals(clienteRequestDTO.getEmail())) {
            validateEmail(clienteRequestDTO.getEmail());
        }

        clienteMapper.updateEntityFromDTO(clienteRequestDTO, cliente);

        var updateCustomer = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(updateCustomer);
    }

    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
        clienteRepository.deleteById(id);
    }
}
