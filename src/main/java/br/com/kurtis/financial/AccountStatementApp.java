package br.com.kurtis.financial;

import br.com.kurtis.financial.account_statement.AccountService;
import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.bradesco.BradescoService;
import br.com.kurtis.financial.account_statement.nubank.NubankService;
import br.com.kurtis.financial.account_statement.spreadsheet.SpreadsheetGenerator;
import br.com.kurtis.financial.infra.ContextConfiguration;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@Log
public class AccountStatementApp {

    public static final String NUBANK_FILE_FORMAT = ".json";
    public static final String BRADESCO_FILE_FORMAT = ".html";

    public static void main(@NonNull String args[]) throws IOException {
        final AccountService accountService;
        final ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        final SpreadsheetGenerator spreadsheetGenerator = context.getBean(SpreadsheetGenerator.class);
        final String filePath = args[0];

        if (filePath.endsWith(NUBANK_FILE_FORMAT)) {
            accountService = context.getBean(NubankService.class);
            log.info("reading Nubank account statement in " + filePath + "...");
        } else if (filePath.endsWith(BRADESCO_FILE_FORMAT)) {
            accountService = context.getBean(BradescoService.class);
            log.info("reading Bradesco account statement in " + filePath + "...");
        } else {
            throw new IllegalArgumentException("Invalid file format");
        }

        final List<Transaction> transactions = accountService.transactionsFrom(filePath);
        log.info("account statement was read successfully!");

        final String[] splited = filePath.split("\\.");
        splited[splited.length - 1] = ".xlsx";
        final String fileResultPath = String.join("", splited);
        log.info("saving " + fileResultPath + "...");
        spreadsheetGenerator.generate(transactions, fileResultPath);
        log.info(fileResultPath + " saved!");
    }
}
