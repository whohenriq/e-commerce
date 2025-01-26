package br.edu.ifrn.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import br.edu.ifrn.e_commerce.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/categorias")
@Tag(name = "Categorias")
@EnableCaching
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(description = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> createCategory(@RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.create(categoriaRequestDTO));
    }

    @Operation(description = "Listar categorias")
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> listAllCategorys(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            Pageable pageable) {
        return ResponseEntity.ok(categoriaService.listAllCategorys(pageable, nome, descricao));
    }

    @Operation(description = "Atualizar uma nova categoria")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCaregory(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDto) {
        return ResponseEntity.ok(categoriaService.update(id, categoriaDto));
    }

    @Operation(description = "Adicionar um produto a uma categoria")
    @PostMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> addProductToCategory(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.adicionarProdutoACategoria(categoriaId, produtoId));
    }

    @Operation(description = "Remover um produto de uma categoria")
    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    public ResponseEntity<ProdutoResponseDTO> removeProductToCategory(@PathVariable Long categoriaId, @PathVariable Long produtoId) {
        return ResponseEntity.ok(categoriaService.removerProdutoDaCategoria(categoriaId, produtoId));
    }

    @Operation(description = "Deletar uma categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().build();
    }

}
