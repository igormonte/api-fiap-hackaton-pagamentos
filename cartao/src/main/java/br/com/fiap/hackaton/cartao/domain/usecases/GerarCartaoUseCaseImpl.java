package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.CartaoJaCadastradoException;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.NumeroMaximoCartoesAtingidoException;
import br.com.fiap.hackaton.cartao.domain.cliente.entity.Cliente;
import br.com.fiap.hackaton.cartao.domain.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaClienteRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.GerarCartaoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class GerarCartaoUseCaseImpl implements GerarCartaoUseCase {

    private final GerarCartaoRepository gerarCartaoRepository;
    private final BuscaClienteRepository buscaClienteRepository;
    private final BuscaCartaoRepository buscarCartaoRepository;

    public GerarCartaoUseCaseImpl(GerarCartaoRepository gerarCartaoRepository,
                                  BuscaClienteRepository buscaClienteRepository,
                                  BuscaCartaoRepository buscarCartaoRepository) {
        this.gerarCartaoRepository = gerarCartaoRepository;
        this.buscaClienteRepository = buscaClienteRepository;
        this.buscarCartaoRepository = buscarCartaoRepository;
    }

    @Override
    public Cartao gerarPlanoCustomizado(String cpf,
                                        BigDecimal limite,
                                        String numero,
                                        String dataValidade,
                                        String cvv) {

        if(this.verificaCartaoExiste(numero)) {
            throw new CartaoJaCadastradoException("Já existe um cartão cadastrado com o número informado.");
        }

        Cliente cliente = this.buscaCliente(cpf);

        if(!this.verificaPossibilitadeGeracaoCartao(cpf)) {
            throw new NumeroMaximoCartoesAtingidoException("Número máximo de cartões atingido!");

        }

        Cartao cartao = new Cartao(cliente.getId(), cpf, limite, numero, dataValidade, cvv);

        return this.gerarCartaoRepository.executar(cartao);

    }

    private Boolean verificaCartaoExiste(String numero) {

        Optional<Cartao> cartao = this.buscarCartaoRepository.porNumeroCartao(numero);

        return cartao.isPresent();

    }

    private Cliente buscaCliente(String cpf) {

        Optional<Cliente> cliente = this.buscaClienteRepository.buscaPorCpf(cpf);

        if(cliente.isEmpty()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");
        }

        return cliente.get();

    }

    private Boolean verificaPossibilitadeGeracaoCartao(String cpf) {

        List<Cartao> cartaoCadastrado = this.buscarCartaoRepository.porCpf(cpf);

        return cartaoCadastrado == null || cartaoCadastrado.size() < 2;

    }

}
