package pl.marcisz.patryk.kalkulator.walut.service;

import pl.marcisz.patryk.kalkulator.walut.database.DatabaseDao;
import pl.marcisz.patryk.kalkulator.walut.database.DatabaseDaoHibernateImpl;
import pl.marcisz.patryk.kalkulator.walut.externalapi.CalculatorHttpClient;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CurrencyService {
    private final CalculatorHttpClient calculatorHttpClient;
    private final DatabaseDao databaseDao;

    public CurrencyService(){
        this.calculatorHttpClient = new CalculatorHttpClient();
        this.databaseDao = new DatabaseDaoHibernateImpl();
    }

    public BigDecimal exchange(String currencyFrom, String currencyTo, LocalDate day) {
        return exchange(currencyFrom, currencyTo, day, BigDecimal.ONE);
    }

    public BigDecimal exchange(String currencyFrom, String currencyTo, LocalDate day, BigDecimal amount) {
        Exchange exchangeInfo = getFromDatabaseOrExternalApi(currencyFrom, currencyTo, day);
        return amount.multiply(BigDecimal.valueOf(exchangeInfo.getExchangeRate()));
    }

    private Exchange getFromDatabaseOrExternalApi(String currencyFrom, String currencyTo, LocalDate day) {
        Exchange data = databaseDao.getByCriteria(currencyFrom, currencyTo, day);
        if(data == null){
            data = calculatorHttpClient.getExchange(currencyFrom, currencyTo, day);
            databaseDao.save(data);
        }
        return data;
    }
}
