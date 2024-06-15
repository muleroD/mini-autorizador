package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.domain.entity.Cartao;
import br.com.mulero.miniautorizador.domain.repository.CartaoRepository;
import br.com.mulero.miniautorizador.dto.CartaoDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.CartaoExistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class CartaoService {

    public static final BigDecimal SALDO_INICIAL = BigDecimal.valueOf(500);

    private final CartaoRepository cartaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CartaoDTO> salvar(CartaoDTO cartaoDTO) {
        Cartao cartao = Cartao.builder().numero(cartaoDTO.getNumeroCartao()).build();

        cartaoRepository.findOne(cartao.toExample()).ifPresent(c -> {
            throw new CartaoExistenteException(cartaoDTO);
        });

        cartao.setSenha(cartaoDTO.getSenha());
        cartao.setSaldo(SALDO_INICIAL);
        cartaoRepository.save(cartao);

        return ResponseEntity.status(CREATED).body(cartaoDTO);
    }

    public ResponseEntity<BigDecimal> obterSaldo(String numeroCartao) {
        return cartaoRepository.findOne(Cartao.builder().numero(numeroCartao).build().toExample())
                .map(Cartao::getSaldo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
