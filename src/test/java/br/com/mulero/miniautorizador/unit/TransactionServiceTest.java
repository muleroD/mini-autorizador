package br.com.mulero.miniautorizador.unit;

import br.com.mulero.miniautorizador.chain.TransactionValidator;
import br.com.mulero.miniautorizador.domain.repository.TransactionRepository;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.enumerator.TransactionType;
import br.com.mulero.miniautorizador.service.CardService;
import br.com.mulero.miniautorizador.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class TransactionServiceTest extends BaseUnitTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private CardService cardService;

    @Test
    void authorizeWithdraw() {
        doNothing().when(transactionValidator).validate(any(TransactionDTO.class), any(TransactionType.class));
        transactionService.withdraw(new TransactionDTO());

        verify(transactionValidator).validate(any(TransactionDTO.class), any(TransactionType.class));
        verify(cardService).withdraw(any(TransactionDTO.class));

        verify(transactionRepository).save(any());
    }
}
