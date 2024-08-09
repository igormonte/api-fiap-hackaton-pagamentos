package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.CartaoVencidoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.SemLimiteException;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.MetodoPagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.StatusPagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RegistrarPagamentoUseCaseImpl implements RegistrarPagamentoUseCase {
    private final RegistraPagamentoRepository pagamentoRepository;
    private final BuscaCartaoRepository buscarCartaoRepository;
    private final BuscaPagamentoRepository buscaPagamentoRepository;

    public RegistrarPagamentoUseCaseImpl(
            RegistraPagamentoRepository pagamentoRepository,
            BuscaCartaoRepository buscarCartaoRepository,
            BuscaPagamentoRepository buscaPagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.buscarCartaoRepository = buscarCartaoRepository;
        this.buscaPagamentoRepository = buscaPagamentoRepository;
    }

    @Override
    public Pagamento executar(Pagamento pagamento) {

        Cartao cartao = this.buscarCartaoRepository.porCpfENumero(
                pagamento.getCartao().getCpf(),
                pagamento.getCartao().getNumero());

        if(this.validaValidadeCartao(cartao)) {
            throw new CartaoVencidoException("Cartão vencido!");
        }

        pagamento.setCartao(cartao);

        if(!this.validaLimite(pagamento.getValor(), cartao)) {
            throw new SemLimiteException("Cartão sem limite!");
        }

        pagamento.setDescricao("Compra de produto X");
        pagamento.setMetodoPagamento(MetodoPagamento.cartao_credito);
        pagamento.setStatus(StatusPagamento.aprovado);

        return this.pagamentoRepository.registrarPagamento(pagamento);
    }

    private Boolean validaValidadeCartao(Cartao cartao) {
        return cartao.getDataValidade().isBefore(LocalDate.now());
    }

    private Boolean validaLimite(BigDecimal valor, Cartao cartao) {

        BigDecimal limite = cartao.getLimite();
        List<Pagamento> pagamentoList = this.buscaPagamentoRepository.porCpfAndNumero(
                cartao.getCpf(),
                cartao.getNumero());

        if(pagamentoList == null) {
            return true;
        }

        BigDecimal fatura = pagamentoList.stream()
                .map(Pagamento::getValor)
                .reduce(BigDecimal::add).orElse(null);

        if(fatura == null) {
            return true;
        }

        limite = limite.subtract(fatura);

        return limite.compareTo(valor) >= 0;

    }
}
