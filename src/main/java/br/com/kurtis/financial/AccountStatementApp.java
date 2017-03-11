package br.com.kurtis.financial;

import br.com.kurtis.financial.infra.ContextConfiguration;
import br.com.kurtis.financial.nubank.NubankService;
import br.com.kurtis.financial.nubank.domain.NubankTransaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountStatementApp {

    public static void main(String args[]) throws IOException {
        final ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfiguration.class);
        final NubankService nubankService = context.getBean(NubankService.class);

        final String filePath = args[0];
        final List<NubankTransaction> transactions = nubankService.transactionsFrom(filePath);
        final Map<LocalDate, List<NubankTransaction>> collect = transactions.stream().collect(Collectors.groupingBy(NubankTransaction::getDueDate));

        System.out.printf("NubankAccount : " + transactions);
    }
}
