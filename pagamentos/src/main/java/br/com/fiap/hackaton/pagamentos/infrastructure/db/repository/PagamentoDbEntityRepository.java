package br.com.fiap.hackaton.pagamentos.infrastructure.db.repository;

import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagamentoDbEntityRepository extends JpaRepository<PagamentoDbEntity, UUID> {

    List<PagamentoDbEntity> findByCpfAndNumero(String cpf, String numero);

    List<PagamentoDbEntity> findByIdCliente(UUID id);
}
