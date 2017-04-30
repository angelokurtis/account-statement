package br.com.kurtis.financial.account_statement.bradesco;

import br.com.kurtis.financial.account_statement.AccountService;
import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.bradesco.domain.BradescoAccount;
import br.com.kurtis.financial.account_statement.bradesco.html.Table;
import lombok.NonNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BradescoService implements AccountService {

    private Table tableFrom(@NonNull final String htmlPath) throws IOException {
        final File stream = new File(htmlPath);
        final Document document = Jsoup.parse(stream, "UTF-8");
        final Element tableBody = document.select(".tabSaldosExtratos tbody").first();
        return Table.instanceOf(tableBody);
    }

    @Override
    public List<Transaction> transactionsFrom(@NonNull final String htmlPath) throws IOException {
        final BradescoAccount bradescoAccount = BradescoAccount.newInstanceOf(tableFrom(htmlPath));
        final List transactions = bradescoAccount.getTransactions();
        return (List<Transaction>) transactions;
    }
}
