package br.com.mulero.miniautorizador.chain.handler.transaction;

import br.com.mulero.miniautorizador.chain.handler.ChainHandler;
import org.springframework.stereotype.Component;

@Component
public class PasswordCorrectHandler implements ChainHandler {

    private ChainHandler nextChain;

    @Override
    public ChainHandler next(ChainHandler nextChain) {
        this.nextChain = nextChain;
        return nextChain;
    }

    @Override
    public void process(Object request) {
        // TODO: Implement this method
        nextChain.process(request);
    }
}
