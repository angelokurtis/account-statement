package br.com.kurtis.financial.nubank.domain;

import br.com.kurtis.financial.nubank.model.Event;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class NubankAccount {

    @Getter
    private final List<NubankTransaction> transactions;

    private NubankAccount(List<NubankTransaction> transactions) {
        this.transactions = transactions;
    }

    public static NubankAccount newInstanceOf(List<Event> events) {
        final List<NubankTransaction> transactions = events
                .stream()
                .filter(event -> event.getCategory().equals("transaction"))
                .map(NubankTransaction::newInstanceOf)
                .collect(Collectors.toList());
        return new NubankAccount(transactions);
    }
}
