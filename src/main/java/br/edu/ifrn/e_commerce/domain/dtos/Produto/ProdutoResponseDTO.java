package br.edu.ifrn.e_commerce.domain.dtos.Produto;

import java.math.BigDecimal;
import java.util.List;

import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer estoque;
    private List<CategoriaResponseDTO> categorias;
}
