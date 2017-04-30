package br.com.kurtis.financial.account_statement;

import lombok.NonNull;

import java.io.IOException;
import java.util.List;

public interface AccountService {
    List<Transaction> transactionsFrom(@NonNull final String path) throws IOException;
}
