package br.com.fiap.hackaton.cliente.infrastructure.db.repository;

import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDbEntity, UUID> {

    boolean existsByCpf(String cpf);

    Optional<ClienteDbEntity> findByCpf(String cpf);
}
