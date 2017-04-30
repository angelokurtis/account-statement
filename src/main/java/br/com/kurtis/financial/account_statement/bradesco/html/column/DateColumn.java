package br.com.kurtis.financial.account_statement.bradesco.html.column;

import br.com.kurtis.financial.account_statement.bradesco.html.Column;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Value
@EqualsAndHashCode(callSuper = true)
public class DateColumn extends Column<LocalDate> {

    private static final DateTimeFormatter DATE_STRING_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
    private final LocalDate value;

    private DateColumn(@NonNull final Element htmlElement) {
        super(htmlElement);
        final Optional<String> htmlString = htmlString();
        value = htmlString.isPresent() ? LocalDate.parse(htmlString.get(), DATE_STRING_FORMATTER) : null;
    }

    public static DateColumn valueOf(@NonNull final Element htmlElement) {
        return new DateColumn(htmlElement);
    }
}
