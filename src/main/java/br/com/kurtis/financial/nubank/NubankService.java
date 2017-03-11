package br.com.kurtis.financial.nubank;

import br.com.kurtis.financial.nubank.domain.NubankAccount;
import br.com.kurtis.financial.nubank.domain.NubankTransaction;
import br.com.kurtis.financial.nubank.model.Event;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class NubankService {

    private final ObjectMapper mapper;

    @Autowired
    public NubankService(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public List<NubankTransaction> transactionsFrom(String jsonPath) throws IOException {
        final NubankAccount nubankAccount = NubankAccount.newInstanceOf(eventsFrom(jsonPath));
        return nubankAccount.getTransactions();
    }

    private List<Event> eventsFrom(String jsonPath) throws IOException {
        final File stream = new File(jsonPath);
        final JsonNode jsonEvents = mapper.readTree(stream).get("events");
        final Event[] events = mapper.treeToValue(jsonEvents, Event[].class);
        return Arrays.asList(events);
    }
}
