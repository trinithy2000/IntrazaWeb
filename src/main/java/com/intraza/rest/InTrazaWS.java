package com.intraza.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.intraza.rest.db.*;
import com.intraza.rest.db.datos.*;

/**
 * 
 * Clase que define los WebService REST
 * 
 */
@Path(value = "/sincroniza")
public class InTrazaWS {
	
	private static Logger logger = Logger.getLogger(InTrazaWS.class);

	@GET
	@Path("hola")
	@Produces("text/html")
	public String getHtml() {
		logger.debug("##### hola ######");
		return "<html lang=\"es\"><body><h1>Servicio operativo</h1></body></html>";
	}
	
	@GET
	@Path("totales")
	@Produces(MediaType.APPLICATION_JSON)
	public Totales consultaTotalesBD() {
		return JDBCQuery.getRegistrosTotales();
	}
	
	@GET
	@Path("articulos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Articulo> consultaArticulosBD() {
		return JDBCQuery.getArticulos();
	}

	@GET
	@Path("clientes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cliente> consultaClientesBD() {
		return JDBCQuery.getClientes();
	}

	@GET
	@Path("ruteros_total_fraccionados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rutero> getRuterosTotalFraccionados(@QueryParam("start") int start, @QueryParam("end") int end) {
		return JDBCQuery.getRuterosTotalFraccionados(start, end);
	}
	
	@GET
	@Path("ruteros_fraccionados")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rutero> consultaRuterosFraccionados(@QueryParam("start") int start, @QueryParam("end") int end) {
		return JDBCQuery.getRuterosFraccionados(start, end);
	}
		
	@GET
	@Path("ruteros_total")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rutero> getRuterosTotal() {
		return JDBCQuery.getRuterosTotal();
	}
	
	@GET
	@Path("ruteros")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Rutero> consultaRuteros() {
		return JDBCQuery.getRuteros();
	}
	
	@GET
	@Path("rutero_tarifa_cliente")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoConsultaDato consultaTarifaClienteRuteroBD(@QueryParam("idCliente") int cliente,
			@QueryParam("codigoArticulo") String articulo) {
		return JDBCQuery.getRuteroTarifaCliente(cliente, articulo);
	}

	@GET
	@Path("rutero_peso_total_anio")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoConsultaDato consultaPesoTotalAnioRuteroBD(@QueryParam("idCliente") int cliente,
			@QueryParam("codigoArticulo") String articulo) {
		return JDBCQuery.getRuteroPesoTotalAnio(cliente, articulo);
	}

	@GET
	@Path("rutero_datos")
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoDatosRutero consultaDatosParaRuteroBD(@QueryParam("idCliente") int cliente,
			@QueryParam("codigoArticulo") String articulo) {
		return JDBCQuery.getDatosParaRutero(cliente, articulo);
	}

	@POST
	@Path("prepedido")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultadoEnvioPedido enviaPrepedidoBD(String jsonPrepedido) {
		ResultadoEnvioPedido resultadoEnvio = null;
		try {
			// Convertimos el JSON que nos llega a un objeto java
			ObjectMapper mapper = new ObjectMapper();
			JsonPedido datosPrepedido = mapper.readValue(jsonPrepedido, JsonPedido.class);
			
			logger.debug("##### observaciones (" + datosPrepedido.getObservaciones() + ") ######");

			resultadoEnvio = JDBCQuery.postPrepedido(datosPrepedido);
		} catch (Exception e) {
			resultadoEnvio = new ResultadoEnvioPedido(ResultadoEnvioPedido.CON_ERROR,
					"Se ha producido una excepcion al decodificar JSON (" + jsonPrepedido + ") (" + e.toString() + ")");
		}

		return resultadoEnvio;
	}
}
