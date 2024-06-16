package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
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
    public void process(Object request) {
        TransactionDTO transactionDTO = (TransactionDTO) request;

        cardRepository.findFirstByNumber(transactionDTO.getCardNumber())
                .ifPresentOrElse(exists -> nextChain.process(request), () -> {
                    throw new CardNotFoundException();
                });
    }
}
