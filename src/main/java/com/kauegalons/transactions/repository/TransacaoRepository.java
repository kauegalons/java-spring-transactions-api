package com.kauegalons.transactions.repository;

import com.kauegalons.transactions.model.Transacao;
import org.springframework.stereotype.Repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class TransacaoRepository {


    Queue<Transacao> listaTransacoes = new ConcurrentLinkedQueue<>();

    //salvar os dados em uma lista
    public void salvarTransacao(Transacao transacao) {
        listaTransacoes.add(transacao);
    }


    //apagar todas as transacoes da lista
    public void apagarTodasTransacoes() {
        listaTransacoes.clear();
    }


    public Queue<Transacao> getListaTransacoes() {
        return new ConcurrentLinkedQueue<>(listaTransacoes);
    }

}
