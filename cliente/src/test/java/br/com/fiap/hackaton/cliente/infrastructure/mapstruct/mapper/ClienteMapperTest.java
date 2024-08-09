package br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper;

import br.com.fiap.hackaton.cliente.application.dto.request.CriarClienteDto;
import br.com.fiap.hackaton.cliente.application.dto.response.ClienteDto;
import br.com.fiap.hackaton.cliente.domain.entity.ClienteTest;
import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntityTest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface ClienteMapperTest {
    ClienteTest toCliente(ClienteDbEntityTest clienteDbEntity);
    ClienteTest toCliente(CriarClienteDto criarClienteDto);
    ClienteDto toClienteDto(ClienteTest cliente);
    ClienteDbEntityTest toClienteDbEntity(ClienteTest cliente);
    List<ClienteTest> toClienteList(List<ClienteDbEntityTest> clienteDbEntityList);
    List<ClienteDbEntityTest> toClienteDbEntityList(List<ClienteTest> clienteList);
}
