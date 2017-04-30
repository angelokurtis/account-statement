package br.com.kurtis.financial.account_statement.bradesco.domain;

import br.com.kurtis.financial.account_statement.Bank;
import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.bradesco.html.Tuple;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@EqualsAndHashCode(callSuper = true)
public class BradescoTransaction extends Transaction {

    private Optional<String> category;

    @Builder
    public BradescoTransaction(final Bank bank, final String code, final String description, final LocalDate dueDate, final BigDecimal value, final String category) {
        super(bank, code, description, dueDate, value);
        this.category = Optional.ofNullable(category);
    }

    public static List<BradescoTransaction> instancesOf(@NonNull final List<Tuple> tuples) {
        final List<BradescoTransaction> bradescoTransactions = Lists.newArrayList();
        LocalDate dueDate = null;
        for (Tuple tuple : tuples) {
            if (tuple.getDate().getValue() != null) dueDate = tuple.getDate().getValue();
            bradescoTransactions.add(BradescoTransaction.builder()
                    .dueDate(dueDate)
                    .bank(Bank.BRADESCO)
                    .description(tuple.getHistory().getValue())
                    .value(tuple.getDebit().getValue().add(tuple.getCredit().getValue()))
                    .code(tuple.getDocument().getValue())
                    .build());
        }
        return bradescoTransactions;
    }
}
