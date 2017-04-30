package br.com.kurtis.financial.account_statement.bradesco.html.column;

import br.com.kurtis.financial.account_statement.bradesco.html.Column;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.java.Log;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.util.Optional;

@Value
@EqualsAndHashCode(callSuper = true)
public class AmountColumn extends Column<BigDecimal> {
    private final BigDecimal value;

    private AmountColumn(@NonNull final Element htmlElement) {
        super(htmlElement);
        final Optional<String> htmlString = htmlString();
        if (htmlString.isPresent()) {
            final String stringValue = htmlString.get().replace(".", "").replace(",", ".").replace(" ", "");
            this.value = new BigDecimal(stringValue);
        } else {
            this.value = BigDecimal.ZERO;
        }
    }

    public static AmountColumn valueOf(@NonNull final Element htmlElement) {
        return new AmountColumn(htmlElement);
    }
}
