package br.edu.ifrn.e_commerce.domain.dtos.Pedido;

import java.util.List;

import br.edu.ifrn.e_commerce.domain.dtos.ItemPedido.ItemPedidoRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    @NotNull(message = "O id do cliente é obrigatório")
    private Long clienteId;

    @NotEmpty(message = "A lista de itens do pedido deve ter pelo menos 1 item")
    @Valid
    private List<ItemPedidoRequestDTO> itensPedido;

}
