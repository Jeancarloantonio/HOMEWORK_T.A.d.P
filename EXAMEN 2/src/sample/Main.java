/* Aranda Cen Jeancarlo Antonio IU4 ISC 17390318 Ejercicio 2 */
package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class Main extends Application {
    private  Thread thread = null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        final int[] sumaDados = new int[1];
        final boolean[] gano = {false};
        final int[] tiempoRestante = new int[1];

        AnchorPane root = new AnchorPane();

        Label lbl2 = new Label("Suma de puntos:");
        lbl2.setLayoutY(30);

        Label lbl3 = new Label();
        lbl3.setLayoutY(120);

        Label lbl4 = new Label();
        lbl4.setLayoutY(150);

        TextField textField = new TextField();
        textField.setLayoutY(30);


        Button btn1 = new Button("Tirar");
        btn1.setDisable(true);
        btn1.setOnMousePressed((MouseEvent evt)->{
            int dado1 = (int)(Math.random()*6)+1;
            int dado2 = (int)(Math.random()*6)+1;

            sumaDados[0] = dado1+dado2;

            textField.setText(String.valueOf(sumaDados[0]));

            if(sumaDados[0] == 7){
                gano[0] = true;
            }
        });


        Button btn2 = new Button("Iniciar");
        btn2.setLayoutY(80);
        btn2.setOnMousePressed((MouseEvent evt)->{
            btn1.setDisable(false);
            if( this.thread == null){
                lbl4.setText("");
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        int tiempo = 60;
                        while(tiempo >= 0 && !gano[0]){
                            int finalTiempo = tiempo;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lbl3.setText(String.valueOf(finalTiempo));
                                }
                            });

                            Thread.sleep(100);
                            tiempoRestante[0] = tiempo;
                            tiempo--;
                        }

                        if(!gano[0]){
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lbl4.setText("Perdiste");
                                }
                            });
                        } else {
                            int finalTiempo1 = tiempo;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    lbl4.setText("Te falto: "+(finalTiempo1 +1)+"segundos");
                                }
                            });

                        }

                        thread = null;
                        btn1.setDisable(true);
                        gano[0] = false;
                        return null;
                    }
                };
                this.thread = new Thread(task);
                this.thread.setDaemon(true);
                this.thread.start();
            }
        });

        root.getChildren().add(lbl2);
        root.getChildren().add(lbl3);
        root.getChildren().add(lbl4);
        root.getChildren().add(textField);
        root.getChildren().add(btn1);
        root.getChildren().add(btn2);


        primaryStage.setTitle("Tira el dado hasta que caiga 7");
        primaryStage.setScene(new Scene(root, 250, 200));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}