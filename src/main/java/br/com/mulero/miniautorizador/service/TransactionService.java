package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.chain.TransactionValidator;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionValidator transactionValidator;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Object> authorize(TransactionDTO transactionDTO) throws Exception {
        transactionValidator.validate(transactionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
