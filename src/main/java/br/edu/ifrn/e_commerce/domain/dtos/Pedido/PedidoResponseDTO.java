package br.edu.ifrn.e_commerce.domain.dtos.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.edu.ifrn.e_commerce.domain.dtos.ItemPedido.ItemPedidoResponseDTO;
import br.edu.ifrn.e_commerce.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private Long clienteId;
    private List<ItemPedidoResponseDTO> itemsPedido;
}
