package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordCorrectHandler implements ChainHandler {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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

        if (!bCryptPasswordEncoder.matches(transactionDTO.getPassword(), card.getPassword())) {
            throw new InvalidPasswordException();
        }

        nextChain.process(originalRequest, processedRequest);
    }
}
