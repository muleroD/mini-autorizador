package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.CardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardExistsHandler implements ChainHandler {

    private final CardRepository cardRepository;

    private ChainHandler nextChain;

    @Override
    public ChainHandler next(ChainHandler nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }

    @Override
    public <O, P> void process(O originalRequest, P processedRequest) {
        TransactionDTO transactionDTO = (TransactionDTO) originalRequest;
        Card cardExample = Card.builder().number(transactionDTO.getCardNumber()).build();

        cardRepository.findOne(cardExample.toExample())
                .ifPresentOrElse(card -> nextChain.process(originalRequest, card), () -> {
                    throw new CardNotFoundException();
                });
    }
}
