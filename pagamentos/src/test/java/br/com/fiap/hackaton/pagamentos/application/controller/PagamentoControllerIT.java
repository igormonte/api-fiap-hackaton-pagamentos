//package br.com.fiap.hackaton.pagamentos.application.controller;
//
//import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
//import br.com.fiap.hackaton.pagamentos.application.dto.response.AutorizacaoDto;
//import br.com.fiap.hackaton.pagamentos.application.dto.response.PagamentoDto;
//import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
//import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCase;
//import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCase;
//import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.AutorizacaoMapper;
//import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Sql(scripts = {"/clean.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//class PagamentoControllerIT {
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    public void setup() {
//        RestAssured.port = port;
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        // RestAssured.filters(new AllureRestAssured()); // Use this if you are using Allure for reporting
//    }
//
//    @Nested
//    class RegistrarPagamento {
//
//        @Test
//        void devePermitirRegistrarPagamento() {
//            var registrarPagamentoRequest = PagamentoHelper.gerarRegistrarPagamentoRequest();
//
//            given()
//                    .filter(new AllureRestAssured())
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .body(registrarPagamentoRequest)
//                    .when()
//                    .post("/pagamentos")
//                    .then()
//                    .statusCode(HttpStatus.OK.value())
//                    .body("$", hasKey("idAutorizacao"))
//                    .body("$", hasKey("status"))
//                    .body("$", hasKey("mensagem"))
//                    .body("status", equalTo("APROVADO"));
//        }
//
//        @Test
//        void deveGerarExcecao_QuandoRegistrarPagamento_CamposInvalidos() {
//            var registrarPagamentoRequest = PagamentoHelper.gerarRegistrarPagamentoRequest();
//            registrarPagamentoRequest.setNumeroCartao(""); // Campo inválido
//
//            given()
//                    .filter(new AllureRestAssured())
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .body(registrarPagamentoRequest)
//                    .when()
//                    .post("/pagamentos")
//                    .then()
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .body("$", hasKey("message"))
//                    .body("$", hasKey("errors"))
//                    .body("message", equalTo("Validation error"))
//                    .body("errors[0]", equalTo("número do cartão não pode estar vazio"));
//        }
//
//        @Test
//        void deveGerarExcecao_QuandoRegistrarPagamento_PayloadComXml() {
//            String xmlPayload = "<pagamento><numeroCartao>1234567890123456</numeroCartao><valor>100.00</valor></pagamento>";
//
//            given()
//                    .contentType(MediaType.APPLICATION_XML_VALUE)
//                    .body(xmlPayload)
//                    .when()
//                    .post("/pagamentos")
//                    .then()
//                    .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
//        }
//    }
//
//    @Nested
//    class ConsultarPagamentosPorIdCliente {
//
//        @Test
//        @Sql(scripts = {"/clean.sql", "/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//        void devePermitirConsultarPagamentosPorIdCliente() {
//            var idCliente = "5f789b39-4295-42c1-a65b-cfca5b987db2";
//
//            given()
//                    .filter(new AllureRestAssured())
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when()
//                    .get("/pagamentos/cliente/{id}", idCliente)
//                    .then()
//                    .statusCode(HttpStatus.OK.value())
//                    .body(matchesJsonSchemaInClasspath("./schemas/PagamentoResponseSchema.json"));
//        }
//
//        @Test
//        void deveGerarExcecao_QuandoConsultarPagamentos_IdInvalido() {
//            var idCliente = "2";
//
//            given()
//                    .filter(new AllureRestAssured())
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when()
//                    .get("/pagamentos/cliente/{id}", idCliente)
//                    .then()
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .body(equalTo("ID inválido"));
//        }
//
//        @Test
//        void deveGerarExcecao_QuandoConsultarPagamentos_IdNaoExistente() {
//            var idCliente = "5f789b39-4295-42c1-a65b-cfca5b987db3";
//
//            given()
//                    .filter(new AllureRestAssured())
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .when()
//                    .get("/pagamentos/cliente/{id}", idCliente)
//                    .then()
//                    .statusCode(HttpStatus.NOT_FOUND.value())
//                    .body(equalTo("pagamentos não encontrados"));
//        }
//    }
//}
