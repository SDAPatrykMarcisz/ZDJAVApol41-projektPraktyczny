package pl.marcisz.patryk.kalkulator.walut.view.console;

import pl.marcisz.patryk.kalkulator.walut.service.CurrencyService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CurrencyService currencyService = new CurrencyService();
        currencyService.exchange("EUR", "PLN", LocalDate.now());
    }
}
