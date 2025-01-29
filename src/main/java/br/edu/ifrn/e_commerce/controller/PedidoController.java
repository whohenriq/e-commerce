package br.edu.ifrn.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoResponseDTO;
import br.edu.ifrn.e_commerce.domain.enums.StatusPedido;
import br.edu.ifrn.e_commerce.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @Operation(description = "Adicionar um novo pedido")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> create(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
        PedidoResponseDTO pedido = pedidoService.create(pedidoRequestDTO);
        return ResponseEntity.ok(pedido);
    }

    @Operation(description = "Listar pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listAllPedidos(
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(pedidoService.listAllPedidos(page, size).getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> getOrderById(@PathVariable Long id) {
        PedidoResponseDTO pedido = pedidoService.getOrderById(id);
        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<PedidoResponseDTO>> listOrdersByCustomer(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.listOrdersByCustomer(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> updateStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        PedidoResponseDTO pedido = pedidoService.update(id, status);
        return ResponseEntity.ok(pedido);
    }
}
