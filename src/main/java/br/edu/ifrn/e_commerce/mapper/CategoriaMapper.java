package br.edu.ifrn.e_commerce.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import br.edu.ifrn.e_commerce.domain.Categoria;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Categoria toEntity(CategoriaRequestDTO categoriaRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    void updateEntityFromDTO(CategoriaRequestDTO categoriaRequestDTO, @MappingTarget Categoria categoria);

    default Page<CategoriaResponseDTO> toDTOList(Page<Categoria> categorias) {
        return categorias.map(this::toResponseDTO);
    }
}
