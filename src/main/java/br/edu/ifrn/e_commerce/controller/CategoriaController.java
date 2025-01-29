package br.edu.ifrn.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import br.edu.ifrn.e_commerce.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
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
    public ResponseEntity<List<CategoriaResponseDTO>> listAllCategorys(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
            ) {


        return ResponseEntity.ok(categoriaService.listAllCategorys(page, size).getContent());
    }

    @Operation(description = "Buscar categoria por Id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.getById(id));
    }

    @Operation(description = "Atualizar uma nova categoria")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> updateCaregory(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.update(id, categoriaDTO));
    }

    @Operation(description = "Deletar uma categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.ok().build();
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
}
