package br.edu.ifrn.e_commerce.domain.dtos.Categoria;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {
    @NotBlank(message = "Nome da categoria é obrigatório.")
    private String nome;

    private String status;
}
