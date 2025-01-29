package br.edu.ifrn.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifrn.e_commerce.domain.Categoria;
import br.edu.ifrn.e_commerce.domain.Produto;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Produto.ProdutoResponseDTO;
import br.edu.ifrn.e_commerce.exception.BusinessException;
import br.edu.ifrn.e_commerce.exception.ResourceNotFoundException;
import br.edu.ifrn.e_commerce.mapper.CategoriaMapper;
import br.edu.ifrn.e_commerce.mapper.ProdutoMapper;
import br.edu.ifrn.e_commerce.repository.CategoriaRepository;
import br.edu.ifrn.e_commerce.repository.ProdutoRepository;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }
    
    public CategoriaResponseDTO create(CategoriaRequestDTO categoriaRequestDTO) {
        var categoria = categoriaMapper.toEntity(categoriaRequestDTO);
        
        if (categoriaRepository.existsByNome(categoria.getNome())) {
             throw new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaRepository.save(categoria);
        return  categoriaMapper.toResponseDTO(categoria);
    }

    public Page<CategoriaResponseDTO> listAllCategorys(int page, int size){
        Pageable pageRequest = createPageRequestUsing(page, size);
        
        Page<Categoria> categoriaPage = categoriaRepository.findAll(pageRequest);
        
        Page<CategoriaResponseDTO> response = categoriaPage.map(categoriaMapper::toResponseDTO);

        return response;
    }

    public CategoriaResponseDTO getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));
        return categoriaMapper.toResponseDTO(categoria);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!categoria.getNome().equals(categoriaRequestDTO.getNome()) && categoriaRepository.existsByNome( categoriaRequestDTO.getNome()) ) {
            throw  new BusinessException("Já existe uma categoria com esse nome");
        }

        categoriaMapper.updateEntityFromDTO(categoriaRequestDTO, categoria);
        var updateCategory = categoriaRepository.save(categoria);

        return categoriaMapper.toResponseDTO(updateCategory);
    }

        public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    public ProdutoResponseDTO adicionarProdutoACategoria(Long categoriaId, Long produtoId) {

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        categoria.getProdutos().add(produto);
        produto.getCategorias().add(categoria);

        produtoRepository.save(produto);
        categoriaRepository.save(categoria);

        return produtoMapper.toResponseDTO(produto);
    }

    public ProdutoResponseDTO removerProdutoDaCategoria(Long categoriaId, Long produtoId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        categoria.getProdutos().remove(produto);
        produto.getCategorias().remove(categoria);

        produtoRepository.save(produto);
        categoriaRepository.save(categoria);

        return produtoMapper.toResponseDTO(produto);
    }

}
