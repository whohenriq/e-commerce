package br.edu.ifrn.e_commerce.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.edu.ifrn.e_commerce.domain.Pedido;
import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoRequestDTO;
import br.edu.ifrn.e_commerce.domain.dtos.Pedido.PedidoResponseDTO;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE, 
    uses =  ItemPedidoMapper.class
)
public interface PedidoMapper {
    @Mapping(target = "id", ignore = true)
    Pedido toEntity(PedidoRequestDTO pedidoRequestDTO);
    
    @Mapping(target = "clienteId", source = "cliente.id")
    PedidoResponseDTO toResponseDTO(Pedido pedido);

    List<PedidoResponseDTO> toDTOList(List<Pedido> pedidos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PedidoRequestDTO pedidoRequestDTO, @MappingTarget Pedido pedido);
}
