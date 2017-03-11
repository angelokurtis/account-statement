package br.com.kurtis.financial;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountStatementApp {

    public static void main(String args[]) {
        final String accountStatementPath = args[0];
        log.debug(accountStatementPath);
    }
}
