package pl.marcisz.patryk.kalkulator.walut.database;

import pl.marcisz.patryk.kalkulator.walut.service.Exchange;

import java.time.LocalDate;

public interface DatabaseDao {

    Exchange getByCriteria(String currencyFrom, String currencyTo, LocalDate day);

    void save(Exchange data);

}
