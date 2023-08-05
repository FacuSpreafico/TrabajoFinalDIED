package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entidades.Camino;
import entidades.Sucursal;
import gestores.GestorCamino;
import gestores.GestorSucursal;

public class EditarCamino extends JFrame {

		private JPanel contentPane;
		private JTextField campoNombre;
		private JTextField campoTiempo;
		private JTextField campoCapacidad;
		private JComboBox<String> campoSucuOrigen;
		private JComboBox<String> campoSucuDestino;

		public EditarCamino(Camino c) {
			setResizable(false);
			
			setTitle("Editar Camino");
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			setBounds(100, 100, 600, 500);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Nombre");
			lblNewLabel.setBounds(24, 28, 78, 14);
			contentPane.add(lblNewLabel);
			
			campoNombre = new JTextField();
			campoNombre.setBounds(95, 28, 120, 20);
			campoNombre.setText(c.getNombre());
			contentPane.add(campoNombre);
			campoNombre.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Tiempo de transito");
			lblNewLabel_1.setBounds(286, 28, 120, 14);
			contentPane.add(lblNewLabel_1);
			
			campoTiempo = new JTextField();
			campoTiempo.setColumns(10);
			campoTiempo.setText(c.getTiempoRecorrido().toString());
			campoTiempo.setBounds(402, 25, 86, 20);
			contentPane.add(campoTiempo);
			
			JLabel lblNewLabel_2 = new JLabel("HS");
			lblNewLabel_2.setBounds(491, 28, 25, 14);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblSucursalOrigen = new JLabel("Sucursal origen");
			lblSucursalOrigen.setBounds(24, 85, 105, 14);
			contentPane.add(lblSucursalOrigen);
			
			ArrayList<String> listaOpciones = new ArrayList<String>();

			listaOpciones.addAll(GestorSucursal.getInstance().listaSucursales());
			
			campoSucuOrigen = new JComboBox<String>(listaOpciones.toArray(new String[0]));
			campoSucuOrigen.setBounds(137, 81, 120, 22);
			campoSucuOrigen.setSelectedItem(c.getInicio().getNombre());
			contentPane.add(campoSucuOrigen);
			
			JLabel lblSucursalDestino = new JLabel("Sucursal destino");
			lblSucursalDestino.setBounds(286, 85, 96, 14);
			contentPane.add(lblSucursalDestino);
			
			campoSucuDestino = new JComboBox<String>(listaOpciones.toArray(new String[0]));
			campoSucuDestino.setBounds(402, 81, 105, 22);
			campoSucuDestino.setSelectedItem(c.getFin().getNombre());
			contentPane.add(campoSucuDestino);
			campoSucuDestino.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 JComboBox comboBox = (JComboBox) e.getSource(); 
					String opcion = comboBox.getSelectedItem().toString();
				    completarCapacidad(opcion);
				}
				
			});
			
			JLabel lblNewLabel_3_1 = new JLabel("Capacidad de almacenamiento");
			lblNewLabel_3_1.setBounds(24, 143, 203, 14);
			contentPane.add(lblNewLabel_3_1);
			
			campoCapacidad = new JTextField();
			campoCapacidad.setEnabled(false);
			campoCapacidad.setEditable(false);
			campoCapacidad.setColumns(10);
			campoCapacidad.setBounds(210, 140, 134, 20);
			completarCapacidad(c.getFin().getNombre());
			contentPane.add(campoCapacidad);
			
			JButton botonAplicar = new JButton("Aplicar");
			botonAplicar.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) {    
					 
					Sucursal nuevaSucursalOrigen = GestorSucursal.getInstance().buscarSucursal(campoSucuOrigen.getSelectedItem().toString());
					Sucursal nuevaSucursalDestino = GestorSucursal.getInstance().buscarSucursal(campoSucuDestino.getSelectedItem().toString());
		            Time nuevoTiempo = Time.valueOf(campoTiempo.getText());
					try {
						GestorCamino.getInstance().editarCamino(c, campoNombre.getText(), nuevoTiempo, nuevaSucursalOrigen, nuevaSucursalDestino);
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(rootPane, "Se modificó exitosamente el camino.","Exito",JOptionPane.PLAIN_MESSAGE, new ImageIcon("path/to/green_tick_icon.png"));
					BuscarCamino buscar = null;
					try {
						buscar = new BuscarCamino();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					buscar.setVisible(true);
					dispose();
				 }
		    });
			
			botonAplicar.setBounds(24, 411, 89, 23);
			contentPane.add(botonAplicar);
			
			JButton botonCancelar = new JButton("Cancelar");
			botonCancelar.setBounds(111, 411, 89, 23);
			contentPane.add(botonCancelar);
			botonCancelar.addActionListener(volverAnterior());
			
			JLabel lblNewLabel_2_1 = new JLabel("KG");
			lblNewLabel_2_1.setBounds(343, 143, 25, 14);
			contentPane.add(lblNewLabel_2_1);
	
		}
		
		public void completarCapacidad(String nombre) {
			Sucursal s = GestorSucursal.getInstance().buscarSucursal(nombre);
			campoCapacidad.setText(String.valueOf(s.getCapacidad()));
		}
	
	ActionListener volverAnterior() {
        ActionListener a= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BuscarCamino pantalla1 = null;
				try {
					pantalla1 = new BuscarCamino();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                //pantalla1.cargarDatos(nombre,idSucursal,capacidad,comboBox);
                pantalla1.setVisible(true);
                dispose();
                
        }
    };
        return a;
    }
	
	
	
	
	/*
	void nombreSucu (String nombre) {
		this.nombre= nombre;
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
	}*/

}
	
	
