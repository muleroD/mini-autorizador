package br.com.mulero.miniautorizador.chain.handler;

public interface ChainHandler {

    ChainHandler next(ChainHandler nextChain);

    <O, P> void process(O originalRequest, P processedRequest);

    default <O> void process(O originalRequest) {
        process(originalRequest, null);
    }
}
