package Controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.ConsoleHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Richard
 */
public class InterpolacionInversa implements Initializable {
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	    @FXML
	    private TextField buscarX;

	    @FXML
	    private Button Buscar;

	    @FXML
	    private TextArea ConsolaArea;

	    @FXML
	    private Button limpiar;

	    @FXML
	    private Button irRungeKutta;




	

    @FXML private TextField valorY;

    @FXML private TextField valorX;

    @FXML private Button PuntoButton;

    
    @FXML private TextField rangoMin;
    @FXML private TextField rangoMax;
    @FXML private Button GraficarButton;

    @FXML private LineChart<Double, Double> graph;
    @FXML private NumberAxis x;
    @FXML private NumberAxis y;
   
   
    
    ArrayList<Double> listaX = new ArrayList<>();
    ArrayList<Double> listaY = new ArrayList<>();
    
    String consolaTexto = "";
    

    @FXML
    void irRungeKuttaAction(ActionEvent event) {

    	try {
    		root = FXMLLoader.load(getClass().getResource("/Vista/RungeKutta.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    		
    	} catch (IOException e) {
    		System.out.println("¡Metame en arroz!");
    	}
    }

    
    @FXML private void GraficarAction(ActionEvent event) {
    
        pintarGrafica(PuntoMenor(listaY), PuntoMayor(listaY), listaX.size());  
    }
    

	@FXML
    void PuntoAction(ActionEvent event) {
    	String textX = valorX.getText();
    	Double nuevaX = Double.parseDouble(textX);
    	
    	String textY = valorY.getText();
    	Double nuevaY = Double.parseDouble(textY);
    	
    	listaX.add(nuevaX);
    	listaY.add(nuevaY);
    	
    	valorX.clear();
    	valorY.clear();
    	
    	consolaTexto = ConsolaArea.getText();
    	ConsolaArea.setText("\nAñadido: (" + nuevaX + " , " + nuevaY + ")\n" + consolaTexto);
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void pintarGrafica (Double min, Double max, int puntos){
        
        ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
        LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>();
        series.setName("Grado: " + (puntos - 1));
        
    	consolaTexto = ConsolaArea.getText();
    	ConsolaArea.setText( "\n\nGRAFICANDO\n\n" + consolaTexto);
        

        for (double i = min; i < max; i=i+0.1){
            series.getData().add(new XYChart.Data<Double, Double>(i, (double) calcularFX(i, puntos, listaX, listaY)));
        }
        
        lineChartData.add(series);
        graph.setCreateSymbols(true);
        graph.setData(lineChartData);
        graph.createSymbolsProperty();
    }
    
    @FXML
    void limpiarAction(ActionEvent event) {
    	listaX.clear();
    	listaY.clear();
    	consolaTexto = " ";
    	pintarGrafica(10.0, 10.0, listaX.size());
    	ConsolaArea.clear();
    }

	private float calcularFX(double x, int n, ArrayList<Double> ax, ArrayList<Double> ay) {
		Double num,den;
		float term =0f;
	    
	    
	    for(int i=0;i<n;i++) {
	        num=1.0;
	        den=1.0;
	        for(int j=0;j<n;j++) {
	            if(j!=i) {
	                num=(num*(x-ax.get(j)));
	                den = den*(ax.get(i)-ax.get(j));    
	            }
	        }
	        term += (num/den)*ay.get(i);
	    }
	    
    	consolaTexto = ConsolaArea.getText();
    	ConsolaArea.setText("\nX: " + x + " Y: " + term + "\n" + consolaTexto);
		return term;
	}
	
    @FXML
    void BuscarAction(ActionEvent event) {
    	String textX = buscarX.getText();
    	Double nuevaX = Double.parseDouble(textX);
    	
    	float fx = calcularFX(nuevaX, listaX.size(), listaX, listaY);
    	
    	consolaTexto = ConsolaArea.getText();
    	ConsolaArea.setText("\n\nResultado: Si X = " + nuevaX + " F(x) = " + fx + "\n\n" + consolaTexto);
    	
    }
	
    private Double PuntoMenor(ArrayList<Double> arr) {
        Collections.sort(arr);
        Double min = arr.get(0);
        min = min - 5;
		return min;
	}
    private Double PuntoMayor(ArrayList<Double> arr) {
        int n = arr.size();
        int i;
        Collections.sort(arr);
        Double max = arr.get(n-1);
        max = max + 5;
		return max;
	}
    
}