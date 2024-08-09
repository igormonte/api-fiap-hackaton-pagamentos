package br.com.fiap.hackaton.cartao.application.gateway;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.GerarCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepository;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;

public class GeraCartaoDbGateaway implements GerarCartaoRepository {

    private final CartaoDbEntityRepository cartaoDbEntityRepository;
    private final CartaoMapper cartaoMapper;

    public GeraCartaoDbGateaway(
            CartaoDbEntityRepository cartaoDbEntityRepository,
            CartaoMapper cartaoMapper) {
        this.cartaoDbEntityRepository = cartaoDbEntityRepository;
        this.cartaoMapper = cartaoMapper;
    }

    @Override
    public Cartao executar(Cartao cartao) {
        CartaoDbEntity cartaoDbEntity = this.cartaoMapper.toCartaoDbEntity(cartao);
        return this.cartaoMapper.toCartao(
                this.cartaoDbEntityRepository.save(
                        cartaoDbEntity));
    }
}
