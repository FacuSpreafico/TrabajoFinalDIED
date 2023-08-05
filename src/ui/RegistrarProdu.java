package ui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import entidades.Producto;
import gestores.GestorProducto;
import main.Administrador;

public class RegistrarProdu extends JFrame {

	private JPanel contentPane;
	private JTextField textnombre;
	private JTextField textpeso;
	private JTextField textprecio;
	private JTextField textdescripcion;

	public RegistrarProdu() {
		setResizable(false);
		setTitle("Registrar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(5, 5, 569, 445);
		panel.setForeground(new Color(0, 0, 0));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(24, 67, 61, 20);
		panel.add(lblNewLabel);
		
		textnombre = new JTextField();
		textnombre.setBounds(95, 67, 110, 20);
		panel.add(textnombre);
		textnombre.setColumns(10);
		
		onlyLet(textnombre);
		
		JLabel lblNewLabel_1 = new JLabel("Peso");
		lblNewLabel_1.setBounds(24, 118, 61, 20);
		panel.add(lblNewLabel_1);
		
		textpeso = new JTextField();
		textpeso.setBounds(95, 118, 110, 20);
		panel.add(textpeso);
		textpeso.setColumns(10);
		
		onlyNum(textpeso);
		
		JLabel lblNewLabel_3 = new JLabel("Descripcion");
		lblNewLabel_3.setBounds(24, 171, 103, 14);
		panel.add(lblNewLabel_3);
		
		
		
		JLabel lblNewLabel_4 = new JLabel("KG");
		lblNewLabel_4.setBounds(215, 121, 46, 14);
		panel.add(lblNewLabel_4);
		
		JButton registrar = new JButton("Registrar");
		registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				double precio=Double.parseDouble(textprecio.getText());
				double peso=Double.parseDouble(textpeso.getText());
					
			try {
				GestorProducto.getInstance().registrarProductol(textnombre.getText(),precio,textdescripcion.getText(),peso);
				Producto producto = new Producto(textnombre.getText(),precio,textdescripcion.getText(),peso);
				Administrador.getInstance().registrarProducto(producto);
				JOptionPane.showMessageDialog(rootPane, "Se registro correctamente el producto.","Exito",JOptionPane.PLAIN_MESSAGE);
				
			} catch (ClassNotFoundException | SQLException e1) {
				JOptionPane.showMessageDialog(rootPane, "Error: No se registro correctamente el producto.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		    }
			});
		
		registrar.setBounds(24, 395, 89, 23);
		panel.add(registrar);
		
		
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBounds(110, 395, 95, 23);
		panel.add(cancelar);
		
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarProducto buscar;
				try {
					buscar = new BuscarProducto();
					buscar.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
				}
				
			}
			
			
			
		});
		
		
		JLabel lblNewLabel_1_1 = new JLabel("Precio");
		lblNewLabel_1_1.setBounds(262, 67, 61, 20);
		panel.add(lblNewLabel_1_1);
		
		textprecio = new JTextField();
		textprecio.setColumns(10);
		textprecio.setBounds(313, 67, 110, 20);
		panel.add(textprecio);
		
		JLabel lblNewLabel_4_1 = new JLabel("ARS");
		lblNewLabel_4_1.setBounds(436, 70, 46, 14);
		panel.add(lblNewLabel_4_1);
		
		textdescripcion = new JTextField();
		textdescripcion.setBounds(95, 171, 387, 88);
		panel.add(textdescripcion);
		textdescripcion.setColumns(10);
		
	}

	private void onlyNum (final JTextField texto) {
		texto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '.') {
					e.consume();
				}
				if (c == '.' && texto.getText().contains(".")) {
					e.consume();
				}
			}
		});	
	}

	private void onlyLet (final JTextField texto) {
		texto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isAlphabetic(c) || c == '.') {
					e.consume();
				}
			}
		});	
	}
}