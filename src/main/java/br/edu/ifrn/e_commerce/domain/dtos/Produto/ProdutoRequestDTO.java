package br.edu.ifrn.e_commerce.domain.dtos.Produto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {
    @NotBlank(message = "O nome do produto é obrigatório")
    @Size(max = 255, min = 1, message = "O nome do produto deve ter entre 1 e 255 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço do produto deve ser maior do que 0")
    private BigDecimal preco;

    @NotNull(message = "A quantidade do produto no estoque é obrigatória")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque;

    @NotEmpty(message = "A lista de categorias é obrigatória")
    private List<Long> categoriaIds;
}
