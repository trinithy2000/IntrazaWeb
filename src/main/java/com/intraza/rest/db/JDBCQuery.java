package com.intraza.rest.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.intraza.rest.db.datos.*;
import com.intraza.rest.util.NamedParamStatement;

/**
 * 
 * Clase con las operaciones de consulta a la BD
 * 
 */
public class JDBCQuery {
	private static Logger logger = Logger.getLogger(JDBCQuery.class);

	private final static String SELECT_ARTICULOS = "SELECT id, kg, fecha_tarifa, codigo, descripcion, congelado, nombre, tarifa, precioventa FROM producto ORDER BY codigo;";

	private final static String SELECT_CLIENTES = "SELECT id, nombre, descripcion, telefono FROM cliente ORDER BY id;";

	private final static String SELECT_RUTEROS = "SELECT z.id, z.idart ,z.codigo, z.cliente_id, z.fechaoferta, z.unidades, z.cantidad, z.precioventa, "
			+ "z.descripcion FROM ( SELECT r.id, d.id as idart ,codigo, p.cliente_id, p.fechaoferta, r.unidades, r.cantidad, precioventa, d.descripcion "
			+ "FROM pedido as p INNER JOIN productos_pedido AS r ON p.id = r.pedido_id INNER JOIN producto AS d ON d.id= r.producto_id " + "WHERE p.fechaoferta >= ";

	private final static String SELECT_RUTEROS_NEXT = ") as z;";

	private final static String SELECT_RUTEROS_PRACIONADOS = "SELECT z.id, z.idart ,z.codigo, z.cliente_id, z.fechaoferta, z.unidades, "
			+ "z.cantidad, z.precioventa, z.descripcion FROM ( SELECT r.id, d.id as idart, codigo, p.cliente_id, p.fechaoferta, r.unidades, "
			+ "r.cantidad, precioventa, d.descripcion FROM pedido as p INNER JOIN productos_pedido AS r ON p.id = r.pedido_id "
			+ "INNER JOIN producto AS d ON d.id= r.producto_id ) as z INNER JOIN (SELECT ROW_NUMBER() OVER (ORDER BY id) AS row, "
			+ "* FROM pedido ) AS b ON z.id = b.id WHERE ( z.fechaoferta >= ";

	private final static String SELECT_RUTEROS_PRACIONADOS_NEXT = " AND b.row BETWEEN :start AND :end ) ORDER BY b.row ";

	private final static String SELECT_TOTAL_ARTICULOS = "SELECT count(*) FROM producto";

	private final static String SELECT_TOTAL_CLIENTES = "SELECT count(*) FROM cliente";

	private final static String SELECT_TOTAL_RUTEROS = "SELECT count(*) FROM pedido as p inner join productos_pedido as r on p.id = r.pedido_id "
			+ "inner join producto as d on d.id= r.producto_id;";

	private final static String SELECT_ID_PEDIDO_PARA_INSERT = "SELECT max(id) FROM pedido";

	private final static String SELECT_ID_ARTICULO_PEDIDO = "SELECT max(id) FROM productos_pedido";

	/**
	 * Devuelve una conexion con la BD
	 */

	public static Connection getConnection() throws Exception {
		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "erp";
		String driver = "com.mysql.cj.jdbc.Driver";
		String userName = "root";
		String password = "";
		String opts = "?useLegacyDatetimeCode=false&serverTimezone=UTC";

		Class<?> driver_class = Class.forName(driver);
		Driver drv = (Driver) driver_class.newInstance();
		DriverManager.registerDriver(drv);

		Connection conn = DriverManager.getConnection(url + dbName + opts, userName, password);

		return conn;
	}

	/**
	 * Obtiene los totales de los datos de articulo, cliente, rutero y observaciones
	 * a sincronizar de la BD de intraza
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un articulo
	 */
	@SuppressWarnings("resource")
	public static Totales getRegistrosTotales() {
		Totales totales = new Totales(0, 0, 0);
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			logger.debug("########SINCRONIZACION SOLICITADA#####");
			rs = stmt.executeQuery(SELECT_TOTAL_ARTICULOS);
			while (rs.next())
				totales.setTotalArticulos(rs.getInt(1));

			rs = stmt.executeQuery(SELECT_TOTAL_CLIENTES);
			while (rs.next())
				totales.setTotalClientes(rs.getInt(1));

			rs = stmt.executeQuery(SELECT_TOTAL_RUTEROS);
			while (rs.next())
				totales.setTotalRuteros(rs.getInt(1));

		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			totales = new Totales(-1, -1, -1);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return totales;
	}

	/**
	 * Obtiene los datos de los articulos de la BD de intraza, para la tabla
	 * articulo de la BD de la tablet
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un articulo
	 */
	public static ArrayList<Articulo> getArticulos() {
		ArrayList<Articulo> listaArticulos = new ArrayList<Articulo>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		Integer id = 0;
		boolean esKg = false;
		String fechaTarifa = "";
		String codigo = "";
		byte[] descripcion = new byte[4906];
		boolean esCongelado = false;
		byte[] name = new byte[4906];
		Float tarifa = new Float("0");
		Float precioVenta = new Float("0");

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_ARTICULOS);

			while (rs.next()) {
				id = rs.getInt(1);
				esKg = rs.getBoolean(2);
				fechaTarifa = rs.getString(3);
				codigo = rs.getString(4);
				descripcion = rs.getBytes(5);
				esCongelado = rs.getBoolean(6);
				name = rs.getBytes(7);
				tarifa = rs.getFloat(8);
				precioVenta = rs.getFloat(9);
				if (StringUtils.isBlank(fechaTarifa)) {
					fechaTarifa = "01-01-1901";
				}
				listaArticulos.add(new Articulo(id, esKg, fechaTarifa, codigo, descripcion, esCongelado, name, tarifa, precioVenta));
			}
		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();

			// Devolvemos una lista vacia
			listaArticulos = new ArrayList<Articulo>();
		}
		// Cerramos los objetos abiertos para la consulta en la BD
		finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaArticulos;
	}

	/**
	 * Obtiene los datos de los clientes de la BD de intraza, para la tabla cliente
	 * de la BD de la tablet
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un cliente
	 */
	public static ArrayList<Cliente> getClientes() {
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SELECT_CLIENTES);
			while (rs.next())
				listaClientes.add(new Cliente(rs.getInt(1), rs.getBytes(2), rs.getString(3), rs.getString(4)));

		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			listaClientes = new ArrayList<Cliente>();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaClientes;
	}

	/**
	 * Obtiene los datos de los ruteros de la BD de intraza, para la tabla rutero de
	 * la BD de la tablet en bloques
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un rutero
	 */
	public static ArrayList<Rutero> getRuterosTotalFraccionados(int start, int end) {
		ArrayList<Rutero> listaRuteros = new ArrayList<Rutero>();
		NamedParamStatement stmt = null;
		Statement stmtTarifaCliente = null, stmtTarifaDefecto = null, stmtPesoTotalAnio = null;
		Connection conn = null;
		ResultSet rs = null, rsTarifaCliente = null, rsTarifaDefecto = null, rsPesoTotalAnio = null;
		String codigoArticulo = "", fechaPedido = "";
		int idCliente = 0, unidades = 0, unidadesTotalAnio = 0, idArticulo = 0;
		float peso = 0, pesoTotalAnio = 0, precio = 0, precioCliente = 0;
		byte[] observaciones = null;
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;

		String sqlQuery = SELECT_RUTEROS_PRACIONADOS;
		sqlQuery += "'" + String.valueOf(year) + "-01-01'";
		sqlQuery += SELECT_RUTEROS_PRACIONADOS_NEXT;

		try {

			conn = getConnection();
			stmt = new NamedParamStatement(conn, sqlQuery);
			stmt.setInt("start", start);
			stmt.setInt("end", end);
			rs = stmt.executeQuery();

			while (rs.next()) {
				idArticulo = rs.getInt(2);
				codigoArticulo = rs.getString(3);
				idCliente = rs.getInt(4);
				fechaPedido = rs.getString(5);
				unidades = rs.getInt(6);
				peso = rs.getFloat(7);
				precio = rs.getFloat(8);
				observaciones = rs.getBytes(9);

				stmtTarifaCliente = conn.createStatement();
				rsTarifaCliente = stmtTarifaCliente.executeQuery(dameSelectTarifaClienteArticuloParaRutero(idCliente, codigoArticulo));

				if (rsTarifaCliente.next())
					precioCliente = rsTarifaCliente.getFloat(1);

				rsTarifaCliente.close();
				stmtTarifaCliente.close();

				// Tenemos que obtener el peso total al anio
				stmtPesoTotalAnio = conn.createStatement();
				rsPesoTotalAnio = stmtPesoTotalAnio.executeQuery(dameSelectPesoTotalAnioParaClienteArticulo(idCliente, codigoArticulo));
				if (rsPesoTotalAnio.next()) {
					pesoTotalAnio = rsPesoTotalAnio.getFloat(2);
					unidadesTotalAnio = rsPesoTotalAnio.getInt(3);
				} else {
					pesoTotalAnio = 0;
					unidadesTotalAnio = 0;
				}

				rsPesoTotalAnio.close();
				stmtPesoTotalAnio.close();

				listaRuteros.add(
						new Rutero(idArticulo, codigoArticulo, idCliente, fechaPedido, unidades, peso, unidadesTotalAnio, pesoTotalAnio, precio, precioCliente, observaciones));
			}
		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			listaRuteros = new ArrayList<Rutero>();
		}
		// Cerramos los objetos abiertos para la consulta en la BD
		finally {
			try {
				if (rs != null)
					rs.close();
				if (rsTarifaCliente != null)
					rsTarifaCliente.close();
				if (rsTarifaDefecto != null)
					rsTarifaDefecto.close();
				if (rsPesoTotalAnio != null)
					rsPesoTotalAnio.close();
				if (stmt != null)
					stmt.close();
				if (stmtTarifaCliente != null)
					stmtTarifaCliente.close();
				if (stmtTarifaDefecto != null)
					stmtTarifaDefecto.close();
				if (stmtPesoTotalAnio != null)
					stmtPesoTotalAnio.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaRuteros;
	}

	public static ArrayList<Rutero> getRuterosTotal() {
		ArrayList<Rutero> listaRuteros = new ArrayList<Rutero>();
		NamedParamStatement stmt = null;
		Statement stmtTarifaCliente = null, stmtTarifaDefecto = null, stmtPesoTotalAnio = null;
		Connection conn = null;
		ResultSet rs = null, rsTarifaCliente = null, rsTarifaDefecto = null, rsPesoTotalAnio = null;
		String codigoArticulo = "", fechaPedido = "";
		int idCliente = 0, unidades = 0, unidadesTotalAnio = 0, idArticulo = 0;
		float peso = 0, pesoTotalAnio = 0, precio = 0, precioCliente = 0;
		byte[] observaciones = null;
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;

		String sqlQuery = SELECT_RUTEROS;
		sqlQuery += "'" + String.valueOf(year) + "-01-01'";
		sqlQuery += SELECT_RUTEROS_NEXT;

		try {

			conn = getConnection();
			stmt = new NamedParamStatement(conn, sqlQuery);
			rs = stmt.executeQuery();

			while (rs.next()) {
				idArticulo = rs.getInt(2);
				codigoArticulo = rs.getString(3);
				idCliente = rs.getInt(4);
				fechaPedido = rs.getString(5);
				unidades = rs.getInt(6);
				peso = rs.getFloat(7);
				precio = rs.getFloat(8);
				observaciones = rs.getBytes(9);

				stmtTarifaCliente = conn.createStatement();
				rsTarifaCliente = stmtTarifaCliente.executeQuery(dameSelectTarifaClienteArticuloParaRutero(idCliente, codigoArticulo));

				if (rsTarifaCliente.next())
					precioCliente = rsTarifaCliente.getFloat(1);

				rsTarifaCliente.close();
				stmtTarifaCliente.close();

				// Tenemos que obtener el peso total al anio
				stmtPesoTotalAnio = conn.createStatement();
				rsPesoTotalAnio = stmtPesoTotalAnio.executeQuery(dameSelectPesoTotalAnioParaClienteArticulo(idCliente, codigoArticulo));
				if (rsPesoTotalAnio.next()) {
					pesoTotalAnio = rsPesoTotalAnio.getFloat(2);
					unidadesTotalAnio = rsPesoTotalAnio.getInt(3);
				} else {
					pesoTotalAnio = 0;
					unidadesTotalAnio = 0;
				}

				rsPesoTotalAnio.close();
				stmtPesoTotalAnio.close();

				listaRuteros.add(
						new Rutero(idArticulo, codigoArticulo, idCliente, fechaPedido, unidades, peso, unidadesTotalAnio, pesoTotalAnio, precio, precioCliente, observaciones));
			}
		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			listaRuteros = new ArrayList<Rutero>();
		}
		// Cerramos los objetos abiertos para la consulta en la BD
		finally {
			try {
				if (rs != null)
					rs.close();
				if (rsTarifaCliente != null)
					rsTarifaCliente.close();
				if (rsTarifaDefecto != null)
					rsTarifaDefecto.close();
				if (rsPesoTotalAnio != null)
					rsPesoTotalAnio.close();
				if (stmt != null)
					stmt.close();
				if (stmtTarifaCliente != null)
					stmtTarifaCliente.close();
				if (stmtTarifaDefecto != null)
					stmtTarifaDefecto.close();
				if (stmtPesoTotalAnio != null)
					stmtPesoTotalAnio.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaRuteros;
	}

	/**
	 * Obtiene los datos de los ruteros de la BD de intraza, para la tabla rutero de
	 * la BD de la tablet en bloques
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un rutero
	 */
	public static ArrayList<Rutero> getRuteros() {
		ArrayList<Rutero> listaRuteros = new ArrayList<Rutero>();

		NamedParamStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String codigoArticulo = "", fechaPedido = "";
		int idCliente = 0, unidades = 0, unidadesTotalAnio = 0, idArticulo = 0;
		float peso = 0, pesoTotalAnio = 0, precio = 0, precioCliente = 0;
		byte[] observaciones = null;
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;

		String sqlQuery = SELECT_RUTEROS;
		sqlQuery += "'" + String.valueOf(year) + "-01-01'";
		sqlQuery += SELECT_RUTEROS_NEXT;

		try {
			conn = getConnection();
			stmt = new NamedParamStatement(conn, sqlQuery);
			rs = stmt.executeQuery();

			while (rs.next()) {
				idArticulo = rs.getInt(2);
				codigoArticulo = rs.getString(3);
				idCliente = rs.getInt(4);
				fechaPedido = rs.getString(5);
				unidades = rs.getInt(6);
				peso = rs.getFloat(7);
				precio = rs.getFloat(8);
				observaciones = rs.getBytes(9);
				listaRuteros.add(
						new Rutero(idArticulo, codigoArticulo, idCliente, fechaPedido, unidades, peso, unidadesTotalAnio, pesoTotalAnio, precio, precioCliente, observaciones));
			}
		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			listaRuteros = new ArrayList<Rutero>();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaRuteros;
	}

	/**
	 * Obtiene los datos de los ruteros de la BD de intraza, para la tabla rutero de
	 * la BD de la tablet en bloques
	 * 
	 * @return ArrayList donde cada elemento seran los datos de un rutero
	 */
	public static ArrayList<Rutero> getRuterosFraccionados(int start, int end) {
		ArrayList<Rutero> listaRuteros = new ArrayList<Rutero>();

		NamedParamStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String codigoArticulo = "", fechaPedido = "";
		int idCliente = 0, unidades = 0, unidadesTotalAnio = 0, idArticulo = 0;
		float peso = 0, pesoTotalAnio = 0, precio = 0, precioCliente = 0;
		byte[] observaciones = null;
		int year = Calendar.getInstance().get(Calendar.YEAR) - 1;

		String sqlQuery = SELECT_RUTEROS_PRACIONADOS;
		sqlQuery += "'" + String.valueOf(year) + "-01-01'";
		sqlQuery += SELECT_RUTEROS_PRACIONADOS_NEXT;

		try {
			conn = getConnection();
			stmt = new NamedParamStatement(conn, sqlQuery);
			stmt.setInt("start", start);
			stmt.setInt("end", end);
			rs = stmt.executeQuery();

			while (rs.next()) {
				idArticulo = rs.getInt(2);
				codigoArticulo = rs.getString(3);
				idCliente = rs.getInt(4);
				fechaPedido = rs.getString(5);
				unidades = rs.getInt(6);
				peso = rs.getFloat(7);
				precio = rs.getFloat(8);
				observaciones = rs.getBytes(9);
				listaRuteros.add(
						new Rutero(idArticulo, codigoArticulo, idCliente, fechaPedido, unidades, peso, unidadesTotalAnio, pesoTotalAnio, precio, precioCliente, observaciones));
			}
		} catch (Exception e) {
			logger.error("Excepcion", e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
			listaRuteros = new ArrayList<Rutero>();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaRuteros;
	}

	/**
	 * Obtiene los datos de la tarifa cliente para un rutero
	 * 
	 * @param idCliente
	 * @param codigoArticulo
	 * 
	 * @return objeto con el resultado de la consulta
	 */
	public static ResultadoConsultaDato getRuteroTarifaCliente(int idCliente, String codigoArticulo) {
		ResultadoConsultaDato resultado = new ResultadoConsultaDato(ResultadoEnvioPedido.SIN_ERROR, null, -1, 0);
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(dameSelectTarifaClienteArticuloParaRutero(idCliente, codigoArticulo));
			if (rs.next())
				resultado.setDato(rs.getFloat(1));
			rs.close();
			stmt.close();
		} catch (Exception e) {
			resultado.setCodigoError(ResultadoConsultaDato.CON_ERROR);
			resultado.setDescripcionError(e.toString());
			logger.error("Excepcion: " + e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	/**
	 * Obtiene los datos de la tarifa defecto para un rutero
	 * 
	 * @param idCliente
	 * @param codigoArticulo
	 * 
	 * @return objeto con el resultado de la consulta
	 */
	public static ResultadoConsultaDato getRuteroPesoTotalAnio(int idCliente, String codigoArticulo) {
		ResultadoConsultaDato resultado = new ResultadoConsultaDato(ResultadoEnvioPedido.SIN_ERROR, null, 0, 0);
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(dameSelectPesoTotalAnioParaClienteArticulo(idCliente, codigoArticulo));

			if (rs.next()) {
				resultado.setDato(rs.getFloat(2));
				resultado.setDato2(rs.getInt(3));
			} else {
				resultado.setDato(0);
				resultado.setDato2(0);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			resultado.setCodigoError(ResultadoConsultaDato.CON_ERROR);
			resultado.setDescripcionError(e.toString());
			logger.error("Excepcion: " + e);
			logger.error("Excepcion: " + e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	/**
	 * Obtiene la tarifa cliente, tarifa defecto y peso total de una linea de rutero
	 * 
	 * @param idCliente
	 * @param codigoArticulo
	 * @return ArrayList donde cada elemento seran los datos de un rutero
	 */
	public static ResultadoDatosRutero getDatosParaRutero(int idCliente, String codigoArticulo) {
		ResultadoDatosRutero resultadoDatosRutero = new ResultadoDatosRutero(ResultadoEnvioPedido.SIN_ERROR, null, -1, -1, 0);
		Statement stmtTarifaCliente = null;
		Statement stmtTarifaDefecto = null;
		Statement stmtPesoTotalAnio = null;
		Connection conn = null;
		ResultSet rsTarifaCliente = null;
		ResultSet rsPesoTotalAnio = null;

		try {
			conn = getConnection();

			stmtTarifaCliente = conn.createStatement();
			rsTarifaCliente = stmtTarifaCliente.executeQuery(dameSelectTarifaClienteArticuloParaRutero(idCliente, codigoArticulo));

			if (rsTarifaCliente.next())
				resultadoDatosRutero.setTarifaCliente(rsTarifaCliente.getFloat(1));

			rsTarifaCliente.close();
			stmtTarifaCliente.close();

			stmtPesoTotalAnio = conn.createStatement();
			rsPesoTotalAnio = stmtPesoTotalAnio.executeQuery(dameSelectPesoTotalAnioParaClienteArticulo(idCliente, codigoArticulo));

			if (rsPesoTotalAnio.next()) {
				resultadoDatosRutero.setPesoTotalAnio(rsPesoTotalAnio.getFloat(2));
				resultadoDatosRutero.setUnidadesTotalAnio(rsPesoTotalAnio.getInt(3));
			} else {
				resultadoDatosRutero.setPesoTotalAnio(0);
				resultadoDatosRutero.setUnidadesTotalAnio(0);
			}

			rsPesoTotalAnio.close();
			stmtPesoTotalAnio.close();
		} catch (Exception e) {
			logger.error("Excepcion: " + e);
			logger.error("Excepcion: " + e.toString());
			resultadoDatosRutero.setCodigoError(-1);
			resultadoDatosRutero.setTarifaCliente(0);
			resultadoDatosRutero.setPesoTotalAnio(0);
			resultadoDatosRutero.setUnidadesTotalAnio(0);
			e.printStackTrace();
		}
		// Cerramos los objetos abiertos para la consulta en la BD
		finally {
			try {
				if (rsTarifaCliente != null)
					rsTarifaCliente.close();
				if (rsPesoTotalAnio != null)
					rsPesoTotalAnio.close();
				if (stmtTarifaCliente != null)
					stmtTarifaCliente.close();
				if (stmtTarifaDefecto != null)
					stmtTarifaDefecto.close();
				if (stmtPesoTotalAnio != null)
					stmtPesoTotalAnio.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultadoDatosRutero;
	}

	private static String dameSelectTarifaClienteArticuloParaRutero(int idCliente, String codigoArticulo) throws Exception {

		logger.debug("########dameSelectTarifaClienteArticuloParaRutero#####");
		String sql = "SELECT t.tarifa, t.tarifadefecto FROM tarifa AS t INNER JOIN producto AS p ON p.id=t.producto_id " + "WHERE cliente_id=" + idCliente + " AND p.codigo like '"
				+ codigoArticulo + "';";
		logger.debug(sql);
		return sql;
	}

	private static String dameSelectPesoTotalAnioParaClienteArticulo(int idCliente, String codigoArticulo) throws Exception {

		logger.debug("########dameSelectPesoTotalAnioParaClienteArticulo#####");
		String sql = "SELECT s.producto_id, SUM(s.cantidad) AS pesototal, SUM(s.unidades) AS unidadestotales "
				+ "FROM pedido AS p INNER JOIN productos_pedido AS s ON p.id = s.pedido_id INNER JOIN producto AS x ON x.id = s.producto_id "
				+ "WHERE p.fechaoferta >= now() AND p.cliente_id=" + idCliente + " AND x.codigo like '" + codigoArticulo + "' GROUP BY s.producto_id;";
		logger.debug(sql);
		return sql;
	}

	@SuppressWarnings("resource")
	public static ResultadoEnvioPedido postPrepedido(JsonPedido prepedido) {
		ResultadoEnvioPedido resultado = new ResultadoEnvioPedido(ResultadoEnvioPedido.SIN_ERROR, null);
		Integer InsertPrepedido = InsertPrepedido(prepedido);
		return InsertPrepedidoItem(prepedido, resultado, InsertPrepedido);
	}

	public static Integer InsertPrepedido(JsonPedido prepedido) {
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int idPedidoInTraza = 0;

		try {
			conn = getConnection();
			stmt = conn.createStatement();

			// **********************************
			// Insertamos los datos del prepedido
			// **********************************

			rs = stmt.executeQuery(SELECT_ID_PEDIDO_PARA_INSERT);
			rs.next();
			idPedidoInTraza = rs.getInt(1);
			idPedidoInTraza += 1;
			logger.debug("########Insertamos el prepedido#####");
			// Insertamos el prepedido
			logger.debug(idPedidoInTraza);
			stmt.executeUpdate(dameInsertPrepedido(idPedidoInTraza,prepedido.getIdPedido() ,prepedido.getIdCliente(), prepedido.getObservaciones(), prepedido.getDiaFechaPedido(),
					prepedido.getMesFechaPedido(), prepedido.getAnioFechaPedido(), prepedido.getDiaFechaEntrega(), prepedido.getMesFechaEntrega(), prepedido.getAnioFechaEntrega(),
					prepedido.getDescuento()));

			if (prepedido.isFijar())
				stmt.executeUpdate(dameUpdateObservacionesPrepedidoTablaCliente(prepedido.getIdCliente(), prepedido.getObservaciones()));

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("!!!!!!!!!\n\n Se ha producido una excepcion (" + e.toString() + ")!!!!!!!!!!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return idPedidoInTraza;
	}

	public static ResultadoEnvioPedido InsertPrepedidoItem(JsonPedido prepedido, ResultadoEnvioPedido resultado, Integer idPedidoInTraza) {
		Statement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int idArticuloPedido = 0;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			// ******************************************
			// Insertamos los datos de los prepedido item
			// ******************************************

			rs = stmt.executeQuery(SELECT_ID_ARTICULO_PEDIDO);
			rs.next();
			idArticuloPedido = rs.getInt(1);

			for (int i = 0; i < prepedido.getLineasPedido().size(); i++) {
				logger.debug(idArticuloPedido);
				// Hacemos un insert de la linea de prepedido
				stmt.executeUpdate(dameInsertPrepedidoItem(++idArticuloPedido, idPedidoInTraza, prepedido.getLineasPedido().get(i).getIdArticulo(),
						prepedido.getLineasPedido().get(i).getCantidadKg(), prepedido.getLineasPedido().get(i).getCantidadUd(),
						prepedido.getLineasPedido().get(i).getObservaciones()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultado.setCodigoError(ResultadoEnvioPedido.CON_ERROR);
			resultado.setDescripcionError("Se ha producido una excepcion (" + e.toString() + ")");
			logger.debug("!!!!!!!!!\n\n Se ha producido una excepcion (" + e.toString() + ")!!!!!!!!!!");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultado;
	}

	private static String dameInsertPrepedido(int id, int referencia, int idCliente, String descripcion, int diaPedido, int mesPedido, int anioPedido, int diaEntrega, int mesEntrega,
			int anioEntrega, int descuento) throws Exception {

		logger.debug("########dameInsertPrepedido#####");

		String datePedido = anioPedido + "-" + String.format("%02d", mesPedido) + "-" + String.format("%02d", diaPedido) + " 00:00:00";
		String dateEntrega = anioEntrega + "-" + String.format("%02d", mesEntrega) + "-" + String.format("%02d", diaEntrega) + " 00:00:00";
		String sql = "INSERT INTO pedido (id, version, fechaoferta, fechavalidez, descripcion, referencia, cliente_id, es_prepedido) VALUES (" + id + ",0,'" + datePedido + "','"
				+ dateEntrega + "','" + descripcion + "','" + referencia + "'," + idCliente + ", true);";
		logger.debug(sql);
		return sql;
	}

	private static String dameUpdateObservacionesPrepedidoTablaCliente(int idCliente, String descripcion) throws Exception {
		logger.debug("########dameUpdateObservacionesPrepedidoTablaCliente#####");
		String sql = "UPDATE cliente SET descripcion = '" + descripcion + "' " + "WHERE id = " + idCliente;
		logger.debug(sql);
		return sql;
	}

	private static String dameInsertPrepedidoItem(int id, int idPedido, int idProducto, float cantidadKg, int cantidadUd, String observaciones) throws Exception {
		logger.debug("########dameInsertPrepedidoItem#####");
		String sql = "INSERT INTO productos_pedido (id, version,cantidad, unidades, observaciones, producto_id, pedido_id) " + "VALUES " + "(" + id + ", 0, " + cantidadKg + ", "
				+ cantidadUd + ", '" + observaciones + "', " + idProducto + ", " + idPedido + ")";
		logger.debug(sql);
		return sql;
	}
}