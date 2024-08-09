package br.com.fiap.hackaton.cartao.domain.cartao.entity;

import lombok.Getter;

import java.util.Random;

public class CVV {

    @Getter
    private String numero;

    public CVV() {

    }
    public CVV(String numero) {
        this.numero = numero;
    }

    public static CVV gerarNumero() {
        String numero = String.format("%03d",
                        new Random().nextInt(0, 999));
        return new CVV(numero);
    }

}
