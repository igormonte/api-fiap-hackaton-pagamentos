package br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper;

import br.com.fiap.hackaton.cliente.application.dto.request.CriarClienteDto;
import br.com.fiap.hackaton.cliente.application.dto.response.ClienteDto;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface ClienteMapper {
    Cliente toCliente(ClienteDbEntity clienteDbEntity);
    Cliente toCliente(CriarClienteDto criarClienteDto);
    ClienteDto toClienteDto(Cliente cliente);
    ClienteDbEntity toClienteDbEntity(Cliente cliente);
    List<Cliente> toClienteList(List<ClienteDbEntity> clienteDbEntityList);
    List<ClienteDbEntity> toClienteDbEntityList(List<Cliente> clienteList);
}
