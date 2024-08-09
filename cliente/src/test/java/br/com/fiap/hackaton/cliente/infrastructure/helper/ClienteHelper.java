package br.com.fiap.hackaton.cliente.infrastructure.helper;

import br.com.fiap.hackaton.cliente.application.dto.request.CriarClienteDto;
import br.com.fiap.hackaton.cliente.application.dto.response.ClienteDto;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;

import java.util.UUID;

public class ClienteHelper {

    public static ClienteDbEntity gerarClienteDbEntity() {
        ClienteDbEntity clienteDbEntity = new ClienteDbEntity();
        clienteDbEntity.setId(UUID.randomUUID());
        clienteDbEntity.setCpf("12312312312");
        clienteDbEntity.setNome("Fulano da Silav");
        clienteDbEntity.setEmail("fulano@exemplo.com");
        clienteDbEntity.setTelefone("1122332233");
        clienteDbEntity.setRua("Rua Ex");
        clienteDbEntity.setCidade("Teste");
        clienteDbEntity.setEstado("Hackaton");
        clienteDbEntity.setCep("12341123");
        clienteDbEntity.setPais("Brasil");
        return clienteDbEntity;
    }

    public static Cliente gerarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(UUID.randomUUID());
        cliente.setCpf("12312312312");
        cliente.setNome("Fulano da Silav");
        cliente.setEmail("fulano@exemplo.com");
        cliente.setTelefone("1122332233");
        cliente.setRua("Rua Ex");
        cliente.setCidade("Teste");
        cliente.setEstado("Hackaton");
        cliente.setCep("12341123");
        cliente.setPais("Brasil");
        return cliente;
    }

    public static CriarClienteDto gerarCriarClienteDto() {
        return new CriarClienteDto(
            "12312312312",
            "Fulano da Silav",
            "fulano@exemplo.com",
            "1122332233",
            "Rua Ex",
            "Teste",
            "Hackaton",
            "12341123",
            "Brasil"
        );
    }
    public static ClienteDto gerarClienteDto() {
        return new ClienteDto(
                UUID.randomUUID(),
                "12312312312",
                "Fulano da Silav",
                "fulano@exemplo.com"
        );
    }

}
