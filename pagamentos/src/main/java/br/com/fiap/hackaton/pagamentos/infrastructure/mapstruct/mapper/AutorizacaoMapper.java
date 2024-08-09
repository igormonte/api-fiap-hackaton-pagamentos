package br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper;

import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.AutorizacaoDto;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface AutorizacaoMapper {
    default AutorizacaoDto toAutorizacaoDto(Pagamento pagamento) {
        return new AutorizacaoDto(pagamento.getId().toString());
    }

}
