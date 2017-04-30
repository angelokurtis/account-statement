package br.com.kurtis.financial.account_statement.spreadsheet;

import br.com.kurtis.financial.account_statement.Transaction;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpreadsheetGenerator {

    public void generate(@NonNull final List<Transaction> transactions, @NonNull final String path) throws IOException {
        final FinancialSpreadsheet financialSpreadsheet = FinancialSpreadsheet.newInstance();
        transactions.forEach(transaction -> financialSpreadsheet.lineBuilder()
                .dueDate(transaction.getDueDate())
                .bank(transaction.getBank())
                .description(transaction.getDescription())
                .code(transaction.getCode())
                .value(transaction.getValue())
                .build());
        financialSpreadsheet.writeOn(path);
    }

}
