package com.thd;

public class GeometricHelper {
	public static double[] getAngLin(double x1, double x2, double y1, double y2){
		double coeficienteangular = (y2-y1)/(x2-x1);
		double coeficientelinear = (-coeficienteangular * x1) + y1;
		double[] retorno = new double[2];
		retorno[0] = coeficienteangular;
		retorno[1] = coeficientelinear;
		return retorno;
	}
}
