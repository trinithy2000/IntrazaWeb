package com.intraza.rest.db.datos;

import org.apache.commons.codec.binary.Base64;

public class JsonLineaPedido {

	private int idPrepedido = 0;
	private int idArticulo = 0;
	private String nombreArticulo = null;
	private float cantidadKg = 0;
	private int cantidadUd = 0;
	private float precio = 0;
	private String observaciones = null;
	boolean fijarPrecio = false;
	boolean suprimirPrecio = false;
	boolean fijarArticulo = false;
	boolean fijarObservaciones = false;

	public JsonLineaPedido(int idPrepedido, int idArticulo, String nombreArticulo, float cantidadKg, int cantidadUd, float precio, String observaciones, boolean fijarPrecio,
			boolean suprimirPrecio, boolean fijarArticulo, boolean fijarObservaciones) {
		this.idPrepedido = idPrepedido;
		this.idArticulo = idArticulo;
		this.nombreArticulo = nombreArticulo;
		this.cantidadKg = cantidadKg;
		this.cantidadUd = cantidadUd;
		this.precio = precio;
		this.observaciones = decode(observaciones);
		this.fijarPrecio = fijarPrecio;
		this.suprimirPrecio = suprimirPrecio;
		this.fijarArticulo = fijarArticulo;
		this.fijarObservaciones = fijarObservaciones;
	}

	private String decode(String observaciones) {
		String retorno = "";
		if (observaciones != null && !observaciones.isEmpty()) {
			byte[] data = Base64.decodeBase64(observaciones);
			retorno = new String(data);
		}
		// TODO Auto-generated method stub
		return retorno;
	}

	public JsonLineaPedido() {
		super();
	}

	public int getIdPrepedido() {
		return idPrepedido;
	}

	public void setIdPrepedido(int idPrepedido) {
		this.idPrepedido = idPrepedido;
	}

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public float getCantidadKg() {
		return cantidadKg;
	}

	public void setCantidadKg(float cantidadKg) {
		this.cantidadKg = cantidadKg;
	}

	public int getCantidadUd() {
		return cantidadUd;
	}

	public void setCantidadUd(int cantidadUd) {
		this.cantidadUd = cantidadUd;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isFijarPrecio() {
		return fijarPrecio;
	}

	public void setFijarPrecio(boolean fijarPrecio) {
		this.fijarPrecio = fijarPrecio;
	}

	public boolean isSuprimirPrecio() {
		return suprimirPrecio;
	}

	public void setSuprimirPrecio(boolean suprimirPrecio) {
		this.suprimirPrecio = suprimirPrecio;
	}

	public boolean isFijarArticulo() {
		return fijarArticulo;
	}

	public void setFijarArticulo(boolean fijarArticulo) {
		this.fijarArticulo = fijarArticulo;
	}

	public boolean isFijarObservaciones() {
		return fijarObservaciones;
	}

	public void setFijarObservaciones(boolean fijarObservaciones) {
		this.fijarObservaciones = fijarObservaciones;
	}

}
