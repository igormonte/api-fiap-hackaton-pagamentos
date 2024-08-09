package br.com.fiap.hackaton.cartao.domain.cartao.entity;

import lombok.Getter;

import java.util.Random;

public class NumeroCartao {

    private final static String FIAP_BANK = "9034";

    @Getter
    private String numero;

    public NumeroCartao() {

    }

    public NumeroCartao(String numero) {
        this.numero = numero;
    }

    public static NumeroCartao of(String numero) {
        return new NumeroCartao(numero);
    }

    public static NumeroCartao gerarNumero() {
        String numero = FIAP_BANK +
                String.valueOf(
                        new Random().nextLong(10000000000L, 99999999999L));

        int sum = 0;
        String reverse = new StringBuffer(numero).reverse().toString();
        for (int i = 0 ;i < reverse.length();i++) {
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return new NumeroCartao(numero + (10 - (sum % 10)) % 10);

    }

    public boolean validarNumero() {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(this.numero).reverse().toString();
        for (int i = 0 ;i < reverse.length();i++) {
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0) { s1 += digit; }
            else {
                s2 += 2 * digit;
                if (digit >= 5) { s2 -= 9; }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

}
