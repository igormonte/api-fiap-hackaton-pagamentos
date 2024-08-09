package br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper;


import br.com.fiap.hackaton.cartao.domain.cliente.entity.Cliente;
import br.com.fiap.hackaton.cartao.infrastructure.cliente.dto.ClienteDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface ClienteMapper {
    Cliente toCliente(ClienteDto clienteDto);
}
