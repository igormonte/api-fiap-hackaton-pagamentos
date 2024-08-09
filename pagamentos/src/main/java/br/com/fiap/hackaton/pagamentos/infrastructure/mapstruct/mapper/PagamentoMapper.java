package br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper;

import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.PagamentoDto;
import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.time.LocalDate;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface PagamentoMapper {

    default PagamentoDto toPagamentoDto(Pagamento pagamento) {

        if(pagamento == null) {
            return null;
        }

        return new PagamentoDto(
                pagamento.getValor(),
                pagamento.getDescricao(),
                pagamento.getMetodoPagamento(),
                pagamento.getStatus()
        );
    }

    default Pagamento toPagamento(RegistrarPagamentoDto registrarPagamentoDto) {

        Cartao cartao = new Cartao();
        cartao.setCpf(registrarPagamentoDto.cpf());
        cartao.setCvv(registrarPagamentoDto.cvv());
        cartao.setNumero(registrarPagamentoDto.numero());

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(registrarPagamentoDto.valor());
        pagamento.setCartao(cartao);

        return pagamento;
    }

    default Pagamento toPagamento(PagamentoDbEntity pagamentoDbEntity) {

        if(pagamentoDbEntity == null) {
            return null;
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setId(pagamentoDbEntity.getId());
        pagamento.setDescricao(pagamentoDbEntity.getDescricao());
        pagamento.setMetodoPagamento(pagamentoDbEntity.getMetodoPagamento());
        pagamento.setValor(pagamentoDbEntity.getValor());
        pagamento.setStatus(pagamentoDbEntity.getStatus());

        Cartao cartao = new Cartao();
        cartao.setCpf(pagamentoDbEntity.getCpf());
        cartao.setCvv(pagamentoDbEntity.getCvv());
        cartao.setDataValidade(pagamentoDbEntity.getDataValidade());
        cartao.setNumero(pagamentoDbEntity.getNumero());
        cartao.setId(pagamentoDbEntity.getIdCartao());
        cartao.setIdCliente(pagamentoDbEntity.getIdCliente());
        pagamento.setCartao(cartao);

        return pagamento;

    }
    default PagamentoDbEntity toPagamentoDbEntity(Pagamento pagamento) {

        if(pagamento == null) {
            return null;
        }

        PagamentoDbEntity pagamentoDbEntity = new PagamentoDbEntity();
        pagamentoDbEntity.setId(pagamento.getId());
        pagamentoDbEntity.setDescricao(pagamento.getDescricao());
        pagamentoDbEntity.setMetodoPagamento(pagamento.getMetodoPagamento());
        pagamentoDbEntity.setValor(pagamento.getValor());
        pagamentoDbEntity.setStatus(pagamento.getStatus());

        if(pagamento.getCartao() != null) {
            pagamentoDbEntity.setCpf(pagamento.getCartao().getCpf());
            pagamentoDbEntity.setCvv(pagamento.getCartao().getCvv());
            pagamentoDbEntity.setDataValidade(pagamento.getCartao().getDataValidade());
            pagamentoDbEntity.setNumero(pagamento.getCartao().getNumero());
            pagamentoDbEntity.setIdCartao(pagamento.getCartao().getId());
            pagamentoDbEntity.setIdCliente(pagamento.getCartao().getIdCliente());

        }
        return pagamentoDbEntity;

    }

    List<PagamentoDto> toPagamentoDtoList(List<Pagamento> pagamentoList);

    List<Pagamento> toPagamentoList(List<PagamentoDbEntity> pagamentoDbEntityList);

    List<PagamentoDbEntity> toPagamentoDbEntityList(List<Pagamento> pagamento);
}
