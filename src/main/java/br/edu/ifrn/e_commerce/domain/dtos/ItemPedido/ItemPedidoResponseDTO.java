package br.edu.ifrn.e_commerce.domain.dtos.ItemPedido;

import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    private ProdutoResponseDTO produto;
    private Integer quantidade;


}
