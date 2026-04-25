package es.ubu.lsi.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.model.catering.Cliente;
import es.ubu.lsi.model.catering.Compra;
import es.ubu.lsi.model.catering.Menu;
import es.ubu.lsi.service.PersistenceException;
import es.ubu.lsi.service.catering.IncidentError;
import es.ubu.lsi.service.catering.IncidentException;
import es.ubu.lsi.service.catering.Service;
import es.ubu.lsi.service.catering.ServiceImpl;
import es.ubu.lsi.test.util.ExecuteScript;
import es.ubu.lsi.test.util.PoolDeConexiones;

/**
 * Test client.
 * 
 * @author <a href="mailto:pgdiaz@ubu.es">Pablo García</a> 
 * @since 1.0
 */
public class TestClient {

	/** Logger. */
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	/** Connection pool. */
	private static PoolDeConexiones pool;

	/** Path. */
	private static final String SCRIPT_PATH = "sql/";

	/** Simple date format. */
	private static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	/**
	 * Main.
	 * 
	 * @param args arguments.
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Iniciando...");
			init();
			System.out.println("Probando el servicio...");
			testService();
			System.out.println("FIN.............");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error grave en la aplicación {}", ex.getMessage());
		}
	}

	/**
	 * Init pool.
	 */
	static public void init() {
		try {
			// Acuerdate de q la primera vez tienes que crear el .bindings con:
			//PoolDeConexiones.reconfigurarPool();
			// Inicializacion de Pool
			pool = PoolDeConexiones.getInstance();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Create tables.
	 */
	static public void createTables() {
		ExecuteScript.run(SCRIPT_PATH + "script.sql");
	}

	/**
	 * Test service using JDBC and JPA.
	 */
	static void testService() throws Exception {
		//createTables();
		Service implService = null;
		try {
			// JPA Service
			implService = new ServiceImpl();
			System.out.println("Framework y servicio iniciado...");

			
			insertarCompraCorrecta(implService);
			
			insertarCompraFechaNula(implService);
			
			insertarCompraClienteInexistente(implService);
			
			insertarCompraMenuInexistente(implService);
			
			insertarCompraCompraExistente(implService);
			
			
			createTables();
			insertarCompraImporteNegativo(implService);
			
			insertarCompraPersonasNegativas(implService);
			createTables();
			quitarDescuentoCorrecto(implService);
			
			quitarDescuentoClienteNoExistente(implService);
			

					
			// comprueba que la consulta de menus carga todos los datos
			consultarMenusConMenuInexistente(implService);			
			
			// comprueba que la consulta de menus carga todos los datos
			consultarMenusUsandoGrafo(implService);
			


		} catch (Exception e) { // for testing code...
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			pool = null;
		}
	} // testClient
	
	
	/**
	 * Intenta insertar una compra con un cliente con un numero de personas negativas
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraPersonasNegativas(Service implService) {
		try {
			System.out.println("Insertar Compra con Personas 0 o negativas");
			implService.insertarCompra(dateformat.parse("22/04/2025 23:00"), "B10000000", 4,0);
			System.out.println("\tERROR NO detecta que el número de personas es incorrecto y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NEGATIVE_OR_ZERO_PEOPLE) {
				System.out.println("\tOK detecta correctamente que el número de personas es incorrecto");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}	
	
	/**
	 * Intenta insertar una compra con un cliente con un descuento de mas del 100%
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraImporteNegativo(Service implService) {
		try {
			System.out.println("Insertar Compra con Importe negativo o cero");
			implService.insertarCompra(dateformat.parse("22/04/2025 23:00"), "D10000000", 4, 3);
			System.out.println("\tERROR NO detecta que el importe de la compra será negativo o cero y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NEGATIVE_OR_ZERO_IMPORT) {
				System.out.println("\tOK detecta correctamente que el importe es negativo o cero");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}			
	
	/**
	 * Intenta quitar el descuento a un cliente no existente
	 * 
	 * @param implService servicio
	 */
	private static void quitarDescuentoClienteNoExistente(Service implService) {
		try {
			System.out.println("Quitar descuento con cliente inexistente");
			implService.quitarDescuento("NO EXISTO");
			System.out.println("\tERROR NO detecta que NO EXISTE el cliente y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NOT_EXISTS_CLIENT) {
				System.out.println("\tOK detecta correctamente que no existe el cliente");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de quitarDescuento con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en quitarDescuento ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de quitarDescuento con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en quitarDescuento ", ex);
		}
	}		
	
	
	/**
	 * Intenta insertar unca compra con un cliente y fecha existene.
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraCompraExistente(Service implService) {
		try {
			System.out.println("Insertar compra con fecha y cliente existentes");
			implService.insertarCompra(dateformat.parse("22/04/2020 13:00"), "B10000000", 1, 30);
			System.out.println("\tERROR NO detecta que EXISTE el cliente y la fecha finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.EXISTS_PURCHASE) {
				System.out.println("\tOK detecta correctamente que existe el cliente y fecha");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}		
	
	/**
	 * Intenta insertar unca compra con un menu inexistente.
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraMenuInexistente(Service implService) {
		try {
			System.out.println("Insertar compra con menú inexistente");
			implService.insertarCompra(dateformat.parse("15/05/2019 16:00"), "B10000000", 12, 30);
			System.out.println("\tERROR NO detecta que NO existe el menú y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NOT_EXISTS_MENU) {
				System.out.println("\tOK detecta correctamente que NO existe el menú");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}		
	
	
	/**
	 * Intenta insertar unca compra con un cliente inexistente.
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraClienteInexistente(Service implService) {
		try {
			System.out.println("Insertar compra con cliente inexistente");
			implService.insertarCompra(dateformat.parse("15/05/2019 16:00"), "NO EXISTE", 1, 30);
			System.out.println("\tERROR NO detecta que NO existe el cliente y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NOT_EXISTS_CLIENT) {
				System.out.println("\tOK detecta correctamente que NO existe el cliente");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}	
	
	/**
	 * Intenta insertar cmpra con fecha nula.
	 * 
	 * @param implService servicio
	 */
	private static void insertarCompraFechaNula(Service implService) {
		try {
			System.out.println("Insertar compra con fecha nula");
			implService.insertarCompra(null, "B10000000", 1, 30);
			System.out.println("\tERROR NO detecta que la fecha es nula y no salta excepción");

		} catch (IncidentException ex) {
			System.out.println("\tOK detecta correctamente que la fecha es nula: " + ex.getLocalizedMessage());
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en insertarCompra ", ex);
		} catch(Exception ex) {
			logger.error("ERROR GRAVE de programación en transacción de insertarCompra con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en insertarCompra ", ex);
		}
	}
	


	/**
	 * quitar descuento correctamente a conductor con dos incidencias.
	 * 
	 * @param implService implementación del servicio
	 * @throws Exception error en test
	 */
	private static void quitarDescuentoCorrecto(Service implService) throws Exception {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		float devuelto = -1;
		try {
			System.out.print("Quitar descuento del cliente...\n");
			devuelto = implService.quitarDescuento("B10000000");
			if(devuelto != 0) {
				System.out.println("\tERROR valor devuelto por descuento incorrecto");
			}else {
				System.out.println("\tOK valor devuelto por descuento correcto");
			}
				
			
			con = pool.getConnection();

			
			st = con.createStatement();
			rs = st.executeQuery("SELECT count(0) FROM compra where cif = 'B10000000'");

			StringBuilder resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			String cadenaEsperada =
			// @formatter:off
			"5\n";
			// @formatter:on
			

			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK filas correctas en compra después de aplicar descuento");
			} else {
				System.out.println("\tERROR filas incorrectas en compra después de aplicar descuento");
			}
			rs.close();
			con.commit();		
			
			st = con.createStatement();
			rs = st.executeQuery("SELECT sum(importe) FROM compra where cif = 'B10000000'");

			resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getFloat(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			cadenaEsperada =
			// @formatter:off
			"1487.5\n";
			// @formatter:on
			

			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK Importe en compra correcto después de aplicar descuento");
			} else {
				System.out.println("\tERROR Importe en compra incorrecto después de aplicar descuento");
			}
			
			
			rs.close();
			con.commit();	
			
			
			devuelto = implService.quitarDescuento("A10000000");
			System.out.println("El valor devuelto es " + String.format("%.4f", devuelto));
			
			if(String.format("%.4f", devuelto).equals("1242,4938".toString())) {
				System.out.println("\tOK valor devuelto por descuento correcto");
				
			}else {
				System.out.println("\tERROR valor devuelto por descuento incorrecto");
			}
				
			
			con = pool.getConnection();

			
			st = con.createStatement();
			rs = st.executeQuery("SELECT count(0) FROM compra where cif = 'A10000000'");

			resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			cadenaEsperada =
			// @formatter:off
			"3\n";
			// @formatter:on
			

			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK filas correctas en compra después de aplicar descuento");
			} else {
				System.out.println("\tERROR filas incorrectas en compra después de aplicar descuento");
			}
			rs.close();
			con.commit();
			
			st = con.createStatement();
			rs = st.executeQuery("SELECT sum(importe) FROM compra where cif = 'A10000000'");

			resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getFloat(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			cadenaEsperada =
			// @formatter:off
			"4825.0\n";
			// @formatter:on
			

			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK Importe en compra correcto después de aplicar descuento");
			} else {
				System.out.println("\tERROR Importe en compra incorrecto después de aplicar descuento");
			}
			
			
			rs.close();
			con.commit();			
			
		} catch (Exception ex) {
			logger.error("ERROR grave en test. " + ex.getLocalizedMessage());
			con.rollback();
			throw ex;
		} finally {
			cerrarRecursos(con, st, rs);
		}
	}
	


	/**
	 * Inserta una compra correcta.
	 * 
	 * @param implService implementación del servicio
	 * @throws Exception error en test
	 */
	private static void insertarCompraCorrecta(Service implService) throws Exception {

		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			System.out.println("Insertar compra correcta");
			implService.insertarCompra(dateformat.parse("15/05/2019 16:00"), "B10000000", 1, 30); // 3 es moderada con 3
			con = pool.getConnection();

			// Comprobar si la incidencia se ha añadido
			st = con.createStatement();
			rs = st.executeQuery("SELECT fecha||'-'||personas||'-'||importe FROM compra where cif = 'B10000000' AND idmenu = 1 order by fecha asc");

			StringBuilder resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			String cadenaEsperada =
			// @formatter:off
			"12/04/19 10:00:00,000000-20-250\n" +
			"13/04/19 11:00:00,000000-30-375\n" +
			"14/04/19 11:00:00,000000-15-187,5\n" +
			"15/05/19 16:00:00,000000-30-375\n";
			// @formatter:on
			

			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK compra bien insertada");
			} else {
				System.out.println("\tERROR compra mal insertada");
			}
			rs.close();
			con.commit();
		

			implService.insertarCompra(dateformat.parse("15/05/2019 16:00"), "C10000000", 3, 30);																						// puntos
			con = pool.getConnection();

			// Comprobar si la incidencia se ha añadido
			st = con.createStatement();
			rs = st.executeQuery("SELECT fecha||'-'||personas||'-'||importe FROM compra where cif = 'C10000000' AND idmenu = 3 order by fecha asc");

			resultado = new StringBuilder();
			while (rs.next()) {
				resultado.append(rs.getString(1));
				resultado.append("\n");
			}
			logger.debug(resultado.toString());
			cadenaEsperada =
			// @formatter:off
			"15/05/19 16:00:00,000000-30-510,3\n" +
			"15/06/19 11:00:00,000000-500-8505\n";
			// @formatter:on
			
	
			if (cadenaEsperada.equals(resultado.toString())) {
				System.out.println("\tOK compra 2  bien insertada");
			} else {
				System.out.println("\tERROR compra 2  mal insertada");
			}
			rs.close();
			con.commit();			
			
			
			
			
		} catch (Exception ex) {
			logger.error("ERROR grave en test. " + ex.getLocalizedMessage());
			con.rollback();
			throw ex;
		} finally {
			cerrarRecursos(con, st, rs);
		}
	}

	
	/**
	 * Consulta con un tipo de menu no existente
	 * 
	 * @param implService servicio
	 */
	private static void consultarMenusConMenuInexistente(Service implService) {
		try {
			System.out.println("Cnosulta con tipo de menú erróneo");
			// fecha y usuario correcto
			implService.consultarMenu(15); // 15 no existe
			System.out.println("\tERROR NO detecta que NO existe el menú y finaliza la transacción");

		} catch (IncidentException ex) {
			if (ex.getError() == IncidentError.NOT_EXISTS_MENU) {
				System.out.println("\tOK detecta correctamente que NO existe ese menú");
			} else {
				System.out.println("\tERROR detecta un error diferente al esperado:  " + ex.getError().toString());
			}
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción consultar menú con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en consultar menú", ex);
		} catch(Exception ex) {
			logger.error("ERROR grave de programación en transacción de consultar menús con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error grave en consultar de menús", ex);
		}
	}	

	/**
	 * Prueba consulta de menus, cargando datos completos desde un grafo de
	 * entidades.
	 * 
	 * @param implService implementación del servicio
	 */
	private static void consultarMenusUsandoGrafo(Service implService) {
		try {
			System.out.println("Información completa con grafos de entidades...");
			List<Menu> menus = implService.consultarMenu(1);		
			for (Menu menu : menus) {
				System.out.println(menu.toString());
				Set<Compra> compras = menu.getCompras();
				for (Compra compra : compras) {
					System.out.println("\t" + compra.toString());
					Cliente cliente = compra.getCliente();
					System.out.println("\t\t" + cliente.toString());
				}
			}
			System.out.println("OK Sin excepciones en la consulta completa y acceso posterior");
		} catch (PersistenceException ex) {
			logger.error("ERROR en transacción de consultarMenusUsandoGrafo con JPA: " + ex.getLocalizedMessage());
			throw new RuntimeException("Error en consulta de vehiculos", ex);
		}
	}
	
	
	
	/**
	 * Cierra recursos de la transacción.
	 * 
	 * @param con conexión
	 * @param st  sentencia
	 * @param rs  conjunto de datos
	 * @throws SQLException si se produce cualquier error SQL
	 */
	private static void cerrarRecursos(Connection con, Statement st, ResultSet rs) throws SQLException {
		if (rs != null && !rs.isClosed())
			rs.close();
		if (st != null && !st.isClosed())
			st.close();
		if (con != null && !con.isClosed())
			con.close();
	}

} // TestClient
