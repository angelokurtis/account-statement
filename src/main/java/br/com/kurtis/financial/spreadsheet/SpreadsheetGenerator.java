package br.com.kurtis.financial.spreadsheet;

import br.com.kurtis.financial.domain.Transaction;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SpreadsheetGenerator {

    public void generate(List<Transaction> transactions, String path) throws IOException {
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
