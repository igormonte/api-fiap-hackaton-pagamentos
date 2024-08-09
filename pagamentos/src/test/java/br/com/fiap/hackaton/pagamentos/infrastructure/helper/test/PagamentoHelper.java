package br.com.fiap.hackaton.pagamentos.infrastructure.helper.test;

import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.PagamentoDto;
import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.MetodoPagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.StatusPagamento;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PagamentoHelper {

    public static PagamentoDbEntity gerarPagamentoDbEntity() {
        PagamentoDbEntity pagamentoDbEntiy = new PagamentoDbEntity();
        pagamentoDbEntiy.setId(UUID.randomUUID());
        pagamentoDbEntiy.setIdCartao(UUID.randomUUID());
        pagamentoDbEntiy.setIdCliente(UUID.randomUUID());
        pagamentoDbEntiy.setCpf("12345678901");
        pagamentoDbEntiy.setNumero("1234567890123456");
        pagamentoDbEntiy.setDataValidade(LocalDate.now().plusYears(1));
        pagamentoDbEntiy.setCvv("123");
        pagamentoDbEntiy.setDescricao("Pagamento Teste");
        pagamentoDbEntiy.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamentoDbEntiy.setStatus(StatusPagamento.aprovado);
        pagamentoDbEntiy.setValor(new BigDecimal("100.00"));
        return pagamentoDbEntiy;
    }

    public static Pagamento gerarPagamento() {
        Cartao cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setIdCliente(UUID.randomUUID());
        cartao.setCpf("12345678901");
        cartao.setNumero("1234567890123456");
        cartao.setDataValidade(LocalDate.now().plusYears(1));
        cartao.setCvv("123");

        Pagamento pagamento = new Pagamento();
        pagamento.setId(UUID.randomUUID());
        pagamento.setDescricao("Pagamento Teste");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setStatus(StatusPagamento.aprovado);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setCartao(cartao);
        return pagamento;
    }
    public static PagamentoDto gerarPagamentoDto() {
        return new PagamentoDto(
                new BigDecimal("100.00"),
                "Pagamento Teste",
                MetodoPagamento.cartao_credito,
                StatusPagamento.aprovado
        );
    }

    public static RegistrarPagamentoDto gerarRegistrarPagamentoDto() {
        return new RegistrarPagamentoDto(
                "12345678901",
                "1234567890123456",
                "12/29",
                "123",
                new BigDecimal("100.00")
        );
    }

}
