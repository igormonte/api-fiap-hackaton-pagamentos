//package br.com.fiap.hackaton.cartao;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest
//class PagamentosApplicationTests {
//
//	void deve_gerarNumeroCartao() {
//
//		NumeroCartaoTest numeroCartao = NumeroCartaoTest.gerarNumero();
//
//		assertThat(numeroCartao.getNumero()).isNotBlank();
//
//	}
//	void deve_gerarNumeroCartaoValido() {
//
//		NumeroCartaoTest numeroCartao = NumeroCartaoTest.gerarNumero();
//
//		assertThat(numeroCartao.getNumero()).isNotBlank();
//		assertThat(numeroCartao.validarNumero()).isTrue();
//
//	}
//
//	void deve_gerarNumeroCVV() {
//
//		CVVTest cvv = CVVTest.gerarNumero();
//
//		assertThat(cvv.getNumero()).isNotBlank();
//	}
//
//	void deve_gerarCartao() {
//
//		CartaoTest cartao = new CartaoTest(
//				UUID.randomUUID(),
//				"",
//				BigDecimal.valueOf(3000),
//				5L
//		);
//
//		assertThat(cartao.getCpf()).isNotBlank();
//		assertThat(cartao.getNumero()).isNotNull();
//		assertThat(cartao.getCvv()).isNotNull();
//		assertThat(cartao.getLimite()).isNotNull();
//		assertThat(cartao.getDataValidade()).isNotNull();
//		assertThat(cartao.getLimite()).isNotNull();
//		assertThat(cartao.getIdCliente()).isNotNull();
//
//	}
//
//	@Test
//	void contextLoads() {
//		NumeroCartaoTest numeroCartao = NumeroCartaoTest.gerarNumero();
//
//		CVVTest cvv = CVVTest.gerarNumero();
//
//		log.info(cvv.getNumero());
//		log.info(numeroCartao.getNumero());
//
//		assert numeroCartao.validarNumero();
//
//	}
//
//}
