package br.com.fiap.hackaton.cartao.infrastructure.db.repository;

import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoDbEntityRepository extends JpaRepository<CartaoDbEntity, UUID> {

    List<CartaoDbEntity> findByCpf(String cpf);

    Optional<CartaoDbEntity> findByCpfAndNumero(String cpf, String numero);

    Optional<CartaoDbEntity> findByNumero(String numero);
}
