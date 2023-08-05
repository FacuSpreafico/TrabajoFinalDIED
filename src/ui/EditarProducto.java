package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import entidades.Producto;
import excepciones.NombreSucursalExistenteException;
import gestores.GestorProducto;
import gestores.GestorSucursal;
import main.Administrador;

public class EditarProducto extends JFrame {

	private JPanel contentPane;
	private JTextField campoNombre;
	private JTextField campoPrecio;
    private String nombre, horarioAbre, horarioCierre;
    private Integer capacidad;
    private JComboBox <String> comboBox;
    private JTextField campoPeso;
    private JTextField campoDescripcion;

	public EditarProducto(Producto producto) {
    setResizable(false);
		
		setTitle("Editar producto");
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
		lblNewLabel.setBounds(24, 28, 61, 20);
		panel.add(lblNewLabel);
		
		campoNombre = new JTextField();
		campoNombre.setBounds(95, 28, 110, 20);
		campoNombre.setText(producto.getNombre());
		panel.add(campoNombre);
		campoNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Precio");
		lblNewLabel_1.setBounds(262, 28, 61, 20);
		panel.add(lblNewLabel_1);
		
		campoPrecio = new JTextField();
		campoPrecio.setBounds(333, 28, 110, 20);
		panel.add(campoPrecio);
		String precio = String.valueOf(producto.getPrecio());
		campoPrecio.setColumns(10);
		campoPrecio.setText(precio);
		
		JLabel lblNewLabel_3 = new JLabel("Peso");
		lblNewLabel_3.setBounds(24, 85, 103, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("ARS");
		lblNewLabel_4.setBounds(453, 31, 46, 14);
		panel.add(lblNewLabel_4);
		
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBounds(110, 411, 89, 23);
		panel.add(cancelar);		
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setBounds(24, 411, 89, 23);
		panel.add(btnAplicar);
		
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					    Double peso = Double.parseDouble(campoPeso.getText().toString());
					    Double precio = Double.parseDouble(campoPrecio.getText().toString());
	                 	Administrador.getInstance().reemplazarDatosProducto(precio,campoNombre.getText().toString(),campoDescripcion.getText().toString(),peso,producto.getIdProducto());
	                 	
	                 	JOptionPane.showMessageDialog(rootPane, "Se modificó exitosamente el producto.","Exito",JOptionPane.PLAIN_MESSAGE, new ImageIcon("path/to/green_tick_icon.png"));

                        BuscarProducto buscar = new BuscarProducto();
						buscar.setVisible(true);
						dispose();
			           
					} catch (ClassNotFoundException e1) {
						
						JOptionPane.showMessageDialog(rootPane, "Error: No se pudo realizar la modificación.", "Error", JOptionPane.ERROR_MESSAGE);
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(rootPane, "Error:" + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						
					} 
				
				
			}
			
		});
		
		
		campoPeso = new JTextField();
		campoPeso.setText((String) null);
		campoPeso.setColumns(10);
		campoPeso.setBounds(95, 82, 110, 20);
		String peso = String.valueOf(producto.getPeso());
		campoPeso.setText(peso);
		panel.add(campoPeso);
		
		JLabel descrip = new JLabel("Descripcion");
		descrip.setBounds(24, 136, 71, 14);
		panel.add(descrip);
		
		campoDescripcion = new JTextField();
		campoDescripcion.setColumns(10);
		campoDescripcion.setBounds(95, 136, 353, 88);
		panel.add(campoDescripcion);
		campoDescripcion.setText(producto.getDescripcion());
		
		JLabel lblNewLabel_4_1 = new JLabel("KG");
		lblNewLabel_4_1.setBounds(208, 85, 46, 14);
		panel.add(lblNewLabel_4_1);
		
				
	}
}
