package br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper;


import br.com.fiap.hackaton.cartao.application.dto.response.CartaoDto;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.CVV;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.DataValidade;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.NumeroCartao;
import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface CartaoMapper {

    Cartao toCartao(CartaoDbEntity cartaoDbEntity);

    List<Cartao> toCartaoList(List<CartaoDbEntity> cartaoDbEntity);

    CartaoDto toCartaoDto(Cartao cartao);

    default NumeroCartao toNumeroCartao(String numeroCartao) {
        return new NumeroCartao(numeroCartao);
    }

    default CVV toCVV(String cvv){
        return new CVV(cvv);
    }

    default DataValidade toDataValidade(LocalDate dataValidade) {
        return new DataValidade(dataValidade);
    }

    default String toNumeroCartao(NumeroCartao numeroCartao) {
        return numeroCartao.getNumero();
    }

    default String toCVVString(CVV cvv){
        return cvv.getNumero();
    }

    default LocalDate toDataValidadeLocalDate(DataValidade dataValidade) {
        return dataValidade.getData();
    }

    CartaoDbEntity toCartaoDbEntity(Cartao cartao);

    List<CartaoDbEntity> toCartaoDbEntityList(List<Cartao> cartaoList);

    List<CartaoDto> toCartaoDtoList(List<Cartao> cartao);
}
