package Vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Controlador.Controlador;
import Modelo.Modelo;
import javax.swing.ListSelectionModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Controlador miControlador;
	private Modelo miModelo;
	private JTextField txtTitulo,txtAutor,txtCategoria, txtPrecio;
	JLabel lblError;


	public static void main(String[] args) {
		Vista frame = new Vista();
		frame.setVisible(true);
	}

	public Vista() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				table.setModel(miModelo.getModelo());
			}
		});
		setTitle("Tablas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 841, 789);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 257, 805, 304);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		JLabel lblBBDD = new JLabel("Base de Datos - Librería Colinas");
		lblBBDD.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblBBDD.setBounds(50, 0, 543, 68);
		contentPane.add(lblBBDD);
		
		JLabel lblTitulo = new JLabel("Título:");
		lblTitulo.setBounds(50, 116, 100, 20);
		contentPane.add(lblTitulo);
		
		txtTitulo = new JTextField();
		txtTitulo.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtTitulo.setBounds(100, 116, 269, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setBounds(50, 166, 100, 20);
		contentPane.add(lblAutor);
		
		txtAutor = new JTextField();
		txtAutor.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtAutor.setColumns(10);
		txtAutor.setBounds(100, 166, 269, 20);
		contentPane.add(txtAutor);
		
		JLabel lblCategora = new JLabel("Categoría:");
		lblCategora.setBounds(420, 116, 100, 20);
		contentPane.add(lblCategora);
		
		txtCategoria = new JTextField();
		txtCategoria.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtCategoria.setColumns(10);
		txtCategoria.setBounds(506, 116, 269, 20);
		contentPane.add(txtCategoria);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(420, 166, 100, 20);
		contentPane.add(lblPrecio);
		
		txtPrecio = new JTextField();
		txtPrecio.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				cambiarError("");
			}
		});
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(506, 166, 269, 20);
		contentPane.add(txtPrecio);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.annadirRegistro();
			}
		});
		btnAgregar.setBounds(349, 220, 89, 23);
		contentPane.add(btnAgregar);
		
		lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.RED);
		lblError.setBounds(280, 197, 248, 14);
		contentPane.add(lblError);
	}
	
	
	public String getTitulo() {
		return txtTitulo.getText();
	}
	public String getAutor() {
		return txtAutor.getText();
	}
	public String getCategoria() {
		return txtCategoria.getText();
	}
	public Double getPrecio() {
		double precio = Double.parseDouble(txtPrecio.getText());
		return precio;
	}
	
	
	//Comunicación entre clases
	public void setModelo(Modelo miModelo2) {
		this.miModelo = miModelo2;
	}

	public void setControlador(Controlador miControlador2) {
		this.miControlador = miControlador2; 
	}

	public void cambiarError(String msgError) {
		lblError.setText(msgError);	
	}
}
