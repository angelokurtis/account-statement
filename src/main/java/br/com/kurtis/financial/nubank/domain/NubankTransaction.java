package br.com.kurtis.financial.nubank.domain;

import br.com.kurtis.financial.domain.Bank;
import br.com.kurtis.financial.domain.Transaction;
import br.com.kurtis.financial.infra.ConfigProperties;
import br.com.kurtis.financial.nubank.model.Event;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static NubankTransaction newInstanceOf(Event event) {
        final String timeZoneId = ConfigProperties.getProperty("app.time_zone");
        final TimeZone zone = TimeZone.getTimeZone(timeZoneId);
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(DATETIME_FORMAT)
                .withZone(zone.toZoneId());
        final DateTimeFormatter stringFormatter = DateTimeFormatter.ofPattern("dd-MMM");

        final LocalDateTime date = LocalDateTime.parse(event.getTime(), formatter);
        final Integer dueDate = Integer.valueOf(ConfigProperties.getProperty("nubank.due_date"));
        final LocalDate nextDueDate = date.getDayOfMonth() > dueDate ? date.plusMonths(1).withDayOfMonth(dueDate).toLocalDate() : date.withDayOfMonth(dueDate).toLocalDate();

        final String description = '[' + date.format(stringFormatter) + "] " + event.getDescription();
        final String amount = event.getAmount().toString();
        final String valueString = new StringBuilder(amount).insert(amount.length() - 2, ".").toString();
        final BigDecimal value = new BigDecimal(valueString);

        return NubankTransaction.builder()
                .date(date)
                .dueDate(nextDueDate)
                .bank(Bank.NUBANK)
                .description(description)
                .value(value)
                .code(event.getId())
                .build();
    }
}
