package org.example;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import servise.BackpropagationAlgorithm;

public class PrimaryController {

    @FXML
    private Button trainingStartId;

    @FXML
    private Button car1butId;

    @FXML
    private Button car2butId;

    @FXML
    private Button moto1butId;

    @FXML
    private Button moto2butId;

    @FXML
    private Button ship1butId;

    @FXML
    private Button ship2butId;

    @FXML
    private Button airplane1butId;

    @FXML
    private Button airplane2butId;

    @FXML
    private Button train1butId;

    @FXML
    private Button train2butId;

    @FXML
    private TextField epochId;

    @FXML
    private TextField nuId;

    @FXML
    private TextField carPid;

    @FXML
    private TextField motoPid;

    @FXML
    private TextField shipPid;

    @FXML
    private TextField airplanePid;

    @FXML
    private TextField trainPid;

    @FXML
    private TextField statusId;

    @FXML
    private GridPane gridId;

    private int sizeImg = 25;
    private int rectSize = 10;


    @FXML
    void initialize() throws IOException {

        BackpropagationAlgorithm backpropagationAlgorithm = new BackpropagationAlgorithm();
        backpropagationAlgorithm.initTrainingSamples(sizeImg);
        trainingStartId.setOnAction(actionEvent -> {
            backpropagationAlgorithm.setEpoch(Integer.parseInt(epochId.getText()));
            backpropagationAlgorithm.setLearnRate(Double.parseDouble(nuId.getText()));
            backpropagationAlgorithm.training();
            statusId.appendText("success");
        });


        car1butId.setOnAction(actionEvent -> {
//            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(3);
            recognition(3, backpropagationAlgorithm);
        });

        car2butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(2);
            recognition(2, backpropagationAlgorithm);
        });

        airplane1butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(0);
            recognition(0, backpropagationAlgorithm);
        });

        airplane2butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(1);
            recognition(1, backpropagationAlgorithm);
        });

        moto1butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(4);
            recognition(4, backpropagationAlgorithm);
        });

        moto2butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(5);
            recognition(5, backpropagationAlgorithm);
        });

        ship1butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(6);
            recognition(6, backpropagationAlgorithm);
        });

        ship2butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(7);
            recognition(7, backpropagationAlgorithm);
        });


        train1butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(1);
            recognition(8, backpropagationAlgorithm);
        });

        train2butId.setOnAction(actionEvent -> {
            renderGridPane(sizeImg);
            backpropagationAlgorithm.recognition(1);
            recognition(9, backpropagationAlgorithm);
        });

    }

    private void recognition(int numImg, BackpropagationAlgorithm backpropagationAlgorithm){
        airplanePid.clear();
        carPid.clear();
        motoPid.clear();
        shipPid.clear();
        trainPid.clear();
        renderGridPane(sizeImg);
        double[] vectorOfInputValues =  backpropagationAlgorithm.getTrainingSamples().get(numImg).getVectorOfInputValues();
        for (int i = 0; i < sizeImg; i++) {
            for (int j = 0; j < sizeImg; j++) {
                Label label = new Label();
                label.setStyle("-fx-background-color: #000000;");
                Rectangle rectangle = new Rectangle();
                rectangle.setStyle("-fx-background-color: #000000;");
                rectangle.setWidth(rectSize);
                rectangle.setHeight(rectSize);
                GridPane.setHgrow(label, Priority.SOMETIMES);
                GridPane.setVgrow(label, Priority.SOMETIMES);
                if (vectorOfInputValues[i * sizeImg + j] == 1){
                    gridId.add(rectangle,j,i);
                }
            }
        }
        double[] vectorOfOutputValues = backpropagationAlgorithm.getActual();
        NumberFormat formatter = new DecimalFormat("#0.0000000");
        airplanePid.appendText(formatter.format(vectorOfOutputValues[0]));
        carPid.appendText(formatter.format(vectorOfOutputValues[1]));
        motoPid.appendText(formatter.format(vectorOfOutputValues[2]));
        shipPid.appendText(formatter.format(vectorOfOutputValues[3]));
        trainPid.appendText(formatter.format(vectorOfOutputValues[4]));

    }

    private void renderGridPane(int size) {
        gridId.setGridLinesVisible(false);
        gridId.getRowConstraints().clear();
        gridId.getColumnConstraints().clear();
        gridId.getChildren().clear();

        //  стоим сетку с одинаковой шириной столбцов
        for (int i = 0; i < size; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(-1, -1, (1.0 / size * 100), Priority.NEVER, HPos.CENTER, false);
            columnConstraints.setPercentWidth(1.0 / size * 100);
            RowConstraints rowConstraints = new RowConstraints(-1, -1, (1.0 / size * 100), Priority.NEVER, VPos.CENTER, false);
            rowConstraints.setPercentHeight(1.0 / size * 100);
            gridId.getColumnConstraints().add(columnConstraints);
            gridId.getRowConstraints().add(rowConstraints);
            /*
            v - минимальная высота
            v1 - предпочтительная ширина
            v2 - максимальная ширина
            Priority - параметры расширения столбца
            HPos - выравнивание по горизонтали
            false - запрещает растяжение стобцов
            */
        }
        gridId.setGridLinesVisible(true);
    }
}
