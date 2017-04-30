package br.com.kurtis.financial.account_statement.bradesco.domain;

import br.com.kurtis.financial.account_statement.bradesco.html.Table;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class BradescoAccount {

    @Getter
    private final List<BradescoTransaction> transactions;

    private BradescoAccount(@NonNull final List<BradescoTransaction> transactions) {
        this.transactions = transactions;
    }

    public static BradescoAccount newInstanceOf(@NonNull final Table table) {
        final List<BradescoTransaction> transactions = BradescoTransaction.instancesOf(table.getTuples());
        return new BradescoAccount(transactions);
    }
}
