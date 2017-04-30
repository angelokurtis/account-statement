package br.com.kurtis.financial.account_statement.bradesco.html.column;

import br.com.kurtis.financial.account_statement.bradesco.html.Column;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.jsoup.nodes.Element;

import java.util.Optional;

@Value
@EqualsAndHashCode(callSuper = true)
public class TextColumn extends Column<String> {
    private final String value;

    private TextColumn(@NonNull final Element htmlElement) {
        super(htmlElement);
        final Optional<String> htmlString = htmlString();
        this.value = htmlString.isPresent() ? htmlString.get() : null;
    }

    public static TextColumn valueOf(@NonNull final Element htmlElement) {
        return new TextColumn(htmlElement);
    }
}
