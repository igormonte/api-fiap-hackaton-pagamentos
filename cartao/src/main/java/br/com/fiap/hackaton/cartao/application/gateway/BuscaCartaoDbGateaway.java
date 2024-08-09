package br.com.fiap.hackaton.cartao.application.gateway;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepository;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;

import java.util.List;
import java.util.Optional;

public class BuscaCartaoDbGateaway implements BuscaCartaoRepository {

    private final CartaoDbEntityRepository cartaoDbEntityRepository;
    private final CartaoMapper cartaoMapper;

    public BuscaCartaoDbGateaway(
            CartaoDbEntityRepository cartaoDbEntityRepository,
            CartaoMapper cartaoMapper) {
        this.cartaoDbEntityRepository = cartaoDbEntityRepository;
        this.cartaoMapper = cartaoMapper;
    }

    @Override
    public List<Cartao> porCpf(String cpf) {
        return this.cartaoMapper.toCartaoList(
                this.cartaoDbEntityRepository.findByCpf(cpf));
    }

    @Override
    public Optional<Cartao> porCpfENumeroCartao(String cpf, String numero) {
        return Optional.ofNullable(
                this.cartaoMapper.toCartao(
                        this.cartaoDbEntityRepository.findByCpfAndNumero(cpf, numero).orElse(null)));
    }

    @Override
    public Optional<Cartao> porNumeroCartao(String numero) {
        return Optional.ofNullable(
                this.cartaoMapper.toCartao(
                        this.cartaoDbEntityRepository.findByNumero(numero).orElse(null)));
    }
}
