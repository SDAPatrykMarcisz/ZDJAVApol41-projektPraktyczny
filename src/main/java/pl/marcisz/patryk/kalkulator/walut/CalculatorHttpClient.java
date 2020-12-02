package pl.marcisz.patryk.kalkulator.walut;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculatorHttpClient {

     private final HttpClient httpClient;
     private final ObjectMapper objectMapper;

     public CalculatorHttpClient(){
         this.httpClient = HttpClient.newBuilder().build();
         this.objectMapper = new ObjectMapper();
     }

    public double getEuroExchange(){
         return getEuroExchange(    "latest");
    }

    public double getEuroExchange(LocalDate localDate){
         return getEuroExchange(localDate.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

     //parametry: waluta od, waluta do, data (jesli nie podana, to dzisiejsza)
     private double getEuroExchange(String date){
         String requestUrl = "https://api.exchangeratesapi.io/" + date + "?base=EUR";
         HttpRequest request = HttpRequest.newBuilder()
                 .GET()
                 .uri(URI.create(requestUrl))
                 .build();

         try {
             HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
             String body = response.body();
             CalculatorResponse asObject = objectMapper.readValue(body, CalculatorResponse.class);
             return asObject.getRates().get("PLN");
         } catch (Exception e) {
             e.printStackTrace();
         }
         return 0.0;
     }

    public static void main(String[] args) {
        CalculatorHttpClient calculatorHttpClient = new CalculatorHttpClient();
        double euroExchange = calculatorHttpClient.getEuroExchange();
        System.out.println(euroExchange);
    }



}
