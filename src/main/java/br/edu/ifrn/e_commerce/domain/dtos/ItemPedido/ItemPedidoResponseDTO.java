package br.edu.ifrn.e_commerce.domain.dtos.ItemPedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    private ItemPedidoDetailResponseDTO produto;
    private Integer quantidade;
}
