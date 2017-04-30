package br.com.kurtis.financial.account_statement.nubank.domain;

import br.com.kurtis.financial.account_statement.Bank;
import br.com.kurtis.financial.account_statement.Transaction;
import br.com.kurtis.financial.account_statement.nubank.json.Event;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Data
@EqualsAndHashCode(callSuper = true)
public class NubankTransaction extends Transaction {

    private Optional<String> category;
    private LocalDateTime date;
    private List<String> tags;

    @Builder
    public NubankTransaction(final Bank bank, final String code, final String description, final LocalDate dueDate, final BigDecimal value, final String category, final LocalDateTime date, final List<String> tags) {
        super(bank, code, description, dueDate, value);
        this.category = Optional.ofNullable(category);
        this.date = date;
        this.tags = tags != null ? tags : emptyList();
    }

    public static List<NubankTransaction> instancesOf(@NonNull final Event event) {
        final List<NubankTransaction> nubankTransactions = Lists.newArrayList();
        for (int i = 0; i < event.getNumberOfCharges(); i++) {
            nubankTransactions.add(NubankTransaction.builder()
                    .date(event.getDateTime())
                    .dueDate(event.getDueDate().plusMonths(i))
                    .bank(Bank.NUBANK)
                    .description(event.hasCharges() ? event.getFullDescription() + " (" + (i + 1) + '/' + event.getNumberOfCharges() + ')' : event.getFullDescription())
                    .value(event.getValue())
                    .code(event.getId())
                    .category(event.getTitle())
                    .tags(event.getDetails().getTags())
                    .build());
        }
        return nubankTransactions;
    }
}
