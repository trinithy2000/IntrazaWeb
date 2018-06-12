package com.intraza.rest.db.datos;

public class Articulo {
	private int id = 0;
	private boolean esKg = false;
	private String fechaTarifa = null;
	private String codigo = null;
	private byte[] descripcion = null;
	private boolean esCongelado = false;
	private byte[] name = null;
	private float tarifa = 0;
	private float precioVenta = 0;

	public Articulo(int id, boolean esKg, String fechaTarifa, String codigo, byte[] descripcion, boolean esCongelado, byte[] name, float tarifa, float precioVenta) {
		this.id = id;
		this.esKg = esKg;
		this.fechaTarifa = fechaTarifa;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.esCongelado = esCongelado;
		this.name = name;
		this.tarifa = tarifa;
		this.precioVenta = precioVenta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEsKg() {
		return esKg;
	}

	public void setEsKg(boolean esKg) {
		this.esKg = esKg;
	}

	public String getFechaTarifa() {
		return fechaTarifa;
	}

	public void setFechaTarifa(String fechaTarifa) {
		this.fechaTarifa = fechaTarifa;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public byte[] getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(byte[] descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEsCongelado() {
		return esCongelado;
	}

	public void setEsCongelado(boolean esCongelado) {
		this.esCongelado = esCongelado;
	}

	public byte[] getName() {
		return name;
	}

	public void setName(byte[] name) {
		this.name = name;
	}

	public float getTarifa() {
		return tarifa;
	}

	public void setTarifa(float tarifa) {
		this.tarifa = tarifa;
	}

	public float getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(float precioVenta) {
		this.precioVenta = precioVenta;
	}
}
