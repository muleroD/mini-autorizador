package br.com.mulero.miniautorizador.chain.handler;

public interface ChainHandler {

    ChainHandler next(ChainHandler nextChain);

    void process(Object request);
}
