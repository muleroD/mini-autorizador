package br.com.mulero.miniautorizador.domain.repository;

import br.com.mulero.miniautorizador.domain.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
