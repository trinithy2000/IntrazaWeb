package com.intraza.rest.db.datos;

public class Prepedido {

	private String id = null;
	private String observaciones = null;
	private Integer diaPedido = null;
	private Integer mesPedido = null;
	private Integer anioPedido = null;
	private Integer diaEntrega = null;
	private Integer mesEntrega = null;
	private Integer anioEntrega = null;
	private Integer descuento = null;
	
	
	

	public Prepedido(String id, String observaciones, int diaPedido, int mesPedido, int anioPedido, int diaEntrega,
			int mesEntrega, int anioEntrega, int descuento) {
		this.id = id;
		this.observaciones = observaciones;
		this.diaPedido = diaPedido;
		this.mesPedido = mesPedido;
		this.anioPedido = anioPedido;
		this.diaEntrega = diaEntrega;
		this.mesEntrega = mesEntrega;
		this.anioEntrega = anioEntrega;
		this.descuento = descuento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Integer getDiaPedido() {
		return diaPedido;
	}

	public void setDiaPedido(Integer diaPedido) {
		this.diaPedido = diaPedido;
	}

	public Integer getMesPedido() {
		return mesPedido;
	}

	public void setMesPedido(Integer mesPedido) {
		this.mesPedido = mesPedido;
	}

	public Integer getAnioPedido() {
		return anioPedido;
	}

	public void setAnioPedido(Integer anioPedido) {
		this.anioPedido = anioPedido;
	}

	public Integer getDiaEntrega() {
		return diaEntrega;
	}

	public void setDiaEntrega(Integer diaEntrega) {
		this.diaEntrega = diaEntrega;
	}

	public Integer getMesEntrega() {
		return mesEntrega;
	}

	public void setMesEntrega(Integer mesEntrega) {
		this.mesEntrega = mesEntrega;
	}

	public Integer getAnioEntrega() {
		return anioEntrega;
	}

	public void setAnioEntrega(Integer anioEntrega) {
		this.anioEntrega = anioEntrega;
	}

	public Integer getDescuento() {
		return descuento;
	}

	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}
	

}