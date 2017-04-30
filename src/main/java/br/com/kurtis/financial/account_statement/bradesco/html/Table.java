package br.com.kurtis.financial.account_statement.bradesco.html;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import lombok.extern.java.Log;
import org.jsoup.nodes.Element;

import java.util.List;

@Data
@Builder
public class Table {
    @Singular
    private List<Tuple> tuples;

    public static Table instanceOf(@NonNull final Element body) {
        final TableBuilder builder = builder();
        for (Element element : body.children()) {
            builder.tuple(Tuple.instanceOf(element));
        }
        return builder.build();
    }
}
