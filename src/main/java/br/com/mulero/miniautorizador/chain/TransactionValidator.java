package br.com.mulero.miniautorizador.chain;

import br.com.mulero.miniautorizador.chain.handler.transaction.CardExistsHandler;
import br.com.mulero.miniautorizador.chain.handler.transaction.PasswordCorrectHandler;
import br.com.mulero.miniautorizador.chain.handler.transaction.SufficientBalanceHandler;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.enumerator.TransactionType;
import br.com.mulero.miniautorizador.infrastructure.exception.TransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionValidator {

    private final CardExistsHandler cardExistsHandler;
    private final PasswordCorrectHandler passwordCorrectHandler;
    private final SufficientBalanceHandler sufficientBalanceHandler;

    public void validate(TransactionDTO transaction, TransactionType transactionType) throws TransactionException {
        switch (transactionType) {
            case DEPOSIT:
                validateDeposit(transaction);
                break;
            case WITHDRAW, TRANSFER:
                validateWithdrawOrTransfer(transaction);
                break;
            default:
                throw new TransactionException("error.invalid.transaction.type");
        }
    }

    private void validateWithdrawOrTransfer(TransactionDTO transaction) {
        this.cardExistsHandler
                .next(passwordCorrectHandler)
                .next(sufficientBalanceHandler);

        this.cardExistsHandler.process(transaction);
    }

    private void validateDeposit(TransactionDTO transaction) {
        this.cardExistsHandler
                .next(passwordCorrectHandler);

        this.cardExistsHandler.process(transaction);
    }
}
