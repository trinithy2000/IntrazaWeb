package com.intraza.rest.db.datos;

import java.util.ArrayList;
import org.apache.commons.codec.binary.Base64;

public class JsonPedido {
	private int idPedido = 0;
	private int idCliente = 0;
	private String cliente = null;
	private int diaFechaPedido = 0;
	private int mesFechaPedido = 0;
	private int anioFechaPedido = 0;
	private int diaFechaEntrega = 0;
	private int mesFechaEntrega = 0;
	private int anioFechaEntrega = 0;
	private String observaciones = null;
	private boolean fijar = false;
	private int descuento = 0;
	private ArrayList<JsonLineaPedido> lineasPedido = null;

	/**
	 * Constructor.
	 * 
	 * @param idPedido
	 * @param idCliente
	 * @param cliente
	 * @param diaFechaPedido
	 * @param mesFechaPedido
	 * @param anioFechaPedido
	 * @param diaFechaEntrega
	 * @param mesFechaEntrega
	 * @param anioFechaEntrega
	 * @param observaciones
	 * @param fijarObservaciones
	 * @param descuentoEspecial
	 */
	public JsonPedido(int idPedido, int idCliente, String cliente, int diaFechaPedido, int mesFechaPedido, int anioFechaPedido, int diaFechaEntrega, int mesFechaEntrega,
			int anioFechaEntrega, String observaciones, boolean fijar, int descuento) {
		this.idPedido = idPedido;
		this.idCliente = idCliente;
		this.cliente = cliente;
		this.diaFechaPedido = diaFechaPedido;
		this.mesFechaPedido = mesFechaPedido;
		this.anioFechaPedido = anioFechaPedido;
		this.diaFechaEntrega = diaFechaEntrega;
		this.mesFechaEntrega = mesFechaEntrega;
		this.anioFechaEntrega = anioFechaEntrega;
		this.observaciones = decode(observaciones);
		this.fijar = fijar;
		this.descuento = descuento;
		this.lineasPedido = new ArrayList<JsonLineaPedido>();
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

	public JsonPedido() {
		super();
	}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public int getDiaFechaPedido() {
		return diaFechaPedido;
	}

	public void setDiaFechaPedido(int diaFechaPedido) {
		this.diaFechaPedido = diaFechaPedido;
	}

	public int getMesFechaPedido() {
		return mesFechaPedido;
	}

	public void setMesFechaPedido(int mesFechaPedido) {
		this.mesFechaPedido = mesFechaPedido;
	}

	public int getAnioFechaPedido() {
		return anioFechaPedido;
	}

	public void setAnioFechaPedido(int anioFechaPedido) {
		this.anioFechaPedido = anioFechaPedido;
	}

	public int getDiaFechaEntrega() {
		return diaFechaEntrega;
	}

	public void setDiaFechaEntrega(int diaFechaEntrega) {
		this.diaFechaEntrega = diaFechaEntrega;
	}

	public int getMesFechaEntrega() {
		return mesFechaEntrega;
	}

	public void setMesFechaEntrega(int mesFechaEntrega) {
		this.mesFechaEntrega = mesFechaEntrega;
	}

	public int getAnioFechaEntrega() {
		return anioFechaEntrega;
	}

	public void setAnioFechaEntrega(int anioFechaEntrega) {
		this.anioFechaEntrega = anioFechaEntrega;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isFijar() {
		return fijar;
	}

	public void setFijar(boolean fijar) {
		this.fijar = fijar;
	}

	public int getDescuento() {
		return descuento;
	}

	public void setDescuento(int descuento) {
		this.descuento = descuento;
	}

	public ArrayList<JsonLineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(ArrayList<JsonLineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}

}
