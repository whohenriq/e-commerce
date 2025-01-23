package br.edu.ifrn.e_commerce.domain.dtos.ItemPedido;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequestDTO {
    @NotNull(message = "O Id do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "A quantidade é obrigatória")
    @Min(value = 1, message = "Deve ter pelo menos 1")
    private Integer quantidade;
}
