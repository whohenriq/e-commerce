package br.edu.ifrn.e_commerce.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.edu.ifrn.e_commerce.domain.Categoria;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Categoria.CategoriaResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoriaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    Categoria toEntity(CategoriaRequestDTO categoriaRequestDTO);

    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    List<CategoriaResponseDTO> toDTOList(List<Categoria> categoias);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtos", ignore = true)
    void updateEntityFromDTO(CategoriaRequestDTO categoriaRequestDTO, @MappingTarget Categoria categoria);
}
