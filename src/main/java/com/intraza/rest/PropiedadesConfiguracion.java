package com.intraza.rest;

import java.io.InputStream;
import java.util.Properties;

/**
* @author JLZS
* Se encarga de obtener los valores de configuraci�n de la aplicaci�n
*/
public class PropiedadesConfiguracion
{
	//Fichero con la configuraci�n necesaria para la aplicacion
	public static String FICHERO_CONFIGURACION = "configuracion.properties";

	/**
	 * M�todo que devuelve el valor en formato String del parametro de configuracion indicado
	 * 
	 * @param parametro a leer de la configuracion
	 * @param valor por defecto en caso de que el parametro no exista
	 * 
	 * @return valor asociado al parametro en formato String
	 * @throws Exception Si falla la lectura de la configuracion
	 */
	public static String leeStringConfiguracion(String nombreParametro, String valorDefecto) throws Exception 
	{
		Properties configuracion = new Properties();
		String valorParametro = null;
		InputStream is = null;

		// Cargamos el fichero de propiedades
		is = PropiedadesConfiguracion.class.getResourceAsStream("/"+FICHERO_CONFIGURACION);
		if (is==null)
		{
			is = ClassLoader.getSystemResourceAsStream(FICHERO_CONFIGURACION);	
		}
		configuracion.load(is);

		//Leemos el valor del parametro en caso de ser nulo devolvemos el valor por defecto
		valorParametro = configuracion.getProperty(nombreParametro);

		if (valorParametro == null)
		{
				valorParametro = valorDefecto;
		}
		
		return valorParametro;
	}
}