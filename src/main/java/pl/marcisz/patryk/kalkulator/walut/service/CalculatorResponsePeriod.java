package pl.marcisz.patryk.kalkulator.walut.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorResponsePeriod {
    private Map<String, Map<String, Double>> rates;
    private String start_at;
    private String base;
    private String end_at;
}