package com.intraza.rest.db.datos;

public class Totales {

	private int totalArticulos = 0;
	private int totalClientes = 0;
	private int totalRuteros = 0;

	public Totales(int totalArticulos, int totalClientes, int totalRuteros) {
		super();
		this.totalArticulos = totalArticulos;
		this.totalClientes = totalClientes;
		this.totalRuteros = totalRuteros;
	}

	public int getTotalArticulos() {
		return totalArticulos;
	}

	public void setTotalArticulos(int totalArticulos) {
		this.totalArticulos = totalArticulos;
	}

	public int getTotalClientes() {
		return totalClientes;
	}

	public void setTotalClientes(int totalClientes) {
		this.totalClientes = totalClientes;
	}

	public int getTotalRuteros() {
		return totalRuteros;
	}

	public void setTotalRuteros(int totalRuteros) {
		this.totalRuteros = totalRuteros;
	}

}