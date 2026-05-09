package es.ubu.lsi.test;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

	/** Connection pool. */
	private static PoolDeConexiones pool;

	/** Path. */
	private static final String SCRIPT_PATH = "sql/";

	/** Simple date format. */
	private static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static void ejecutarTests(Service implService) throws Exception{
		System.out.println();
		System.out.println("**** TEST ADICIONALES CLASE ALUMNO *****");
		
	}

}
