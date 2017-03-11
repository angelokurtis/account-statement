package br.com.kurtis.financial.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Transaction {
    private LocalDate dueDate;
    private Bank bank;
    private String description;
    private BigDecimal value;
    private String code;
}
