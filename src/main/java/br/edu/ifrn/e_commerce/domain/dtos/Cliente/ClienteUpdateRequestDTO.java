package br.edu.ifrn.e_commerce.domain.dtos.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateRequestDTO {
    private String email;
    private String telefone;
}
