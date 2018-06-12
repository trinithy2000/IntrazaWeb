package com.intraza.rest.db.datos;

public class ResultadoDatosRuteroTarifa {

	private int cliente = 0;
	private String codigo = "";
	private String fecha = "";
	private float tarifaCliente = 0;
	private float pesoTotalAnio = 0;
	private int unidadesTotalAnio = 0;
	private byte[] observaciones = null;

	public ResultadoDatosRuteroTarifa(int cliente, String codigo, String fecha, byte[] observaciones, ResultadoDatosRutero rutero) {
		this.setCliente(cliente);
		this.setCodigo(codigo);
		this.setFecha(fecha);
		this.tarifaCliente = rutero.getTarifaCliente();
		this.pesoTotalAnio = rutero.getPesoTotalAnio();
		this.unidadesTotalAnio = (int) rutero.getUnidadesTotalAnio();
		this.observaciones = observaciones;
	}

	public ResultadoDatosRuteroTarifa(int cliente, String codigo, ResultadoDatosRutero rutero) {
		this.setCliente(cliente);
		this.setCodigo(codigo);
		this.tarifaCliente = rutero.getTarifaCliente();
		this.pesoTotalAnio = rutero.getPesoTotalAnio();
		this.unidadesTotalAnio = (int) rutero.getUnidadesTotalAnio();
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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

	public byte[] getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(byte[] observaciones) {
		this.observaciones = observaciones;
	}

}
