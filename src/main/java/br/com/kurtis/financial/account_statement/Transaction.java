package br.com.kurtis.financial.account_statement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    private final Bank bank;
    private final String code;
    private final String description;
    private final LocalDate dueDate;
    private final BigDecimal value;
}
