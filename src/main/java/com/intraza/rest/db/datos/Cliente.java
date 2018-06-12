package com.intraza.rest.db.datos;

public class Cliente {

	private int idCliente = 0;
	private byte[] nombreCliente = null;
	private String descripcion = null;
	private String telefono = null;

	public Cliente(int idCliente, byte[] nombreCliente, String descripcion, String telefono) {
		this.idCliente = idCliente;
		this.nombreCliente = nombreCliente;
		this.descripcion = descripcion;
		this.telefono = telefono;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public byte[] getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(byte[] nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}