package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import Controlador.Controlador;
import Vista.Vista;

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
	private String[] sqlUpdates = { "UPDATE BooksTable SET ", "=? WHERE Titulo =?" };

	public Modelo() {
		// Cargar pantalla al empezar
		crearConexion();
		cargarTabla1();
	}

	private void crearConexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, login, pwd);
			System.out.println(" - Conexión con MySQL establecida -");
			if (conexion != null) {
				System.out.println("Conexión a la BD: " + url + " ok!");
			}
		} catch (ClassNotFoundException cnfe) {
			System.err.println("Driver JDBC no encontrado");
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println("Error al conectarse a al BD");
			sqle.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error general");
			e.printStackTrace();
		}
	}

	private void cerrarConexion() {
		try {
			conexion.close();
		} catch (SQLException e) {
			System.err.println("No se pudo cerrar la conexión.");
		}
	}

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

	public void annadirRegistro(String titulo, String autor, String categoria, String precio) {
		try {
			Statement stmt = conexion.createStatement();
			String qery = "INSERT INTO `BooksTable` (`Titulo`, `Autor`, `Categoria`, `Precio`) VALUES ('" + titulo
					+ "','" + autor + "','" + categoria + "','" + precio + "')";
			stmt.executeUpdate(qery);
			stmt.close();
			modelo.insertRow(modelo.getRowCount(),
					new String[] { (modelo.getRowCount() + 1) + "", titulo, autor, categoria, precio.toString() });

		} catch (SQLException e) {
			miVista.cambiarError("Error al añadir registro");
			System.err.println(e);
		}
	}

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
				qry = "UPDATE BooksTable SET " + cabecera[i] + "='"+datos[i+1] +"' WHERE Titulo ='"+ seleccion+"';";
				System.out.println(qry);
				PreparedStatement pst;
				pst = conexion.prepareStatement(qry);
				System.out.println(pstmt);
				stmt.executeUpdate(qry);
				
			}
			// Para que la modificación del titulo no pise el resto
			qry = "UPDATE BooksTable SET " +  cabecera[0] + "='"+datos[1] +"' WHERE Titulo ='"+ seleccion+"';";
			pstmt = conexion.prepareStatement(qry);
			stmt.executeUpdate(qry);
			stmt.close();

			cargarTabla1();
			miVista.cambiarMsgResultado(
					"Los datos han sido modificados, se mostrarán al minimizar y reabrir la ventana.");

		} catch (SQLException e) {
			miVista.cambiarError("Error al modificar registro");
			System.err.println(e);
		}
	}

	public void mostrarSeleccion(String seleccion) {
		miVista.setSeleccion(seleccion);
	}

	public DefaultTableModel getModelo() {
		return modelo;
	}

	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}

}
