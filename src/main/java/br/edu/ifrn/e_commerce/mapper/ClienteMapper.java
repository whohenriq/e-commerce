package br.edu.ifrn.e_commerce.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import br.edu.ifrn.e_commerce.domain.Cliente;
import br.edu.ifrn.e_commerce.domain.dtos.Cliente.ClienteRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Cliente.ClienteResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClienteMapper {
    ClienteResponseDTO toResponseDTO(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Cliente toEntity(ClienteRequestDTO clienteRequestDTO);

    List<ClienteResponseDTO> toDTOList(List<Cliente> clientes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pedidos", ignore = true)
    @Mapping(target = "endereco", ignore = true)
    Cliente updateEntityFromDTO(ClienteRequestDTO clienteRequestDTO, @MappingTarget Cliente cliente);

    default Page<ClienteResponseDTO> toDTOPage(Page<Cliente> clientes) {
        return clientes.map(this::toResponseDTO);
    }
}
