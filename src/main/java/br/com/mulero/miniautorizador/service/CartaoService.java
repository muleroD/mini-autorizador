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

    private final CartaoRepository cartaoRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CartaoDTO> salvar(CartaoDTO cartaoDTO) {
        Cartao cartao = Cartao.builder().numero(cartaoDTO.numeroCartao()).build();

        cartaoRepository.findOne(cartao.toExample()).ifPresent(c -> {
            throw new CartaoExistenteException(cartaoDTO);
        });

        cartao.setSenha(cartaoDTO.senha());
        cartao.setSaldo(BigDecimal.valueOf(500));
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
