package es.ubu.lsi.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.lsi.service.catering.IncidentException;
import es.ubu.lsi.service.catering.Service;
import es.ubu.lsi.test.util.PoolDeConexiones;

/**
 * Test client.
 * 
 * @author <a href="mailto:lmr1027@alu.ubu.es">Luis Menendez</a>
 * @since 1.0
 */
public class TestClientAlumno {
	
	/** Logger. */
	private static final Logger logger = LoggerFactory.getLogger(TestClientAlumno.class);

	/** Connection pool. */
	private static PoolDeConexiones pool = PoolDeConexiones.getInstance();


	/** Simple date format. */
	private static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static void ejecutarTests(Service implService) throws Exception{
		System.out.println();
		System.out.println("**** TEST ADICIONALES CLASE ALUMNO *****");
		
		comprobarRollbackTransaccion(implService);	
		consultarMenuExistente(implService);
		
		
	}
	/**
	 * Comprueba que una transaccion erronea no modifica la base de datos.
	 * Verifica el rollback de la operacion
	 * 
	 * @param implService servicio
	 * @throws Exception error
	 */
	private static void comprobarRollbackTransaccion(Service implService) throws Exception{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			System.out.println("Comprobacion rollback despues de una excepcion");
			con = pool.getConnection();
			// contar las compras antes del error
			String sqlSelect = "Select count(0) from compra";
			st = con.createStatement();			
			rs = st.executeQuery(sqlSelect);
			int comprasAntes = 0;
			
			if (rs.next()) {
				comprasAntes = rs.getInt(1);
			}
			rs.close();
			
			// forzamos una excepcion
			try {
				implService.insertarCompra(dateformat.parse("01/01/2025 10:00"), "Cliente_Inexistente", 1, 20);
			}catch(IncidentException ex) {
				//excepcion
			}
			
			// contar las compras despues del error
			rs = st.executeQuery(sqlSelect);
			int comprasDespues = 0;
			if(rs.next()) {
				comprasDespues = rs.getInt(1);
			}
			
			if(comprasAntes == comprasDespues) {
				System.out.println("\tOk rollback realizado de forma correcta");
			} else {
				System.out.println("\tError la transacion modificó la BBDD");
			}
			

		}  catch (Exception ex) {

			logger.error("ERROR grave en test rollback: " + ex.getLocalizedMessage());
			throw ex;

		} finally {

			cerrarRecursos(con, st, rs);
		}
	}
	
	
	private static void consultarMenuExistente(Service implService) {
		try {
			System.out.println("Consultar menú existente");
			if(!implService.consultarMenu(1).isEmpty()) {
				System.out.println("\tOK menú recuperado correctamente");
			}else {
				System.out.println("\tError el menu existe pero la lista está vacía");
			}
		}catch (Exception ex) {
			logger.error("ERROR en consulta de menú: " +ex.getLocalizedMessage());
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
	

}
