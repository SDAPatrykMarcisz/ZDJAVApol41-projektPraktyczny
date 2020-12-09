package pl.marcisz.patryk.kalkulator.walut.externalapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.marcisz.patryk.kalkulator.walut.service.CalculatorResponsePeriod;
import pl.marcisz.patryk.kalkulator.walut.service.Exchange;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalculatorHttpClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public CalculatorHttpClient() {
        this.httpClient = HttpClient.newBuilder().build();
        this.objectMapper = new ObjectMapper();
    }

    //parametry: waluta od, waluta do, data (jesli nie podana, to dzisiejsza)
    public Exchange getExchange(String currencyFrom, String currencyTo, LocalDate day) {
        return getApiResponse(currencyFrom, currencyTo, day.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .map(response -> {
                    Exchange exchange = new Exchange();
                    exchange.setCurrencyFrom(response.getBase());
                    exchange.setCurrencyTo(/*currencyTo*/
                            response.getRates().keySet().stream()
                                    .findFirst()
                                    .orElseThrow());
                    exchange.setExchangeDate(LocalDate.parse(
                            response.getDate(),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    );
                    exchange.setExchangeRate(response.getRates().get(currencyTo));
                    return exchange;
                }).orElseThrow(() -> new RuntimeException("problem podczas pobierania danych"));
    }

    private Optional<CalculatorResponse> getApiResponse(String currencyFrom, String currencyTo, String date) {
        String requestUrl = "https://api.exchangeratesapi.io/" + date
                + "?base=" + currencyFrom
                + "&symbols=" + currencyTo;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUrl))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            CalculatorResponse asObject = objectMapper.readValue(body, CalculatorResponse.class);
            return Optional.ofNullable(asObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Exchange> getExchangeList(String currencyFrom, String currencyTo, LocalDate startDate, LocalDate endDate) {
        Optional<CalculatorResponsePeriod> listaMap = getApiResponseFromPeriod(currencyFrom, currencyTo, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        listaMap.stream().map(response -> response.getRates()).map(resposne2 -> resposne2.entrySet()).forEach(maps->maps.stream().forEach(x -> x.getKey()));

        return getApiResponseFromPeriod(currencyFrom, currencyTo, startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .stream()
                .flatMap(httpResponseForPeriod -> {
                    String currFrom = httpResponseForPeriod.getBase(); //bierzemy bazowa walute z duzego jsona / obiektu
                    return httpResponseForPeriod.getRates().entrySet().stream() //dla kazdego wpisu w DUZEJ mapie (czyli przechodzimy na poziom przetwarzania rates)
                            .flatMap(day -> { //zeby miec 'wyplaszczona' strukture
                                String date = day.getKey(); //wyciagamy date
                                Stream<Exchange> exchangeStream = day.getValue().entrySet().stream() //dla kazdego wpisu w MALEJ (WEWNETRZNEJ) MAPIE
                                        .map(entry -> {
                                            String currTo = entry.getKey();
                                            return new Exchange(currFrom, currTo, entry.getValue(), LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //DLA KAZDEGO WPISU TWORZYMY
                                            //EXCHANGE BAZUJAC NA INFORMACJACH PRZECHOWYWANYCH WYZEJ
                                        });
                                return exchangeStream;
                            });
                }).collect(Collectors.toList());
    }

    private Optional<CalculatorResponsePeriod> getApiResponseFromPeriod(String currencyFrom, String currencyTo, String startDate, String endDate) {
        String requestUrl = "https://api.exchangeratesapi.io/history?start_at=" + startDate + "&end_at=" + endDate + "&base=" + currencyFrom + "&symbols=" + currencyTo;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUrl))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            CalculatorResponsePeriod asObject = objectMapper.readValue(body, CalculatorResponsePeriod.class);
            return Optional.ofNullable(asObject);
        } catch (Exception e) {
        }
        return Optional.empty();
    }

}
