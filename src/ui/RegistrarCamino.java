package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entidades.Sucursal;
import excepciones.CampoCaminoNoIngresadoException;
import excepciones.NombreCaminoExistenteException;
import excepciones.SucursalesIgualesException;
import gestores.GestorCamino;
import gestores.GestorSucursal;

public class RegistrarCamino extends JFrame {

	private JPanel contentPane;
	private JTextField campoNombre;
	private JTextField campoTiempo;
	private JTextField campoCapacidad;
	private JComboBox<String> campoEstado;
	private JComboBox<String> campoSucuOrigen;
	private JComboBox<String> campoSucuDestino;

	public RegistrarCamino() {
		setResizable(false);
		setTitle("Registrar Camino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(24, 67, 78, 14);
		contentPane.add(lblNewLabel);
		
		campoNombre = new JTextField();
		campoNombre.setBounds(112, 64, 120, 20);
		contentPane.add(campoNombre);
		campoNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tiempo de transito");
		lblNewLabel_1.setBounds(262, 67, 120, 14);
		contentPane.add(lblNewLabel_1);
		
		campoTiempo = new JTextField();
		campoTiempo.setColumns(10);
		campoTiempo.setBounds(380, 64, 86, 20);
		contentPane.add(campoTiempo);
		
		JLabel lblNewLabel_2 = new JLabel("HS");
		lblNewLabel_2.setBounds(476, 67, 25, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblSucursalOrigen = new JLabel("Sucursal origen");
		lblSucursalOrigen.setBounds(24, 121, 105, 14);
		contentPane.add(lblSucursalOrigen);
		
		
		//int tamanio = GestorSucursal.getInstance().listaSucursales().length;
		//String[] listaOpciones = new String[tamanio];
		//listaOpciones[0] = "No seleccionado";
		//for(int i=1; i < tamanio; i++) {
		//	listaOpciones[i] = GestorSucursal.getInstance().listaSucursales()[i-1];
		//}
		
		ArrayList<String> listaOpciones = new ArrayList<String>();
		listaOpciones.add("No seleccionado");
		listaOpciones.addAll(GestorSucursal.getInstance().listaSucursales());
		
		campoSucuOrigen = new JComboBox<String>(listaOpciones.toArray(new String[0]));
		campoSucuOrigen.setBounds(130, 117, 120, 22);
		contentPane.add(campoSucuOrigen);
		
		JLabel lblSucursalDestino = new JLabel("Sucursal destino");
		lblSucursalDestino.setBounds(302, 121, 96, 14);
		contentPane.add(lblSucursalDestino);
		
		campoSucuDestino = new JComboBox<String>(listaOpciones.toArray(new String[0]));
		campoSucuDestino.setBounds(410, 117, 105, 22);
		contentPane.add(campoSucuDestino);
		campoSucuDestino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 JComboBox comboBox = (JComboBox) e.getSource(); 
				String opcion = comboBox.getSelectedItem().toString();
			     
			     if(opcion != "No seleccionado") {
			    	 completarCapacidad(opcion);
			     }
			     else {
			    	 campoCapacidad.setText("");
			     }
			}
			
		});
		
		JLabel lblNewLabel_3 = new JLabel("Estado");
		lblNewLabel_3.setBounds(24, 175, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		campoEstado = new JComboBox<String>();
		campoEstado.setModel(new DefaultComboBoxModel(new String[] {"No seleccionado","Operativa", "No operativa"}));
		campoEstado.setBounds(130, 171, 120, 22);
		contentPane.add(campoEstado);
		
		JLabel lblNewLabel_3_1 = new JLabel("Capacidad de almacenamiento");
		lblNewLabel_3_1.setBounds(24, 226, 203, 14);
		contentPane.add(lblNewLabel_3_1);
		
		campoCapacidad = new JTextField();
		campoCapacidad.setEnabled(false);
		campoCapacidad.setEditable(false);
		campoCapacidad.setColumns(10);
		campoCapacidad.setBounds(205, 223, 134, 20);
		contentPane.add(campoCapacidad);
		
		JButton botonRegistrar = new JButton("Registrar");
		botonRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Sucursal inicio = GestorSucursal.getInstance().buscarSucursal(campoSucuOrigen.getSelectedItem().toString());
				Sucursal fin = GestorSucursal.getInstance().buscarSucursal(campoSucuDestino.getSelectedItem().toString());
				String formato = campoTiempo.getText();
				if(formato.isEmpty()) {
					formato = "00:00:00";
				}
				else {
					formato += ":00:00";
				}
				try {
					GestorCamino.getInstance().registrarCamino(campoNombre.getText().toString(), formato ,inicio, fin, campoEstado.getSelectedItem().toString(), rootPane);
					JOptionPane.showMessageDialog(rootPane, "Se registro correctamente el camino","Exito",JOptionPane.PLAIN_MESSAGE, new ImageIcon("path/to/green_tick_icon.png"));
					MenuPrincipal menu = new MenuPrincipal();
					menu.setVisible(true);
					dispose();
					
				} catch (CampoCaminoNoIngresadoException e1) {
					JOptionPane.showMessageDialog(rootPane, "Error: No se ingresaron alguno de los datos, verifique e intentelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (NombreCaminoExistenteException e1) {
					JOptionPane.showMessageDialog(rootPane, "Error: El nombre del camino ya existe, verifique e intentelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (SucursalesIgualesException e1) {
					JOptionPane.showMessageDialog(rootPane, "Error: Sucursal origen y Sucursal destino deben ser distintas, verifique e intentelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
			}
			
		);
		botonRegistrar.setBounds(24, 395, 89, 23);
		contentPane.add(botonRegistrar);
		
		JButton botonCancelar = new JButton("Cancelar");
		botonCancelar.setBounds(111, 395, 89, 23);
		contentPane.add(botonCancelar);
		botonCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                MenuPrincipal buscar = new MenuPrincipal();
                buscar.setVisible(true);
                dispose();
			}
		});
		
		JLabel lblNewLabel_2_1 = new JLabel("KG");
		lblNewLabel_2_1.setBounds(349, 226, 25, 14);
		contentPane.add(lblNewLabel_2_1);
		
	}
	
	public void completarCapacidad(String nombre) {
		Sucursal s = GestorSucursal.getInstance().buscarSucursal(nombre);
		campoCapacidad.setText(String.valueOf(s.getCapacidad()));
	}
	
	
}
