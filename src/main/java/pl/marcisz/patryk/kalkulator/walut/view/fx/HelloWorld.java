package pl.marcisz.patryk.kalkulator.walut.view.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.marcisz.patryk.kalkulator.walut.service.CurrencyService;

public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static final CurrencyService service = new CurrencyService();

    public static CurrencyService getService() {
        return service;
    }

    @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//
//        TextField textField = new TextField();
//
//        VBox root = new VBox();
//        for(int i=1; i<=10; i++){
//            HBox hBox = new HBox();
//            for(int j=0; j<i; j++){
//                hBox.getChildren().add(new Button(Integer.toString(j)));
//            }
//            root.getChildren().add(hBox);
//        }
//        root.getChildren().add(btn);
//        root.getChildren().add(textField);
            primaryStage.setScene(new Scene(new ExchangePanel(), 300, 250));
        primaryStage.show();
    }
}