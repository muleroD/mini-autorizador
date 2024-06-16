package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import org.springframework.stereotype.Component;

@Component
public class CardExistsHandler implements ChainHandler {

    private ChainHandler nextChain;

    @Override
    public ChainHandler next(ChainHandler nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }

    @Override
    public void process(Object request) {
        // TODO: Implement the process
        nextChain.process(request);
    }
}
