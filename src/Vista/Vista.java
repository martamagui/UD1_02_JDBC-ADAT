/**
 * @author Marta Molina Aguilera
 */
package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controlador.Controlador;
import Modelo.Modelo;
import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase la cual implementa todos los atributos visuales de la app.
 * 
 */
@SuppressWarnings("serial")
public class Vista extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Controlador miControlador;
	private Modelo miModelo;
	private JTextField txtTitulo, txtAutor, txtCategoria, txtPrecio;
	private JLabel lblError, lblSeleccion;
	private String tituloSeleccionado;
	private JLabel lblRectangle;
	private static ImageIcon img = new ImageIcon("imgs/martaMolina.png");

	/**
	 * Constructor de la clase, aplica todos los atributos visuales a la ventana.
	 */
	public Vista() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				table.setModel(miModelo.getModelo());
			}
		});
		setTitle("Marta MA - UD1_02_JDBC");
		setIconImage(img.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 690);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(242, 232, 207));
		contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		UIManager.put("Button.select", new Color(56, 102, 65));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(60, 307, 805, 304);
		scrollPane.setBorder(new EmptyBorder(getInsets()));
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				miControlador.mostrarSeleccion();
			}
		});
		table.setBorder(new EmptyBorder(getInsets()));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		JLabel lblBBDD = new JLabel("Base de Datos - Librería Colinas");
		lblBBDD.setForeground(new Color(56, 102, 65));
		lblBBDD.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblBBDD.setBounds(60, 45, 543, 68);
		contentPane.add(lblBBDD);

		JLabel lblTitulo = new JLabel("Título:");
		lblTitulo.setForeground(new Color(56, 102, 65));
		lblTitulo.setBounds(60, 140, 100, 30);
		contentPane.add(lblTitulo);

		txtTitulo = new JTextField();
		txtTitulo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtTitulo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		txtTitulo.setMargin(new Insets(2, 22, 2, 22));
		txtTitulo.setBounds(119, 140, 300, 30);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);

		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setForeground(new Color(56, 102, 65));
		lblAutor.setBounds(470, 140, 100, 30);
		contentPane.add(lblAutor);

		txtAutor = new JTextField();
		txtAutor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		txtAutor.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtAutor.setColumns(10);
		txtAutor.setBounds(556, 140, 300, 30);
		contentPane.add(txtAutor);

		JLabel lblCategora = new JLabel("Categoría:");
		lblCategora.setForeground(new Color(56, 102, 65));
		lblCategora.setBounds(60, 200, 100, 30);
		contentPane.add(lblCategora);

		txtCategoria = new JTextField();
		txtCategoria.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		txtCategoria.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(119, 200, 300, 30);
		contentPane.add(txtCategoria);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setForeground(new Color(56, 102, 65));
		lblPrecio.setBounds(470, 200, 100, 30);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		txtPrecio.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(556, 200, 300, 30);
		contentPane.add(txtPrecio);

		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setToolTipText("Agregar");
		btnAgregar.setBackground(new Color(106, 153, 78));
		btnAgregar.setForeground(new Color(242, 232, 207));
		btnAgregar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.annadirRegistro();

			}
		});
		btnAgregar.setBounds(119, 260, 95, 30);
		contentPane.add(btnAgregar);

		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.LEFT);
		lblError.setBounds(119, 238, 508, 14);
		contentPane.add(lblError);

		JButton btnModificar = new JButton("Modificar");
		btnModificar.setToolTipText("Modificar");
		btnModificar.setBorder(new EmptyBorder(getInsets()));
		btnModificar.setBackground(new Color(106, 153, 78));
		btnModificar.setForeground(new Color(242, 232, 207));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.modificarRegistro();
			}
		});
		btnModificar.setBounds(222, 260, 95, 30);
		contentPane.add(btnModificar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setToolTipText("Eliminar");
		btnEliminar.setBorder(new EmptyBorder(getInsets()));
		btnEliminar.setBackground(new Color(106, 153, 78));
		btnEliminar.setForeground(new Color(242, 232, 207));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.borrarRegistro();
			}
		});
		btnEliminar.setBounds(324, 260, 95, 30);
		contentPane.add(btnEliminar);

		lblSeleccion = new JLabel("Selección:");
		lblSeleccion.setForeground(new Color(56, 102, 65));
		lblSeleccion.setBounds(470, 260, 386, 30);
		contentPane.add(lblSeleccion);

		lblRectangle = new JLabel("");
		ImageIcon rectangle1 = new ImageIcon("imgs/Rectangle1.png");
		Image newRecImg1 = rectangle1.getImage().getScaledInstance(885, 619, java.awt.Image.SCALE_SMOOTH);
		lblRectangle.setIcon(new ImageIcon(newRecImg1));
		lblRectangle.setBounds(20, 22, 885, 619);
		contentPane.add(lblRectangle);
	}

	/**
	 * Método el cual quita una fila de la tabla.
	 */
	public void borrarFila() {
		int fila = table.getSelectedRow();
		((DefaultTableModel) table.getModel()).removeRow(fila);
	}

	// --------- Getters campos de texto ---------

	/**
	 * Método el cual devuelve lo escrito en el campo de texto del título.
	 * 
	 * @return txtTitulo.getText();
	 */
	public String getTitulo() {
		return txtTitulo.getText();
	}

	/**
	 * Método el cual devuelve lo escrito en el campo de texto del autor.
	 * 
	 * @return txtAutor.getText(); Tipo String.
	 */
	public String getAutor() {
		return txtAutor.getText();
	}

	/**
	 * Método el cual devuelve lo escrito en el campo de texto de la categoría.
	 * 
	 * @return txtCategoria.getText(); Tipo String.
	 */
	public String getCategoria() {
		return txtCategoria.getText();
	}

	/**
	 * Método el cual devuelve lo escrito en el campo de texto del precio.
	 * 
	 * @return txtPrecio.getText(); Tipo String.
	 */
	public String getPrecio() {
		return txtPrecio.getText();
	}

	// --------- Getter selecciones de la tabla ---------

	/**
	 * Método el cual devulve el título del elemento seleccionado.
	 * 
	 * @return seleccion: titulo del elemento seleccionado.Tipo String.
	 */
	public String getSeleccionTitulo() {
		int fila = table.getSelectedRow();
		String seleccion = table.getValueAt(fila, 0).toString();
		tituloSeleccionado = seleccion;
		return seleccion;
	}

	/**
	 * Método el cual devulve el String del tituloSeleccionado
	 * 
	 * @return tituloSeleccionado: la categoría del elemento seleccionado, tipo
	 *         String.
	 */
	public String getTituloSeleccionado() {
		return tituloSeleccionado;
	}

	// --------- Setters ---------

	/**
	 * Función la cual asigna el String pasado por parámetro a la lblSeleccion
	 * 
	 * @param seleccion: Título del elemento seleccionado, tipo String.
	 */
	public void setSeleccion(String seleccion) {
		lblSeleccion.setText("Selección: " + seleccion);
	}

	/**
	 * Función la cual asigna color y mensaje a la etiqueta de error.
	 * 
	 * @param msgError: Tipo String, mensaje de error/resultado.
	 */
	public void cambiarError(String msgError) {
		lblError.setText(msgError);
		lblError.setForeground(new Color(188, 71, 73));
	}

	/**
	 * Función la cual asigna color y mensaje a la etiqueta de error.
	 * 
	 * @param msgError: Tipo String, mensaje de error/resultado.
	 */
	public void cambiarMsgResultado(String resultado) {
		lblError.setText(resultado);
		lblError.setForeground(new Color(106, 153, 78));
	}
	// --------- Comunicación entre clases ---------

	/**
	 * Método el cual asigna las propiedades del objeto Modelo y hace posible la
	 * comunicación entre clases.
	 * 
	 * @param miModelo: Objeto de la clase Modelo.
	 */
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	/**
	 * Método el cual asigna las propiedades del objeto Controlador y hace posible
	 * la comunicación entre clases.
	 * 
	 * @param miControlador
	 */
	public void setControlador(Controlador miControlador) {
		this.miControlador = miControlador;
	}
}
