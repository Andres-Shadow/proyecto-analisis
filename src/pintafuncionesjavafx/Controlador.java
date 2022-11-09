package pintafuncionesjavafx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * @author Richard
 */
public class Controlador implements Initializable {
	

    @FXML
    private TextField valorY;

    @FXML
    private TextField valorX;

    @FXML
    private Button PuntoButton;
    

    @FXML
    void PuntoAction(ActionEvent event) {

    }

    
    @FXML private TextField rangoMin;
    @FXML private TextField rangoMax;
    @FXML private Button GraficarButton;

    @FXML private LineChart<Double, Double> graph;
    @FXML private NumberAxis x;
    @FXML private NumberAxis y;
    
    int rango = 15;
    
    @FXML private void GraficarAction(ActionEvent event) {
        pintarGrafica(rango * -1, rango, 2);  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    private void pintarGrafica (int min, int max, int grado){
        
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>();
        series.setName("Funcion");
        
        //Funcion
        for (double i = min; i<max; i=i+0.1){
            series.getData().add(new XYChart.Data<Double, Double>(i, Math.pow(i, grado)));
        }
        
        //Punto
        series.getData().add(new XYChart.Data<Double, Double>( Double.parseDouble(valorX.getText()), Double.parseDouble(valorX.getText())) );
        lineChartData.add(series);
        graph.setCreateSymbols(true);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }
}