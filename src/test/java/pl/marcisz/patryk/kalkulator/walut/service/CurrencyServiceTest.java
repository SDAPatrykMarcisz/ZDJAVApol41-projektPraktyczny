package pl.marcisz.patryk.kalkulator.walut.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcisz.patryk.kalkulator.walut.database.DatabaseDao;
import pl.marcisz.patryk.kalkulator.walut.externalapi.CalculatorHttpClient;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private CalculatorHttpClient calculatorHttpClient;
    @Mock
    private DatabaseDao databaseDao;
    @InjectMocks
    private CurrencyService currencyService;


    @Test
    void getFromDatabaseIfExists(){
        //given
        String currencyFrom = "PLN";
        String currencyTo = "EUR";
        LocalDate testDay = LocalDate.of(2020,12,8);

        Exchange exchange = new Exchange();
        exchange.setCurrencyTo(currencyTo);
        exchange.setCurrencyFrom(currencyFrom);
        exchange.setExchangeDate(testDay);
        exchange.setExchangeRate(20.2);

        Mockito.when(databaseDao.getByCriteria(currencyFrom, currencyTo, testDay))
                .thenReturn(exchange);

        //when
        BigDecimal exchangeResult = currencyService.exchange(currencyFrom, currencyTo, testDay);

        //then
        assertEquals(BigDecimal.valueOf(20.2), exchangeResult);

        Mockito.verify(databaseDao, Mockito.times(0)).save(any());
        Mockito.verify(calculatorHttpClient, Mockito.times(0)).getExchange(any(), any(), any());

    }
}