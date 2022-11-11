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
public class RungeKutta implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;


	@FXML
	private TextArea ConsolaArea;

	@FXML
	private Button irInterInversa;


	  @FXML
	    private TextField funcionValor;

	    @FXML
	    private TextField xnValor;

	    @FXML
	    private TextField yoValor;

	    @FXML
	    private TextField xoValor;

	    @FXML
	    private TextField iteraciones;
	@FXML private Button GraficarButton;

	@FXML private LineChart<Double, Double> graph;
	@FXML private NumberAxis x;
	@FXML private NumberAxis y;



	ArrayList<Double> listaX = new ArrayList<>();
	ArrayList<Double> listaY = new ArrayList<>();

	String consolaTexto = "";
	public int decimales = 4;
	public int espaciado = 12;


	@FXML
	void irInterInversaAction(ActionEvent event) {

		try {
			root = FXMLLoader.load(getClass().getResource("/Vista/InterpolacionInversa.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			System.out.println("¡Metame en arroz!");
		}
	}


	@FXML private void GraficarAction(ActionEvent event) {
		listaX.clear();
		listaY.clear();
		consolaTexto = " ";
		ConsolaArea.clear();
		ejecutar();
		pintarGrafica(listaX, listaY);
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void pintarGrafica (ArrayList<Double> listaX, ArrayList<Double> listaY){
		ObservableList<XYChart.Series<Double, Double>> lineChartData = FXCollections.observableArrayList();
		LineChart.Series<Double, Double> series = new LineChart.Series<Double, Double>();
		
		for (int i = 0; i < listaX.size(); i++){
			series.getData().add(new XYChart.Data<Double, Double>(listaX.get(i), listaY.get(i)));}
		lineChartData.add(series);
		graph.setCreateSymbols(true);
		graph.setData(lineChartData);
		graph.createSymbolsProperty();
	}

	//---------------------------------------------------------------

    public void ejecutar() {

        double x0 = 0;
        double y0 = 1;
        double xn = 2;
       
        int n = 4;

        Funcion funcion = new Funcion("y*x^2-1.2*y");

        evaluar(funcion, x0, y0, xn, n);
    }
    
	public void ejecutar1() {

		String funcionText = funcionValor.getText();
		
		String valor = " ";
		
		valor = xoValor.getText();
		double xo = Double.parseDouble(valor);
		
		valor = yoValor.getText();
		double yo = Double.parseDouble(valor);
		
		valor = xoValor.getText();
		double xn = Double.parseDouble(valor);
		
		valor = xoValor.getText();
		int iteraciones = Integer.parseInt(valor);

		Funcion funcion = new Funcion(funcionText);

		evaluar(funcion, xo, yo, xn, iteraciones);
	}

	//*
	public double orden4(Funcion funcion, double x0, double y0, double h) {
		double F1 = funcion.evaluar(x0, y0);
		double F2 = funcion.evaluar(x0 + 0.5 * h, y0 + 0.5 * h * F1);
		double F3 = funcion.evaluar(x0 + 0.5 * h, y0 + 0.5 * h * F2);
		double F4 = funcion.evaluar(x0 + h, y0 + h * F3);

		
		String aux = ("" + redondear(y0) + "+ (" + redondear(h) + "* (" + redondear(F1) + "+ 2 * ( " + redondear(F2) + "+ " + redondear(F3) + ") + " + redondear(F4) + ") ) / 6 = " + redondear(y0 + (h * (F1 + 2 * (F2 + F3) + F4)) / 6));
		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "\n" + aux + "\n");
		return y0 + (h * (F1 + 2 * (F2 + F3) + F4)) / 6.0;
	}

	public double[] evaluar(Funcion funcion, double x0, double y0, double xn, int n) {

		String aux = ("\nXo: " + x0 + "\n" +
				"Yo: " + y0 + "\n" +
				"Xn: " + xn + "\n" +
				"Iteraciones: " + n + "\n" +
				"Funcion: " + funcion.toString() + "\n\n ");
		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "\n" + aux + "\n");
		

		double[] Y = new double[n + 1];
		double[] X = new double[n + 1];

		inicializar(X, x0);
		double h = (xn - x0) / n;
		Y[0] = y0;
		X[0] = x0;
		for (int i = 0; i < n; i++) {
			consolaTexto = ConsolaArea.getText();
			ConsolaArea.setText(consolaTexto +  "\n\n I= " + i + "\n");
			//System.out.println("I=" + i);
			y0 = orden4(funcion, x0, y0, h);
			x0 += h;
			X[i + 1] = x0;
			Y[i + 1] = y0;
		}

		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "\n\nResultado: \n\n");
//		System.out.println("Resultado:");
		reportarcoordenadas(X, Y);

		return Y;
	}

	//*
	public String redondear(double numero, int ancho, boolean centrado) {
		return this.redondear(numero, this.decimales, ancho, centrado);
	}

	//*
	public String redondear(double numero) {
		return this.redondear(numero, this.decimales, this.espaciado);
	}

	//*
	public String redondear(double numero, int decimales, int ancho) {
		return this.redondear(numero, decimales, ancho, false);
	}

	//*
	public String redondear(double numero, int decimales, int ancho, boolean centrado) {

		String cadena = "";
		try {

			BigDecimal bd = new BigDecimal(numero);
			bd = bd.setScale(decimales, BigDecimal.ROUND_HALF_UP);
			double resultado = bd.doubleValue();
			cadena = resultado + "";
		} catch (Exception e) {
			cadena = "0";
		}
		return this.redondearString(cadena, ancho, centrado);
	}

	//*
	public String redondearString(String cadena, int ancho, boolean centrado) {

		String str = "";
		String str2 = "";
		if (cadena.length() < ancho) {

			if (centrado) {
				int repetir = ancho - cadena.length();

				int mitad = Integer.parseInt(repetir / 2 + "");
				str = repetir(" ", mitad);
				str2 = repetir(" ", repetir - mitad);
				cadena = str2 + cadena + str;
			} else {

				int repetir = ancho - cadena.length();
				str = repetir(" ", repetir);
				cadena = cadena + str;
			}

		}

		return cadena;
	}

	//*
	public String redondear(String cadena, int ancho, boolean centrado) {
		return this.redondearString(cadena, ancho, centrado);
	}

	//*
	public String repetir(String str, int times) {
		String repetir = "";
		for (int i = 0; i < times; i++) {
			repetir = repetir + str;
		}
		return repetir;
	}

	//*
	public int getEspaciado(double[][] matriz) {
		int tamano = 0;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				int redondeado = (redondear(matriz[i][j])).length();
				tamano = redondeado > tamano ? redondeado : tamano;
			}
		}

		return tamano + 2;
	}


	//*
	public void reportarcoordenadas(double[] x, double[] y) {

		int n = x.length;
		double[][] coordenadas = new double[2][n];

		for (int j = 0; j < n; j++) {
			coordenadas[0][j] = x[j];
			listaX.add(coordenadas[0][j]);
			
			coordenadas[1][j] = y[j];
			listaY.add(coordenadas[1][j]);
		}

		int ancho = getEspaciado(coordenadas);
		System.out.println("");
		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "\n\n");
//		System.out.print("\n");

		for (int i = 0; i < coordenadas.length; i++) {
			if (i == 0) {
				consolaTexto = ConsolaArea.getText();
				ConsolaArea.setText(consolaTexto +  redondear("X", 5, true));
//				System.out.print(redondear("X", 5, true));
			} else {
				consolaTexto = ConsolaArea.getText();
				ConsolaArea.setText(consolaTexto +  redondear("f(x)", 5, true));
//				System.out.print(redondear("f(x)", 5, true));
			}

			reportarFilacoordenadas(coordenadas[i], ancho);
		}
		
		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "\n");
//		System.out.println("");

	}

	//*
	public void reportarFilacoordenadas(double[] fila, int ancho) {

		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "[");
//		System.out.print("[");
		int n = fila.length;
		for (int i = 0; i < n; i++) {
			double numero = fila[i];

			if (i != 0) {

//				System.out.print("|" + redondear(numero, ancho, true));
				consolaTexto = ConsolaArea.getText();
				ConsolaArea.setText(consolaTexto +  "|" + redondear(numero, ancho, true));

			} else {
				consolaTexto = ConsolaArea.getText();
				ConsolaArea.setText(consolaTexto +  redondear(numero, ancho, true) );
//				System.out.print(redondear(numero, ancho, true));
			}
		}

		consolaTexto = ConsolaArea.getText();
		ConsolaArea.setText(consolaTexto +  "]\n");
//		System.out.print("]");
//		System.out.print("\n");

	}

	//*
	public double[] inicializar(double[] n, double defecto) {

		for (int i = 0; i < n.length; i++) {
			n[i] = defecto;
		}
		return n;
	}

}