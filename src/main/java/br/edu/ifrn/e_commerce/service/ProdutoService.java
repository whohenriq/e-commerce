package br.edu.ifrn.e_commerce.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.edu.ifrn.e_commerce.domain.Categoria;
import br.edu.ifrn.e_commerce.domain.Produto;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import br.edu.ifrn.e_commerce.exception.ResourceNotFoundException;
import br.edu.ifrn.e_commerce.mapper.ProdutoMapper;
import br.edu.ifrn.e_commerce.repository.CategoriaRepository;
import br.edu.ifrn.e_commerce.repository.ProdutoRepository;
import br.edu.ifrn.e_commerce.specification.ProdutoSpecification;
import jakarta.validation.ValidationException;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ProdutoMapper produtoMapper;

     private List<Categoria> buscarCategoriasPorId(List<Long> categoriaIds) {
        return categoriaRepository.findAllById(categoriaIds);
    }

    public ProdutoResponseDTO create(ProdutoRequestDTO produtoRequestDTO) {

        if (produtoRequestDTO.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("O preço do produto não pode ser negativo");
        }
        
        var produto =  produtoMapper.toEntity(produtoRequestDTO);
        var categorias = buscarCategoriasPorId(produtoRequestDTO.getCategoriaIds());        
        produto.setCategorias(categorias);

        produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produto);
    }

    public Page<ProdutoResponseDTO> listAllProducts(String nome, Long categoriaId, BigDecimal precoMin, BigDecimal precoMax, int page, int size)
    {
        Specification<Produto> spec = Specification
            .where(ProdutoSpecification.comNome(nome))
            .and(ProdutoSpecification.comCategoria(categoriaId))
            .and(ProdutoSpecification.comPrecoEntre(precoMin, precoMax));

        Pageable pageRequest = PageRequest.of(page, size);
        Page<Produto> produtoPage = produtoRepository.findAll(spec, pageRequest);

        Page<ProdutoResponseDTO> response = produtoPage.map(produtoMapper::toResponseDTO);

        return response;
    }

    public ProdutoResponseDTO getById(Long id) {
        Produto produto = produtoRepository
            .findById(id)
            .orElseThrow( () -> new ResourceNotFoundException
            ("Produto não encontrado"));

        return produtoMapper.toResponseDTO(produto);
    }


    public ProdutoResponseDTO update(Long id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository
            .findById(id)
            .orElseThrow( () -> new ResourceNotFoundException
            ("Produto não encontrado"));
        
        if (produtoRequestDTO.getPreco().compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("O preço do produto não pode ser negativo");
        }

        produtoMapper.updateEntityFromDTO(produtoRequestDTO, produto);
        var updateProduct = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(updateProduct);
    }

    public ProdutoResponseDTO updateStock(Long id, Integer quantity) {
        Produto produto = produtoRepository
            .findById(id)
            .orElseThrow( () -> new ResourceNotFoundException
            ("Produto não encontrado"));

        produto.setEstoque(quantity);
        var produtoAlterado = produtoRepository.save(produto);

        return produtoMapper.toResponseDTO(produtoAlterado);
    }

    public List<ProdutoResponseDTO> buscarPorCategoria(Long id) {
        List<Produto> produtos = produtoRepository.findByCategoriasId(id);
        return produtoMapper.toDTOList(produtos);
    }

    public void delete(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }

}
