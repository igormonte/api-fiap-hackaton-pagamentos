package br.com.fiap.hackaton.cartao.domain.cartao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Cartao {

    private UUID id;
    private UUID idCliente;
    private String cpf;
    private BigDecimal limite;
    private NumeroCartao numero;
    private DataValidade dataValidade;
    private CVV cvv;

    public Cartao() {

    }

    public Cartao(UUID idCliente,
                  String cpf,
                  BigDecimal limite,
                  String numero,
                  String dataValidade,
                  String cvv) {
        this.idCliente = idCliente;
        this.cpf = cpf;
        this.limite = limite;
        this.numero = new NumeroCartao(numero);
        this.dataValidade = DataValidade.doPadrao(dataValidade);
        this.cvv = new CVV(cvv);
    }

    public Cartao(
            UUID idCliente,
            String cpf,
            BigDecimal limite,
            Long validadeAnos
    ) {

        if(validadeAnos > 10) {
            throw new RuntimeException("Não é possível gerar um cartao com vencimento superior a 10 anos!");
        }

        if(limite.compareTo(BigDecimal.valueOf(5000))>=0) {
            throw new RuntimeException("Não é possível gerar um cartão com limite superior a 5000!");
        }

        this.idCliente = idCliente;
        this.cpf = cpf;
        this.numero = NumeroCartao.gerarNumero();
        this.limite = limite;
        this.dataValidade = new DataValidade(
                LocalDate.now().plusYears(validadeAnos));
        this.cvv = CVV.gerarNumero();

    }
    public Cartao(
        UUID idCliente,
        String cpf,
        BigDecimal limite,
        NumeroCartao numero,
        LocalDate dataValidade,
        CVV cvv
    ) {

        if(dataValidade.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cartão vencido!");
        }

        if(!numero.validarNumero()) {
            throw new RuntimeException("Número de cartão inválido!");
        }

        this.idCliente = idCliente;
        this.cpf = cpf;
        this.limite = limite;
        this.numero = numero;
        this.dataValidade = new DataValidade();
        this.cvv = cvv;

    }

    public Boolean cartaoEstaValido() {
        return !this.dataValidade.getData().isBefore(LocalDate.now());

    }

    public void adicionarLimite(BigDecimal valor) {
        if(valor==null) {
            throw new RuntimeException("Não pode ser adicionado um valor nulo!");
        }

        this.limite = this.limite.add(valor);
    }
}
