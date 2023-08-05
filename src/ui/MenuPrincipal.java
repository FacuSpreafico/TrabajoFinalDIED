package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Sucursal;
import excepciones.NoExisteSucursalException;
import gestores.GestorCamino;
import gestores.GestorSucursal;
import main.Administrador;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class MenuPrincipal extends JFrame {

	private JPanel contentPane;

	public MenuPrincipal() {
		setResizable(false);
	
		setTitle("Menu de funcionalidades");
        setSize(300, 200);
    	setLocationRelativeTo(null); // Centrar la ventana
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Menú de funcionalidades");
		lblNewLabel.setBounds(210, 24, 165, 14);
		contentPane.add(lblNewLabel);
		
		lblNewLabel.setFont( new Font("Default",Font.PLAIN,15));
		
		JButton btnBuscarSucursal = new JButton("Sucursales");
		btnBuscarSucursal.setForeground(SystemColor.desktop);
		btnBuscarSucursal.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBuscarSucursal.setBounds(41, 81, 493, 37);
		contentPane.add(btnBuscarSucursal);

        btnBuscarSucursal.addActionListener(buscarSucursal());
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.setForeground(SystemColor.desktop);
		btnProductos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnProductos.setBounds(41, 149, 493, 37);
		contentPane.add(btnProductos);
		
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarProducto producto;
				try {
					producto = new BuscarProducto();
					producto.setVisible(true);
				    dispose();
				} catch (ClassNotFoundException | SQLException e1) {
					
				}
				
			}
		});
		
		JButton btnCaminos = new JButton("Caminos");
		btnCaminos.setForeground(SystemColor.desktop);
		btnCaminos.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCaminos.setBounds(41, 218, 493, 37);
		contentPane.add(btnCaminos);
		
		btnCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarCamino buscar;
				try {
					buscar = new BuscarCamino();
					buscar.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
				
				}
				
	
			}
			});
		
		
		
		JButton btnOrdenes = new JButton("Ordenes");
		btnOrdenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					OrdenesDeProvision orden;
					try {
						orden = new OrdenesDeProvision();
						orden.setVisible(true);
						dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						
					}
					
			
				
			
			}});
		
		btnOrdenes.setForeground(SystemColor.desktop);
		btnOrdenes.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnOrdenes.setBounds(41, 284, 493, 37);
		contentPane.add(btnOrdenes);
		
		
	}
	ActionListener abrirRegistrarSucu () {
        ActionListener a=new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrarSucu registrar = new RegistrarSucu();
                registrar.setVisible(true);
                dispose();
        }
    };
        return a;
    }
	
	ActionListener buscarSucursal () {
        ActionListener b= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BuscarSucu buscar = null;
				try {
					buscar = new BuscarSucu();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                buscar.setVisible(true);
                dispose();
        }
    };
        return b;
    }
	
}
	
	
	
	
	
	
	

