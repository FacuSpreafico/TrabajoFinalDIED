package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.sql.Time;

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
import javax.swing.border.MatteBorder;

import entidades.Sucursal;
import excepciones.NoSeleccionaTuplaException;
import excepciones.NombreSucursalExistenteException;
import gestores.GestorSucursal;
import main.Administrador;

public class EditarSucu extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JComboBox comboBox_1,comboBox_1_1;
    private String nombre, horarioAbre, horarioCierre;
    private Integer capacidad;
    private JComboBox <String> comboBox;

	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public EditarSucu(String name, String horarioA, String horarioC, String capacidS) throws NoSeleccionaTuplaException{
		setResizable(false);
		
		if (name == null && horarioA == null && horarioC == null && capacidS == null) {
			throw new NoSeleccionaTuplaException();
		}
		
		this.nombre=name;
		this.horarioAbre=horarioA;
		this.horarioCierre=horarioC;
		int capacid= Integer.parseInt(capacidS);
		this.capacidad=capacid;
		setTitle("Editar sucursal");
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
		
		textField = new JTextField();
		textField.setBounds(95, 28, 110, 20);
		textField.setText(this.nombre);
		panel.add(textField);
		textField.setColumns(10);
		
		onlyLet(textField);
		
		
		JLabel lblNewLabel_1 = new JLabel("Capacidad");
		lblNewLabel_1.setBounds(262, 28, 61, 20);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(333, 28, 110, 20);
		textField_1.setText(this.capacidad.toString());
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		onlyNum(textField_1);
		
		JLabel lblNewLabel_3 = new JLabel("Horario Apertura");
		lblNewLabel_3.setBounds(24, 85, 103, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Horario Cierre");
		lblNewLabel_3_1.setBounds(24, 143, 103, 14);
		panel.add(lblNewLabel_3_1);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00", "00:00:00"}));
		comboBox_1.setBounds(137, 81, 68, 22);
		comboBox_1.setSelectedItem(this.horarioAbre);
		panel.add(comboBox_1);
		
		comboBox_1_1 = new JComboBox();
		comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00", "21:30:00", "22:00:00", "22:30:00", "23:00:00", "23:30:00", "00:00:00"}));
		comboBox_1_1.setBounds(137, 139, 68, 22);
		comboBox_1_1.setSelectedItem(this.horarioCierre);
		panel.add(comboBox_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("KG");
		lblNewLabel_4.setBounds(453, 31, 46, 14);
		panel.add(lblNewLabel_4);
		
		JButton cancelar = new JButton("Cancelar");
		cancelar.setBounds(110, 411, 89, 23);
		panel.add(cancelar);
		
		cancelar.addActionListener(volverAnterior());		
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setBounds(24, 411, 89, 23);
		panel.add(btnAplicar);
				
		btnAplicar.addActionListener(aplicar(sucursalAEditar(this.nombre)));
		
		
	}
	
	ActionListener volverAnterior() {
        ActionListener a= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BuscarSucu pantalla1 = null;
				try {
					pantalla1 = new BuscarSucu();
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
	
	Sucursal sucursalAEditar(String nombre) {
		for(Sucursal sucu: GestorSucursal.getInstance().getSucursales()) {
    		if(sucu.getNombre().equals(nombre)) {
    			//System.out.print(sucu.getNombre()+"nombre");
    			return sucu;
    		}
    		
    	}
		return null; //hacer la exception
	}
	
	ActionListener aplicar(final Sucursal sucursa) {
        ActionListener a= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	 try {
            		GestorSucursal.getInstance().compararNombreSucursal(textField.getText()); 
            
                 	sucursa.setNombre(textField.getText());
                 	int capacidad= Integer.parseInt(textField_1.getText());
                 	sucursa.setCapacidad(capacidad);
                 	Time horarioAbre= Time.valueOf(comboBox_1.getSelectedItem().toString());
     				Time horarioCierra= Time.valueOf(comboBox_1_1.getSelectedItem().toString());
                 	sucursa.setHorarioApertura(horarioAbre);
                 	sucursa.setHorarioCierre(horarioCierra);
                 	
                 	Administrador.getInstance().reemplazarDatosSucursal(sucursa);
                 	
                 	JOptionPane.showMessageDialog(rootPane, "Se modificó exitosamente la sucursal.","Exito",JOptionPane.PLAIN_MESSAGE, new ImageIcon("path/to/green_tick_icon.png"));
					BuscarSucu buscar = new BuscarSucu();
					buscar.setVisible(true);
					dispose();
		           
				} catch (ClassNotFoundException e1) {
					
					JOptionPane.showMessageDialog(rootPane, "Error: No se pudo realizar la modificación.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException | NombreSucursalExistenteException e1) {
					JOptionPane.showMessageDialog(rootPane, "Error: No se pudo realizar la modificación, el nombre de la sucursal ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
					
				} 
            	
            	
        }
    };
        return a;
    }
	
	
	
	
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
	}
	
	
}