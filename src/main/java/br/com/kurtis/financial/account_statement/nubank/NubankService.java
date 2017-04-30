package br.com.kurtis.financial.account_statement.nubank;

import br.com.kurtis.financial.account_statement.AccountService;
import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.nubank.domain.NubankAccount;
import br.com.kurtis.financial.account_statement.nubank.domain.NubankTransaction;
import br.com.kurtis.financial.account_statement.nubank.json.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class NubankService implements AccountService {

    private final ObjectMapper mapper;

    @Autowired
    public NubankService(@NonNull final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    private List<Event> eventsFrom(@NonNull final String jsonPath) throws IOException {
        final File stream = new File(jsonPath);
        final JsonNode jsonEvents = mapper.readTree(stream).get("events");
        final Event[] events = mapper.treeToValue(jsonEvents, Event[].class);
        return Arrays.asList(events);
    }

    private List<NubankTransaction> nubankTransactionsFrom(@NonNull final String jsonPath) throws IOException {
        final NubankAccount nubankAccount = NubankAccount.newInstanceOf(eventsFrom(jsonPath));
        return nubankAccount.getTransactions();
    }

    public List<Transaction> transactionsFrom(@NonNull final String jsonPath) throws IOException {
        final List transactions = nubankTransactionsFrom(jsonPath);
        return (List<Transaction>) transactions;
    }
}
