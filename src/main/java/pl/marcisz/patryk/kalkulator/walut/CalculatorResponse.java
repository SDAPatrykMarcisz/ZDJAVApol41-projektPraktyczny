package pl.marcisz.patryk.kalkulator.walut;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CalculatorResponse {
    private Map<String, Double> rates;
    private String base;
    private String date;

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

/*
{
  "rates": {
    "CAD": 1.5619,
    "MYR": 4.9199
  },
  "base": "EUR",
  "date": "2020-12-02"
}
 */
