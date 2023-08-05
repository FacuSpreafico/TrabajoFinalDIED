package ui;

import java.awt.Color;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entidades.Sucursal;
import gestores.GestorProducto;
import gestores.GestorStock;
import gestores.GestorSucursal;
import main.Administrador;

public class ProductosSucursal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;


	public ProductosSucursal(Sucursal sucu) throws ClassNotFoundException, SQLException {
		setResizable(false);
		setTitle("Menu de stock");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Productos"));
		panel.setBounds(29, 45, 528, 276);
		getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 508, 175);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSurrendersFocusOnKeystroke(true);
		datosTabla(sucu);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(10, 45, 46, 14);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String M_datos[][]= null;
				TableRowSorter<TableModel> trs; 
				String[] Titulos = {"IdProducto","Nombre", "Peso", "Precio", "Stock"}; 
			    DefaultTableModel dtm_datos = new DefaultTableModel(); 
				try {
					M_datos = Administrador.getInstance().contarDatosQueryProducto(textField, sucu);
				} catch (ClassNotFoundException | SQLException | ArrayIndexOutOfBoundsException e1) {
					e1.printStackTrace();
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
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(68, 42, 109, 20);
		panel.add(textField);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(28, 414, 95, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.setBounds(123, 414, 89, 23);
		getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BuscarSucu buscar;
				try {
					buscar = new BuscarSucu();
					buscar.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		
		JButton btnNewButton_2 = new JButton("Agregar nuevo producto");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] productos = GestorProducto.getInstance().getNombresProductos();
		        JComboBox<String> comboBox = new JComboBox<>(productos);
		        JTextField textField = new JTextField(10);

		        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 5));
		        panel.add(new JLabel("Producto:"));
		        panel.add(comboBox);
		        panel.add(new JLabel("Stock:"));
		        panel.add(textField);
		        int result = JOptionPane.showConfirmDialog(rootPane, panel, "Agregar Stock",
		                 JOptionPane.PLAIN_MESSAGE);
		        try {
		        if (result == JOptionPane.OK_OPTION) {
		            String productoSeleccionado = comboBox.getSelectedItem().toString();
		            int stockIngresado = Integer.parseInt(textField.getText().toString());
						GestorStock.getInstance().insertarStock(GestorSucursal.getInstance().buscarSucursal(sucu.getNombre()), GestorProducto.getInstance().buscarProducto(productoSeleccionado), stockIngresado);
						ProductosSucursal prod = new ProductosSucursal(sucu);
						prod.setVisible(true);
						dispose();
			    } 
		        }
		        catch (ClassNotFoundException | SQLException | NumberFormatException e1) {
				}
		           
		            

		            // Aquí puedes agregar la lógica para aplicar los cambios o realizar alguna acción con los valores seleccionados
		            // Por ejemplo, guardar el stock ingresado en tu base de datos.
		        } 
			
		});
	


		btnNewButton_2.setBounds(376, 332, 181, 23);
		getContentPane().add(btnNewButton_2);
	
		JButton btnNewButton_3 = new JButton("Editar stock seleccionado");
			
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
				if ((table.getValueAt(table.getSelectedRow(), 1) != null)) {
				
				
				JPanel panel = new JPanel(new GridLayout(2, 2));
		        JLabel label = new JLabel("Stock:");
		        JTextField textField = new JTextField(10);
		        panel.add(label);
		        panel.add(textField);
		        
				int option = JOptionPane.showOptionDialog(rootPane,
		                panel,
		                "Ingresar Stock",
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                new Object[]{"Aplicar", "Cancelar"},
		                "Aplicar");

		        if (option == JOptionPane.OK_OPTION) {
		          
		            String stockText = textField.getText();
		            int cant = Integer.parseInt(stockText);
		            try {
						GestorStock.getInstance().editarStock(sucu.getNombre(), (table.getValueAt(table.getSelectedRow(),1).toString()), cant);
						ProductosSucursal pr = new ProductosSucursal(GestorSucursal.getInstance().buscarSucursal(sucu.getNombre()));
						pr.setVisible(true);
						dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					
		           
		        } else {
		        }
		        SwingUtilities.invokeLater(() -> textField.requestFocusInWindow());
		    }
			}	
			catch(IndexOutOfBoundsException e1) {
				
			}
				
			}
		});
		
		
		
		btnNewButton_3.setBounds(28, 332, 164, 23);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Eliminar producto seleccionado");
		//sucu.eliminarProducto(table.getValueAt(table.getSelectedRow(),1).toString());
		btnNewButton_4.setBounds(192, 332, 185, 23);
		getContentPane().add(btnNewButton_4);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(128, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if ((table.getValueAt(table.getSelectedRow(), 1) != null)) {
				
				Object[] options = { "Aceptar", "Cancelar" };
		        int option = JOptionPane.showOptionDialog(
		                rootPane,
		                "¿Desea eliminar este producto en stock?",
		                "Confirmación",
		                JOptionPane.DEFAULT_OPTION,
		                JOptionPane.QUESTION_MESSAGE,
		                null,
		                options,
		                options[1] // El botón "Cancelar" será el botón predeterminado
		        );

		        if (option == JOptionPane.YES_OPTION) {
		            int idProducto= Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		        	int stock = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 4).toString());
		            try {
						GestorStock.getInstance().eliminarStock(sucu.getIdSucursal(), idProducto, stock );
						ProductosSucursal pr = new ProductosSucursal(sucu);
						pr.setVisible(true);
						dispose();
					} catch (ClassNotFoundException | SQLException e1) {
						
					}
		        } else {
		            
		        }
					}
				}
				catch (IndexOutOfBoundsException e1) {
					
				}
			}
			});
		
	}
	
	
	private void datosTabla(Sucursal sucu) throws ClassNotFoundException, SQLException {
		String[] Titulos = {"IdProducto","Nombre", "Peso", "Precio", "Stock"}; 
	    DefaultTableModel dtm_datos = new DefaultTableModel(); 
	    TableRowSorter<TableModel> trs; 
	    
	    String[][] M_datos = null;  
	  
		Integer contador = 0;  //creamos un contador para saber el numero de datos que obtendremos de la tabla datos de sql
       
        //para las consultas sql siempre vamos a ocupar un try catch por su ocurre un error
        sucu.setIdSucursal(GestorSucursal.getInstance().retornaID(sucu.getNombre()));
        
        contador = Administrador.getInstance().contarDatosProductos(sucu);
      
        M_datos= Administrador.getInstance().cargarDatosProductos(contador, sucu);
            
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
	
	
	
	
	
	
	
	
	
	

