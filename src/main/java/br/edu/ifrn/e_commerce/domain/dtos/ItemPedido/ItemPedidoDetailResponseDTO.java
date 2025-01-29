package br.edu.ifrn.e_commerce.domain.dtos.ItemPedido;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDetailResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
}
