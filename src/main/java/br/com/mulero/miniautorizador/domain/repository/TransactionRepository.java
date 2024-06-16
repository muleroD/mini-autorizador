package br.com.mulero.miniautorizador.domain.repository;

import br.com.mulero.miniautorizador.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
