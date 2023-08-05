package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entidades.Producto;
import gestores.GestorProducto;
import main.Administrador;

public class BuscarProducto extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JComboBox<String> comboBox_1;
	
	public BuscarProducto() throws ClassNotFoundException, SQLException {
		setResizable(false);
		setTitle("Productos");
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1_1 = new JButton("Aplicar");
		btnNewButton_1_1.setBounds(29, 409, 89, 23);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton_1_1);
		

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(119, 409, 89, 23);
		contentPane.add(btnCancelar);
		
		btnCancelar.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            MenuPrincipal menu = new MenuPrincipal();
		            menu.setVisible(true);
		            dispose();
		        	}});
		    
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 202, 469, 118);
		contentPane.add(scrollPane);
	
		table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	    datosTabla();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(29, 184, 523, 180);
		contentPane.add(panel_1);
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		panel_1.setLayout(null);
		
		JButton bEditar = new JButton("Editar producto");
		bEditar.setBounds(164, 146, 135, 23);
		panel_1.add(bEditar);
		
		bEditar.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				int idProducto = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
				EditarProducto editar = new EditarProducto(GestorProducto.getInstance().buscarProductoXID(idProducto));
				editar.setVisible(true);
				dispose();
			}
			
		});
		
		JButton btnDarBaja = new JButton("Eliminar producto");
		btnDarBaja.setBounds(299, 146, 148, 23);
		panel_1.add(btnDarBaja);
		
		JButton btnAgregar = new JButton("Agregar producto");
		btnAgregar.setBounds(26, 146, 140, 23);
		panel_1.add(btnAgregar);
		
		btnAgregar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        RegistrarProdu reg = new RegistrarProdu();
	        reg.setVisible(true);
            dispose();
	        }});
		
		
		JPanel panel = new JPanel();
		panel.setBounds(29, 55, 523, 118);
		panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre");
	    lblNewLabel.setBounds(10, 55, 46, 14);
		panel.add(lblNewLabel);

	    textField = new JTextField();
	    textField.addKeyListener(new KeyAdapter() {
	    	@Override
	    	public void keyReleased(KeyEvent e) {
	    		String M_datos[][]= null;
				TableRowSorter<TableModel> trs; 
				String[] Titulos = {"IDProducto", "Precio", "Nombre", "Descripcion", "Peso"}; 
			    DefaultTableModel dtm_datos = new DefaultTableModel(); 
				try {
					M_datos = Administrador.getInstance().contarDatosQueryProductos(textField);
					
				} catch (ClassNotFoundException | SQLException e1) {
					
				}
				dtm_datos = new DefaultTableModel(M_datos, Titulos) {
                public boolean isCellEditable(int row, int column) {
                return false;  
                }
                };
                table.setModel(dtm_datos);
                trs = new TableRowSorter<>(dtm_datos);
                table.setRowSorter(trs);
	    	}
	    });
	    
	    
	    textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(87, 52, 109, 20);
	    panel.add(textField);
		textField.setColumns(10);
				
	}
	private void datosTabla() throws ClassNotFoundException, SQLException {
		String[] Titulos = {"IDProducto", "Precio", "Nombre", "Descripcion", "Peso"}; //Arreglo de los titulos para la tabla
	    DefaultTableModel dtm_datos = new DefaultTableModel(); //creamos  un modelo para la taba de datos
	    TableRowSorter<TableModel> trs; //Hacemos el table row sorter para poder ordenar la tabla al presionar los encabezados de la misma
	    
	    String[][] M_datos = null;  //iniciamos una matriz donde pasaremos los datos de sql
	    Administrador.getInstance();   //iniciamos un objeto que se encargara de la conexion de datos
		
	  
		Integer contador = 0;  //creamos un contador para saber el numero de datos que obtendremos de la tabla datos de sql
                                                                      //para las consultas sql siempre vamos a ocupar un try catch por su ocurre un error
        contador = Administrador.getInstance().contarDatosProducto();
       
        M_datos= Administrador.getInstance().cargarDatosProducto(contador);
            
        dtm_datos = new DefaultTableModel(M_datos, Titulos) { //ahora agregaremos la matriz y los titulos al modelo de tabla
            public boolean isCellEditable(int row, int column) {//este metodo es muy util si no quieren que editen su tabla, 
                return false;  //si quieren modificar los campos al dar clic entonces borren este metodo
            }
        };
        table.setModel(dtm_datos); //ahora el modelo que ya tiene tanto los datos como los titulos lo agregamos a la tabla
        trs = new TableRowSorter<>(dtm_datos); //iniciamos el table row sorter para ordenar los datos (esto es si gustan)
        table.setRowSorter(trs); //y lo agregamos al jtable
	}

}
