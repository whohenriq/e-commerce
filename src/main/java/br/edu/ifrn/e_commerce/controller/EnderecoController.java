package br.edu.ifrn.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoResponseDTO;
import br.edu.ifrn.e_commerce.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/clientes/{clienteId}/enderecos")
@Tag(name = "Endereco")
public class EnderecoController {
        @Autowired
    private EnderecoService enderecoService;

    @Operation(description = "Criar um novo endereço para o cliente")
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> createAddress(
            @PathVariable Long clienteId, 
            @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.ok(enderecoService.create(clienteId, enderecoRequestDTO));
    }

    @Operation(description = "Listar endereços de um cliente")
    @GetMapping
    public ResponseEntity<EnderecoResponseDTO> listAllAddress(@RequestParam Long clienteId) {
        return ResponseEntity.ok(enderecoService.listAddressesByClient(clienteId));
    }

    @Operation(description = "Atualizar um endereço de um cliente")
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> updateAddress(
            @PathVariable Long clienteId, 
            @PathVariable Long id, 
            @RequestBody EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.ok(enderecoService.update(clienteId, id, enderecoRequestDTO));
    }

    @Operation(description = "Deletar um endereço de um cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long clienteId, @PathVariable Long id) {
        enderecoService.delete(clienteId, id);
        return ResponseEntity.ok().build();
    }
}
