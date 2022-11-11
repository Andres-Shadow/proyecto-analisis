/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import de.congrace.exp4j.*;
import de.congrace.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerson
 */
public class Funcion {

    protected ExpressionBuilder parser;
    private String funcion;

    public Funcion() {
    }

    public Funcion(String funcion) {
    	this.funcion = funcion;
        System.out.println("");
        System.out.println("Funcion: " + funcion);

        this.parser = new ExpressionBuilder(funcion);
    }

    //*
    public double evaluar(double x, double y) {
        double resultado = 0;
        try {
            resultado = evaluar(x, y, false);
        } catch (Exception ex) {
        }

        return resultado;
    }

    //*
    public double evaluar(double x, double y, boolean error) throws UnknownFunctionException, UnparsableExpressionException {
        double resultado = 0;
        Calculable calc = parser.withVariable("x", x).withVariable("y", y).build();
        resultado = calc.calculate();



        return resultado;
    }

	@Override
	public String toString() {
		return funcion;
	}

    
}
