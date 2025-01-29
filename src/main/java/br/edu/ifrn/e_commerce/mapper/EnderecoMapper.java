package br.edu.ifrn.e_commerce.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.edu.ifrn.e_commerce.domain.Endereco;
import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Endereco.EnderecoResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnderecoMapper {
    EnderecoResponseDTO toResponseDTO(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Endereco toEntity(EnderecoRequestDTO enderecoRequestDTO);

    List<EnderecoResponseDTO> toDTOList(List<Endereco> enderecos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(EnderecoRequestDTO enderecoRequestDTO, @MappingTarget Endereco endereco);
}
