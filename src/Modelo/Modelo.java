/**
 * @author Marta Molina Aguilera
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;


import com.mysql.cj.jdbc.result.ResultSetMetaData;

import Controlador.Controlador;
import Vista.Vista;

/*
 * Clase la cual contiene la lógica de la aplicación.
 */
@SuppressWarnings("unused")
public class Modelo {
	private Vista miVista;
	private DefaultTableModel modelo;

	private String bd = "Books";
	private String login = "root";
	private String pwd = "";
	private String url = "jdbc:mysql://localhost/" + bd;
	private Connection conexion;
	private PreparedStatement pstmt;
	private String sqlTabla1 = "SELECT * FROM BooksTable;";

	/*
	 * Constructor de la clase Modelo. Nada más ser creado un objeto Modelo, este
	 * llama desde el constructor a la función de crear una conexión con la BD y
	 * cargar la tabla con la información que esta le proporciona.
	 */
	public Modelo() {
		// Cargar pantalla al empezar
		crearConexion();
		cargarTabla1();
	}

	/**
	 * Función que crea la conexión con la base de datos.
	 * 
	 * @throws ClassNotFoundException cnfe: si no encuentra el driver
	 *                                mysql-connector-java
	 * @throws SQLException           sqle: Si no puede conectarse a la BD.
	 * @throws Exception              e: Con cualquier error de otro tipo.
	 */
	private void crearConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Driver JDBC no encontrado");
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.err.println("Error al conectarse a al BD");
			sqle.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error general");
			e.printStackTrace();
		}
	}

	/**
	 * Función la cual carga los datos de la tabla BooksTable y hace que la tabla de
	 * la ventana no sea editable.
	 * 
	 * @throws SQLException e: Si hubiera problemas accediendo a la Base de Datos.
	 */
	@SuppressWarnings("serial")
	private void cargarTabla1() {
		modelo = new DefaultTableModel();
		int numColumnas = getNumColumnas(sqlTabla1);
		int numFilas = getNumFilas(sqlTabla1);

		String[] cabecera = new String[numColumnas];
		Object[][] contenido = new Object[numFilas][numColumnas];
		try {
			pstmt = conexion.prepareStatement(sqlTabla1);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				cabecera[i] = rsmd.getColumnName(i + 1);
			}
			int fila = 0;
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[fila][col - 1] = rset.getString(col);
				}
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		modelo = new DefaultTableModel(contenido, cabecera) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};

	}

	/**
	 * Función la cual retorna el numero de columnas generado por la consulta
	 * ejecutada.
	 * 
	 * @param sql: Query a ejecutar.
	 * @return num: número de columnas.
	 * @throws SQLException e: si hay error al consultar la BD
	 */
	private int getNumColumnas(String sql) {
		int num = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rset.getMetaData();
			num = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * Función la cual retorna el numero de filas generado por la consulta
	 * ejecutada.
	 * 
	 * @param sql: Query a ejecutar.
	 * @return numFilas: nuemro de filas.
	 * @throws SQLException e: si hay error al consultar la BD
	 */
	private int getNumFilas(String sql) {
		int numFilas = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next())
				numFilas++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numFilas;
	}

	/**
	 * Función la cual hace un insert a la base de datos y añade una fila con estos
	 * a la tabla. Si hay un errror, muestra un mensaje en la ventana,
	 * 
	 * @param titulo:    dato enviado por el controlador, procedente de la vista del
	 *                   campo de texto de título.
	 * @param autor:     dato enviado por el controlador, procedente de la vista del
	 *                   campo de texto de autor.
	 * @param categoria: dato enviado por el controlador, procedente de la vista del
	 *                   campo de texto de categoria.
	 * @param precio:    dato enviado por el controlador, procedente de la vista del
	 *                   campo de texto de precio.
	 * @throws SQLException e: Si hay error al añadir el registro.
	 * 
	 */
	public void annadirRegistro(String titulo, String autor, String categoria, String precio) {
		try {
			Statement stmt = conexion.createStatement();
			String qery = "INSERT INTO `BooksTable` (`Titulo`, `Autor`, `Categoria`, `Precio`) VALUES ('" + titulo
					+ "','" + autor + "','" + categoria + "','" + precio + "')";
			stmt.executeUpdate(qery);
			stmt.close();
			modelo.insertRow(modelo.getRowCount(), new String[] { titulo, autor, categoria, precio.toString() });
			miVista.cambiarMsgResultado("Los datos han sido agregados");
			miVista.setVisible(false);
			miVista.setVisible(true);
		} catch (SQLException e) {
			miVista.cambiarError("Error al añadir registro. Cierre y abara la app de nuevo.");
			System.err.println(e);
		}
	}

	/**
	 * Método el cual modifica los datos del registro seleccionado en la ventana.
	 * 
	 * @param datos: array con los datos nuevos para el registro seleccionado.
	 * @throws SQLException e: Si hay error al seleccionar el registro.
	 * 
	 */
	public void modificarRegistro(String[] datos) {

		try {
			Statement stmt = conexion.createStatement();
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = (ResultSetMetaData) rset.getMetaData();
			String[] cabecera = new String[getNumColumnas(sqlTabla1)];
			String seleccion = datos[0];

			// Conseguir nombre de las columnas
			for (int i = 0; i < getNumColumnas(sqlTabla1); i++) {
				cabecera[i] = rsmd.getColumnName(i + 1);
			}
			String qry = "";

			for (int i = 1; i < cabecera.length; ++i) {
				qry = "UPDATE BooksTable SET " + cabecera[i] + "='" + datos[i + 1] + "' WHERE Titulo ='" + seleccion
						+ "';";
				PreparedStatement pst;
				pst = conexion.prepareStatement(qry);
				stmt.executeUpdate(qry);
			}
			// Para que la modificación del titulo no pise el resto
			qry = "UPDATE BooksTable SET " + cabecera[0] + "='" + datos[1] + "' WHERE Titulo ='" + seleccion + "';";
			pstmt = conexion.prepareStatement(qry);
			stmt.executeUpdate(qry);
			stmt.close();
			miVista.cambiarMsgResultado(
					"Los datos han sido modificados.");
			cargarTabla1();
			miVista.setVisible(false);
			miVista.setVisible(true);

		} catch (SQLException e) {
			miVista.cambiarError("Error al añadir registro. Cierre y abara la app de nuevo.");
			System.err.println(e);
		}
	}

	/**
	 * Metodo el cual borra el registro seleccionado en la tabla de miVista y en la
	 * base de datos.
	 * 
	 * @param titulo: título(ID) de regitro a borrar.
	 * @throws SQLException e: Si hay error al borrar el registro.
	 */
	public void borrarRegistro(String titulo) {
		try {
			Statement stmt = conexion.createStatement();
			ResultSet rset = pstmt.executeQuery();
			String qry = "DELETE FROM BooksTable WHERE Titulo='" + titulo + "';";
			PreparedStatement pst;
			pst = conexion.prepareStatement(qry);
			stmt.executeUpdate(qry);
			stmt.close();
			cargarTabla1();
			miVista.cambiarMsgResultado("El registro ha sido eliminado.");
			miVista.borrarFila();
		} catch (SQLException e) {
			miVista.cambiarError("Error al añadir registro. Cierre y abara la app de nuevo.");
			System.err.println(e);
		}
	}

	/**
	 * Método el cual llama a la Vista y hace visible en pantalla el título del
	 * registro seleccionado. Asigna en los campos de texto los valores del artículo
	 * seleccionado.
	 * 
	 * @param seleccion: título seleccionado en la tabla.
	 * @param autor:     autor del artículo seleccionado en la tabla.
	 * @param categoria: categoria del artículo seleccionado en la tabla.
	 * @param precio:    precio del artículo seleccionado en la tabla.
	 * 
	 */
	public void mostrarSeleccion(String titulo) {
		miVista.setSeleccion(titulo);
	}

	/**
	 * Función la cual retorna un objeto de la clase actual.
	 * 
	 * @return modelo
	 */
	public DefaultTableModel getModelo() {
		return modelo;
	}

	/**
	 * Método el cual asigna los valores de la clase Vista a la instancia declarada
	 * en esta clase. Consiguiendo así la comunicación entre clases.
	 * 
	 * @param miVista
	 */
	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}

}
