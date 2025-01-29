package br.edu.ifrn.e_commerce.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import br.edu.ifrn.e_commerce.domain.ItemPedido;
import br.edu.ifrn.e_commerce.domain.dtos.ItemPedido.ItemPedidoResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemPedidoMapper {
    List<ItemPedidoResponseDTO> toDTOList(List<ItemPedido> itemPedidos);
}
