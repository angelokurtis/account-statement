package br.com.kurtis.financial.nubank.domain;

import br.com.kurtis.financial.domain.Bank;
import br.com.kurtis.financial.domain.Transaction;
import br.com.kurtis.financial.infra.ConfigProperties;
import br.com.kurtis.financial.nubank.model.Charges;
import br.com.kurtis.financial.nubank.model.Event;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

@Data
public class NubankTransaction extends Transaction {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private LocalDateTime date;

    @Builder
    public NubankTransaction(final LocalDate dueDate, final Bank bank, final String description, final BigDecimal value, final String code, final LocalDateTime date) {
        super(dueDate, bank, description, code, value);
        this.date = date;
    }

    public static List<NubankTransaction> instancesOf(Event event) {
        final List<NubankTransaction> nubankTransactions = Lists.newArrayList();

        final String timeZoneId = ConfigProperties.getProperty("app.time_zone");
        final TimeZone zone = TimeZone.getTimeZone(timeZoneId);
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(DATETIME_FORMAT)
                .withZone(zone.toZoneId());
        final DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        final LocalDateTime date = LocalDateTime.parse(event.getTime(), formatter);
        final Integer dueDate = Integer.valueOf(ConfigProperties.getProperty("nubank.due_date"));
        final LocalDate nextDueDate = date.getDayOfMonth() >= (dueDate - 7) ? date.plusMonths(1).withDayOfMonth(dueDate).toLocalDate() : date.withDayOfMonth(dueDate).toLocalDate();

        final String description = '[' + date.format(stringFormatter) + "] " + event.getDescription();

        final Charges charges = event.getDetails().getCharges();
        final boolean hasCharge = charges != null;

        final String amount = hasCharge ? charges.getAmount().toString() : event.getAmount().toString();
        final String valueString = new StringBuilder(amount).insert(amount.length() - 2, ".").toString();
        final BigDecimal value = new BigDecimal(valueString);

        for (int i = 0; i < (hasCharge ? charges.getCount() : 1); i++) {
            nubankTransactions.add(NubankTransaction.builder()
                    .date(date)
                    .dueDate(nextDueDate.plusMonths(i))
                    .bank(Bank.NUBANK)
                    .description(hasCharge ? description + " (" + (i + 1) + "/" + charges.getCount() + ")" : description)
                    .value(value)
                    .code(event.getId())
                    .build());
        }
        return nubankTransactions;
    }
}
