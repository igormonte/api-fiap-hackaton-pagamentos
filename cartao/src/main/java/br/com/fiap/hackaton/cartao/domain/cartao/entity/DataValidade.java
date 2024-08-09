package br.com.fiap.hackaton.cartao.domain.cartao.entity;

import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class DataValidade {

    private static final String PATTERN = "(0?[1-9]|1[012])/([0-9][0-9])$";
    private static final Pattern pattern = Pattern.compile(PATTERN);
    private LocalDate data;


    public DataValidade() {

    }

    public DataValidade(LocalDate data) {
        this.data = data;
    }

    /*
    * O padrão especificado deve ser MM/yy, caso não seja respeitado, pode ocasionar em erro.
    * */
    public static DataValidade doPadrao(String valor) {

        Matcher matcher = pattern.matcher(valor);

        if(!matcher.matches()) {
            throw new RuntimeException("Data inválida!");
        }

        String mes = matcher.group(1);
        String anoFinal = matcher.group(2);

        if(Integer.parseInt(mes)<1 || Integer.parseInt(mes) > 12) {
            throw new RuntimeException("Informação Mês incorreta!");
        }

        String anoInicial = String.valueOf(LocalDate.now().getYear()).substring(0,2);

        LocalDate data = LocalDate.of(
                Integer.parseInt(anoInicial + anoFinal),Integer.parseInt(mes), 1);

        data = data
                .plusMonths(1)
                .minusDays(1);

        return new DataValidade(data);

    }

}
