package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;

import java.util.List;
import java.util.Optional;

public class BuscarCartaoUseCaseImpl implements BuscarCartaoUseCase {

    private final BuscaCartaoRepository buscarCartaoRepository;

    public BuscarCartaoUseCaseImpl(
            BuscaCartaoRepository buscarCartaoRepository) {
        this.buscarCartaoRepository = buscarCartaoRepository;
    }

    @Override
    public Optional<Cartao> porCpfENumeroCartao(String cpf, String numero) {
        return this.buscarCartaoRepository.porCpfENumeroCartao(cpf, numero);
    }

    @Override
    public Optional<Cartao> porNumeroCartao(String numero) {
        return this.buscarCartaoRepository.porNumeroCartao(numero);
    }

    @Override
    public List<Cartao> porCpf(String cpf) {
        return this.buscarCartaoRepository.porCpf(cpf);
    }
}
