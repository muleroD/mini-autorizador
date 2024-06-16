package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.chain.TransactionValidator;
import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.domain.entity.Transaction;
import br.com.mulero.miniautorizador.domain.repository.TransactionRepository;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.enumerator.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionValidator transactionValidator;

    private final CardService cardService;

    @Transactional(rollbackFor = Exception.class)
    public void withdraw(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.WITHDRAW);

        Card withdrawn = cardService.withdraw(transactionDTO);
        transactionRepository.save(new Transaction(withdrawn, TransactionType.WITHDRAW, transactionDTO.getAmount()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deposit(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.DEPOSIT);

        Card deposited = cardService.deposit(transactionDTO);
        transactionRepository.save(new Transaction(deposited, TransactionType.DEPOSIT, transactionDTO.getAmount()));
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(TransactionDTO transactionDTO, String cardNumber) {
        this.authorize(transactionDTO, TransactionType.TRANSFER);

        cardService.transfer(transactionDTO, cardNumber).forEach(card ->
                transactionRepository.save(new Transaction(card, TransactionType.TRANSFER, transactionDTO.getAmount())));
    }

    private void authorize(TransactionDTO transactionDTO, TransactionType transactionType) {
        transactionValidator.validate(transactionDTO, transactionType);
    }
}
