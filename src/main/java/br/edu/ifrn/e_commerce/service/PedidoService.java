package br.edu.ifrn.e_commerce.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.e_commerce.domain.Cliente;
import br.edu.ifrn.e_commerce.domain.ItemPedido;
import br.edu.ifrn.e_commerce.domain.Pedido;
import br.edu.ifrn.e_commerce.domain.Produto;
import br.edu.ifrn.e_commerce.domain.dtos.ItemPedido.ItemPedidoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoResponseDTO;
import br.edu.ifrn.e_commerce.domain.enums.StatusPedido;
import br.edu.ifrn.e_commerce.exception.ResourceNotFoundException;
import br.edu.ifrn.e_commerce.mapper.PedidoMapper;
import br.edu.ifrn.e_commerce.repository.ClienteRepository;
import br.edu.ifrn.e_commerce.repository.ItemPedidoRepository;
import br.edu.ifrn.e_commerce.repository.PedidoRepository;
import br.edu.ifrn.e_commerce.repository.ProdutoRepository;
import jakarta.validation.ValidationException;

@Service
public class PedidoService {

    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoMapper pedidoMapper;
    

    public PedidoResponseDTO create(PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        if (pedidoRequestDTO.getItensPedido().isEmpty()) {
            throw new ValidationException("O pedido deve ter pelo menos um item");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatusPedido(StatusPedido.AGUARDANDO);

        var itens = new ArrayList<ItemPedido>();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for(ItemPedidoRequestDTO itemPedidoRequestDTO : pedidoRequestDTO.getItensPedido()) {
            Produto produto = produtoRepository.findById(itemPedidoRequestDTO.getProdutoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));

            if (produto.getEstoque() < itemPedidoRequestDTO.getQuantidade()) {
                throw new ValidationException("Estoque insuficiente para o produto: " + produto.getNome());
            }
            
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemPedidoRequestDTO.getQuantidade());
            itemPedido.setPedido(pedido);

            itens.add(itemPedido);

            valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(itemPedido.getQuantidade())));

            produto.setEstoque(produto.getEstoque() - itemPedido.getQuantidade());
        }

        pedido.setItens(itens);
        pedido.setValorTotal(valorTotal);
        
        Pedido savedPedido = pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itens);
        return pedidoMapper.toResponseDTO(savedPedido);
    }

    public Page<PedidoResponseDTO> listAllPedidos(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Pedido> pedidoPage = pedidoRepository.findAll(pageRequest);

        Page<PedidoResponseDTO> response = pedidoPage.map(pedidoMapper::toResponseDTO); 
        return response;
    }

    public PedidoResponseDTO getOrderById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return pedidoMapper.toResponseDTO(pedido);
    }

    public List<PedidoResponseDTO> listOrdersByCustomer(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        List<Pedido> pedidos = pedidoRepository.findByClienteId(cliente.getId());

        return pedidos.stream().map(pedidoMapper::toResponseDTO).collect(Collectors.toList());

    }

    public PedidoResponseDTO update(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        pedido.setStatusPedido(status);
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(updatedPedido);
    }
}
