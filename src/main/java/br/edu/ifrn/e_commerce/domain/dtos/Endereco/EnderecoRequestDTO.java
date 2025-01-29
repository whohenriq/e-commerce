package br.edu.ifrn.e_commerce.domain.dtos.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDTO {
    @NotBlank(message = "Rua é obrigatória")
    private String rua;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    
    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    
    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    
    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP deve estar no formato 12345-678")
    private String cep;
}
