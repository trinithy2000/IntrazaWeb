package com.intraza.rest.db.datos;

public class ResultadoEnvioPedido {
	
	public final static int SIN_ERROR = 0;
	public final static int CON_ERROR = -1;

	private int codigoError = SIN_ERROR;
	private String descripcionError = null;

	public ResultadoEnvioPedido(int codigoError, String descripcion) {
		this.codigoError = codigoError;
		this.descripcionError = descripcion;
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

	
}
