package pl.marcisz.patryk.kalkulator.walut.view.console;

import pl.marcisz.patryk.kalkulator.walut.externalapi.CalculatorHttpClient;
import pl.marcisz.patryk.kalkulator.walut.service.CurrencyService;
import pl.marcisz.patryk.kalkulator.walut.service.Exchange;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CurrencyService currencyService = new CurrencyService();
        currencyService.exchange("EUR", "PLN", LocalDate.now());
        CalculatorHttpClient calculatorHttpClient = new CalculatorHttpClient();
        List<Exchange> exchangeList = calculatorHttpClient.getExchangeList("EUR", "PLN", LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 5));
        System.out.println(exchangeList);
    }
}
