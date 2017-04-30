package br.com.kurtis.financial.account_statement.bradesco.html;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class Column<T> {

    private final Element htmlElement;

    private StringBuilder extractValues(@NonNull final List<Node> nodes, @NonNull final StringBuilder builder) {
        for (Node node : nodes) {
            final List<Node> childNodes = node.childNodes();
            if (childNodes.isEmpty()) {
                if (node instanceof TextNode) {
                    final String text = ((TextNode) node).text();
                    if (builder.length() > 0) builder.append(" - ");
                    builder.append(text);
                }
            } else {
                extractValues(childNodes, builder);
            }
        }
        return builder;
    }

    public abstract T getValue();

    protected Optional<String> htmlString() {
        final StringBuilder builder = new StringBuilder();
        extractValues(htmlElement.childNodes(), builder);
        final String htmlString = builder.toString();
        return htmlString.isEmpty() ? Optional.empty() : Optional.of(htmlString);
    }
}
