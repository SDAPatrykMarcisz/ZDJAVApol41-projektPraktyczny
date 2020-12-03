package pl.marcisz.patryk.kalkulator.walut.database;

import pl.marcisz.patryk.kalkulator.walut.service.Exchange;

import java.time.LocalDate;

public class DatabaseDaoHibernateImpl implements DatabaseDao {
    @Override
    public Exchange getByCriteria(String currencyFrom, String currencyTo, LocalDate day) {
        return null;
    }

    @Override
    public void save(Exchange data) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
