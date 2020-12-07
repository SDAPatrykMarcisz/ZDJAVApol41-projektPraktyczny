package pl.marcisz.patryk.kalkulator.walut.view.fx;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangePanel extends HBox {
    private TextField currencyFrom;
    private TextField currencyTo;
    private TextField amount;
    private Button button;
    private Label result;

    public ExchangePanel(){
        this.currencyFrom = new TextField();
        this.currencyTo = new TextField();
        this.amount = new TextField();
        this.button = new Button();
        this.result = new Label();

        this.getChildren().addAll(currencyFrom, currencyTo, amount, button, result);

        this.amount.setOnKeyTyped(key -> {
            char keyAsChar = key.getCharacter().toCharArray()[0];
            boolean isDigitOrDot = Character.isDigit(keyAsChar) || keyAsChar == '.';
            if(!isDigitOrDot){
                String valueBeforeKeyPressed = this.amount.getText().substring(0, this.amount.getText().length() - 1);
                this.amount.setText(valueBeforeKeyPressed);
            }
        });

        this.button.setOnMouseClicked(clicked -> {
            String from = currencyFrom.getText();
            String to = currencyTo.getText();
            String value = amount.getText();
            BigDecimal exchange = HelloWorld.getService().exchange(from, to, LocalDate.now(), BigDecimal.valueOf(Double.parseDouble(value)));
            result.setText(exchange.toString());
        });
    }

}
