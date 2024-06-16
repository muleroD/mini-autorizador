package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.chain.TransactionValidator;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.domain.repository.TransactionRepository;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.enumerator.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionValidator transactionValidator;

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;

    public void authorize(TransactionDTO transactionDTO, TransactionType transactionType) {
        transactionValidator.validate(transactionDTO, transactionType);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> withdraw(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.WITHDRAW);
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> deposit(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.DEPOSIT);
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> transfer(TransactionDTO transactionDTO) {
        this.authorize(transactionDTO, TransactionType.TRANSFER);
        return null;
    }
}
