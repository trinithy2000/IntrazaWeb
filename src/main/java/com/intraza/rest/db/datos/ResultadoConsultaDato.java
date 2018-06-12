package com.intraza.rest.db.datos;

public class ResultadoConsultaDato {
	public final static int SIN_ERROR = 0;
	public final static int CON_ERROR = -1;

	private int codigoError = SIN_ERROR;
	private String descripcionError = null;
	private float dato = 0;
	private int dato2 = 0;

	public ResultadoConsultaDato(int codigoError, String descripcion, float dato, int dato2) {
		this.codigoError = codigoError;
		this.descripcionError = descripcion;
		this.dato = dato;
		this.dato2 = dato2;
	}

	public int getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(int codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public float getDato() {
		return dato;
	}

	public void setDato(float dato) {
		this.dato = dato;
	}

	public int getDato2() {
		return dato2;
	}

	public void setDato2(int dato2) {
		this.dato2 = dato2;
	}

}
