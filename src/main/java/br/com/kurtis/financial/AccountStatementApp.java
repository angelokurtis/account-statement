package br.com.kurtis.financial;

import br.com.kurtis.financial.domain.Transaction;
import br.com.kurtis.financial.infra.ContextConfiguration;
import br.com.kurtis.financial.nubank.NubankService;
import br.com.kurtis.financial.spreadsheet.SpreadsheetGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

public class AccountStatementApp {

    public static void main(String args[]) throws IOException {
        final ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        final NubankService nubankService = context.getBean(NubankService.class);
        final SpreadsheetGenerator spreadsheetGenerator = context.getBean(SpreadsheetGenerator.class);

        final String filePath = args[0];
        final List<Transaction> transactions = nubankService.transactionsFrom(filePath);
        spreadsheetGenerator.generate(transactions, filePath.replace(".json", ".xlsx"));
    }
}
