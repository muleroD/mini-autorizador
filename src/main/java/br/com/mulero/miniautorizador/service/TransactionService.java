package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.chain.TransactionValidator;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.enumerator.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionValidator transactionValidator;

    private final CardService cardService;

    @Transactional(rollbackFor = Exception.class)
    public void withdraw(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.WITHDRAW);
        cardService.withdraw(transactionDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deposit(TransactionDTO transactionDTO, String cardNumber) {
        this.authorize(transactionDTO, TransactionType.DEPOSIT);
        cardService.deposit(transactionDTO, cardNumber);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(TransactionDTO transactionDTO, String cardNumber) {
        this.authorize(transactionDTO, TransactionType.TRANSFER);
        cardService.transfer(transactionDTO, cardNumber);
    }

    private void authorize(TransactionDTO transactionDTO, TransactionType transactionType) {
        transactionValidator.validate(transactionDTO, transactionType);
    }
}
