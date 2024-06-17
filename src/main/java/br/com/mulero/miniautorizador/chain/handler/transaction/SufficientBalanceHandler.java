package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.InsufficientBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SufficientBalanceHandler implements ChainHandler {

    private ChainHandler nextChain;

    @Override
    public ChainHandler next(ChainHandler nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }

    @Override
    public <O, P> void process(O originalRequest, P processedRequest) {
        TransactionDTO transactionDTO = (TransactionDTO) originalRequest;
        Card card = (Card) processedRequest;

        if (card.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new InsufficientBalanceException();
        }

        if (nextChain != null) {
            nextChain.process(originalRequest, processedRequest);
        }
    }
}
