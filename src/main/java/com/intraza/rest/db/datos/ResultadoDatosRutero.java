package com.intraza.rest.db.datos;

public class ResultadoDatosRutero {

	public final static int SIN_ERROR = 0;
	public final static int CON_ERROR = -1;

	private int codigoError = SIN_ERROR;
	private String descripcionError = null;
	private float tarifaCliente = 0;
	private float pesoTotalAnio = 0;
	private int unidadesTotalAnio = 0;

	public ResultadoDatosRutero(int codigoError, String descripcion, float tarifaCliente, float pesoTotalAnio,
			int unidadesTotalAnio) {
		this.codigoError = codigoError;
		this.descripcionError = descripcion;
		this.tarifaCliente = tarifaCliente;
		this.pesoTotalAnio = pesoTotalAnio;
		this.unidadesTotalAnio = unidadesTotalAnio;
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

	public float getTarifaCliente() {
		return tarifaCliente;
	}

	public void setTarifaCliente(float tarifaCliente) {
		this.tarifaCliente = tarifaCliente;
	}

	public float getPesoTotalAnio() {
		return pesoTotalAnio;
	}

	public void setPesoTotalAnio(float pesoTotalAnio) {
		this.pesoTotalAnio = pesoTotalAnio;
	}

	public int getUnidadesTotalAnio() {
		return unidadesTotalAnio;
	}

	public void setUnidadesTotalAnio(int unidadesTotalAnio) {
		this.unidadesTotalAnio = unidadesTotalAnio;
	}

}
