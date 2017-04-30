package br.com.kurtis.financial.account_statement.bradesco.html;

import br.com.kurtis.financial.account_statement.bradesco.html.column.AmountColumn;
import br.com.kurtis.financial.account_statement.bradesco.html.column.DateColumn;
import br.com.kurtis.financial.account_statement.bradesco.html.column.TextColumn;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.java.Log;
import org.jsoup.nodes.Element;

@Value
@Builder
public class Tuple {
    private final DateColumn date;
    private final TextColumn history;
    private final TextColumn document;
    private final AmountColumn credit;
    private final AmountColumn debit;
    private final AmountColumn outstandingBalance;

    public static Tuple instanceOf(@NonNull final Element tableRow) {
        return builder()
                .date(DateColumn.valueOf(tableRow.child(0)))
                .history(TextColumn.valueOf(tableRow.child(1)))
                .document(TextColumn.valueOf(tableRow.child(2)))
                .credit(AmountColumn.valueOf(tableRow.child(3)))
                .debit(AmountColumn.valueOf(tableRow.child(4)))
                .outstandingBalance(AmountColumn.valueOf(tableRow.child(5)))
                .build();
    }
}
