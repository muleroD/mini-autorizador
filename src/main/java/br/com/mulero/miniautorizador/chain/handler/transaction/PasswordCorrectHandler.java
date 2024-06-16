package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordCorrectHandler implements ChainHandler {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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

        cardRepository.findPasswordByNumber(transactionDTO.getCardNumber())
                .filter(password -> bCryptPasswordEncoder.matches(transactionDTO.getPassword(), password))
                .ifPresentOrElse(password -> nextChain.process(request), () -> {
                    throw new InvalidPasswordException();
                });
    }
}
