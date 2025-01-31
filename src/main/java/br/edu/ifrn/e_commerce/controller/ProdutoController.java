package br.edu.ifrn.e_commerce.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import br.edu.ifrn.e_commerce.service.ProdutoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produtos")
@Tag(name = "Produtos")
@EnableCaching
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> create(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO createdProduto = produtoService.create(produtoRequestDTO);
        return ResponseEntity.ok(createdProduto);
    }

    @GetMapping
    @Cacheable("produtosCache")
    public ResponseEntity<List<ProdutoResponseDTO>> listAllProducts(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) Long categoria,
        @RequestParam(required = false) BigDecimal precoMin,
        @RequestParam(required = false) BigDecimal precoMax,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProdutoResponseDTO> produtos = produtoService.listAllProducts(nome, categoria, precoMin, precoMax,  page, size);
        return ResponseEntity.ok(produtos.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> getById(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.getById(id);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> update(
        @PathVariable Long id,
        @RequestBody ProdutoRequestDTO produtoRequestDTO
    ) {
        ProdutoResponseDTO updatedProduto = produtoService.update(id, produtoRequestDTO);
        return ResponseEntity.ok(updatedProduto);
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<ProdutoResponseDTO> updateStock(
        @PathVariable Long id,
        @RequestParam Integer quantidade
    ) {
        ProdutoResponseDTO updatedProduto = produtoService.updateStock(id, quantidade);
        return ResponseEntity.ok(updatedProduto);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(@PathVariable Long id) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorCategoria(id);
        return ResponseEntity.ok(produtos);
    }

    // Endpoint para deletar um produto pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}  
