package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;

import java.math.BigDecimal;
import java.util.UUID;

public interface GerarCartaoUseCase {
    Cartao gerarPlanoCustomizado(String cpf,
                                 BigDecimal limite,
                                 String numero,
                                 String dataValidade,
                                 String cvv);
}
