package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Controller {
    int cos;


    int elementsNumber;
    int opinionsNumber;
    int dimensionsNumber;
    char el;

    @FXML
    TextField elements;
    @FXML
    TextField dimNumber;
    @FXML
    TextField opinions;
    @FXML
    Button startButton;
    @FXML
    Button exportButton;
    @FXML
    Button refreshChartButton;
    @FXML
    LineChart<Integer, Integer> lineChart;
    File file;
    WritableImage image;

    @FXML
    void initialize(){
        lineChart.setTitle("Ilość zablokowanych elementów");
        exportButton.setText("Eksportuj");
        refreshChartButton.setText("Odśwież wykres");
        startButton.setText("Start");

    }

    @FXML
    void setElementsNumber(){
        elementsNumber = Integer.parseInt(elements.getText());
    }
    @FXML
    void setOpinionsNumber(){
        opinionsNumber = Integer.parseInt(opinions.getText());
    }
    @FXML
    void setDimNumber(){
        dimensionsNumber = Integer.parseInt(dimNumber.getText());
    }

    @FXML
    public void InitChart(){
       // Simulation symulacja = new Simulation(elementsNumber,opinionsNumber);
        Simulation symulacja = new Simulation(1000,2);
        ObservableList<XYChart.Data<Integer, Integer>> lineChartData;
        lineChartData = FXCollections.observableArrayList();
        ObservableList<XYChart.Series<Integer, Integer>> lineChartSeries;
        lineChartSeries= FXCollections.observableArrayList();
        int y;

        for(int t=0; t<elementsNumber; t++)
        {
            y=symulacja.isolationsTable[t];
            lineChartData.add(new LineChart.Data(t,y));
        }
        lineChartSeries.add(new LineChart.Series(lineChartData));
        lineChart.setData(lineChartSeries);
        lineChart.setCreateSymbols(false);
    }

    @FXML
    void exportAction() {
        image = lineChart.snapshot(new SnapshotParameters(), null);
        file = new File("chart.png");
        try {

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

        } catch (IOException e) {

        }
    }



}
