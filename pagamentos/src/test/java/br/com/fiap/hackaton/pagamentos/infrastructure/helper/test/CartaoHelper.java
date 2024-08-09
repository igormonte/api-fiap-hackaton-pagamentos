package br.com.fiap.hackaton.pagamentos.infrastructure.helper.test;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto.CartaoDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CartaoHelper {

    public static Cartao gerarCartao() {
        Cartao cartao = new Cartao();
        cartao.setId(UUID.randomUUID());
        cartao.setIdCliente(UUID.randomUUID());
        cartao.setCpf("12345678901");
        cartao.setLimite(new BigDecimal(1000));
        cartao.setNumero("1234567890123456");
        cartao.setDataValidade(LocalDate.now().plusYears(1));
        cartao.setCvv("123");
        return cartao;
    }
    public static CartaoDto gerarCartaoDto() {
        return new CartaoDto(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "12345678901",
                new BigDecimal(1000),
                "1234567890123456",
                LocalDate.now().plusYears(1),
                "123"
        );
    }
}
