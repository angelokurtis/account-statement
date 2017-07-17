package br.com.kurtis.financial.account_statement.spreadsheet;

import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.nubank.domain.NubankTransaction;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SpreadsheetGenerator {

    public void generate(@NonNull final List<Transaction> transactions, @NonNull final String path) throws IOException {
        final FinancialSpreadsheet financialSpreadsheet = FinancialSpreadsheet.newInstance();
        transactions.forEach(transaction -> {
            final Optional<List<String>> optionalTags;
            if (transaction instanceof NubankTransaction) {
                final NubankTransaction nubankTransaction = (NubankTransaction) transaction;
                optionalTags = Optional.of(nubankTransaction.getTags());
            } else {
                optionalTags = Optional.empty();
            }

            final FinancialSpreadsheet.LineBuilder lineBuilder = financialSpreadsheet.lineBuilder()
                    .dueDate(transaction.getDueDate())
                    .bank(transaction.getBank())
                    .description(transaction.getDescription())
                    .code(transaction.getCode())
                    .value(transaction.getValue());
            if (optionalTags.isPresent()) lineBuilder.tags(optionalTags.get());
            lineBuilder.build();
        });
        financialSpreadsheet.writeOn(path);
    }

}
