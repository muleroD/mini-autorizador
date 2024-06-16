package br.com.mulero.miniautorizador.chain;

import br.com.mulero.miniautorizador.chain.handler.transaction.CardExistsHandler;
import br.com.mulero.miniautorizador.chain.handler.transaction.PasswordCorrectHandler;
import br.com.mulero.miniautorizador.chain.handler.transaction.SufficientBalanceHandler;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionValidator {

    private final CardExistsHandler cardExistsHandler;
    private final PasswordCorrectHandler passwordCorrectHandler;
    private final SufficientBalanceHandler sufficientBalanceHandler;

    public void validate(TransactionDTO transaction) throws Exception {
        this.cardExistsHandler
                .next(passwordCorrectHandler)
                .next(sufficientBalanceHandler);

        this.cardExistsHandler.process(transaction);
    }
}
