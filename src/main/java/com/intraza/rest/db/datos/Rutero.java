package com.intraza.rest.db.datos;

public class Rutero {

	private int idArticulo = 0;
	private String codigoArticulo = null;
	private int idCliente = 0;
	private String fechaPedido = null;
	private int unidades = 0;
	private float peso = 0;
	private int unidadesTotalAnio = 0;
	private float pesoTotalAnio = 0;
	private float precio = 0;
	private float precioCliente = 0;
	private byte[] observacionesItem = null;

	public Rutero(int idArticulo, String codigoArticulo, int idCliente, String fechaPedido, int unidades, float peso, int unidadesTotalAnio, float pesoTotalAnio, float precio,
			float precioCliente, byte[] observaciones) {
		this.idArticulo = idArticulo;
		this.codigoArticulo = codigoArticulo;
		this.idCliente = idCliente;
		this.fechaPedido = fechaPedido;
		this.unidades = unidades;
		this.peso = peso;
		this.unidadesTotalAnio = unidadesTotalAnio;
		this.pesoTotalAnio = pesoTotalAnio;
		this.precio = precio;
		this.precioCliente = precioCliente;
		this.observacionesItem = observaciones;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getCodigoArticulo() {
		return codigoArticulo;
	}

	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(String fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public int getUnidadesTotalAnio() {
		return unidadesTotalAnio;
	}

	public void setUnidadesTotalAnio(int unidadesTotalAnio) {
		this.unidadesTotalAnio = unidadesTotalAnio;
	}

	public float getPesoTotalAnio() {
		return pesoTotalAnio;
	}

	public void setPesoTotalAnio(float pesoTotalAnio) {
		this.pesoTotalAnio = pesoTotalAnio;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getPrecioCliente() {
		return precioCliente;
	}

	public void setPrecioCliente(float precioCliente) {
		this.precioCliente = precioCliente;
	}

	public byte[] getObservacionesItem() {
		return observacionesItem;
	}

	public void setObservacionesItem(byte[] observacionesItem) {
		this.observacionesItem = observacionesItem;
	}

}
