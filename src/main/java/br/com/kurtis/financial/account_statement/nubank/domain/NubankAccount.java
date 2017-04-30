package br.com.kurtis.financial.account_statement.nubank.domain;

import br.com.kurtis.financial.account_statement.nubank.json.Event;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

public class NubankAccount {

    @Getter
    private final List<NubankTransaction> transactions;

    private NubankAccount(@NonNull final List<NubankTransaction> transactions) {
        this.transactions = transactions;
    }

    public static NubankAccount newInstanceOf(@NonNull final List<Event> events) {
        final List<NubankTransaction> transactions = events
                .stream()
                .filter(event -> event.getCategory().equals("transaction"))
                .flatMap(event -> NubankTransaction.instancesOf(event).stream())
                .sorted((transaction, nextTransaction) -> {
                    final int comparator = transaction.getDueDate().compareTo(nextTransaction.getDueDate());
                    return comparator != 0 ? comparator : transaction.getDate().compareTo(nextTransaction.getDate());
                })
                .collect(Collectors.toList());
        return new NubankAccount(transactions);
    }
}
