package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.application.gateway.exception.CartaoNaoEncontradoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.CartaoMessagingGateway;
import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto.CartaoDto;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.CartaoMapper;
import org.springframework.http.ResponseEntity;

public class BuscaCartaoDbGateway implements BuscaCartaoRepository {
    private final CartaoMessagingGateway cartaoMessagingGateway;
    private final CartaoMapper cartaoMapper;

    public BuscaCartaoDbGateway(
            CartaoMessagingGateway cartaoMessagingGateway,
            CartaoMapper cartaoMapper) {
        this.cartaoMessagingGateway = cartaoMessagingGateway;
        this.cartaoMapper = cartaoMapper;
    }

    @Override
    public Cartao porCpfENumero(String cpf, String numero) {
        ResponseEntity<CartaoDto> cartao =
                this.cartaoMessagingGateway.buscarPorCpfENumero(cpf,numero);

        if(cartao.getStatusCode().isError()) {
            throw new CartaoNaoEncontradoException("Cartão nao encontrado");
        }

        return this.cartaoMapper.toCartao(cartao.getBody());
    }
}
