package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entidades.OrdenDeProvision;
import entidades.Producto;
import entidades.Sucursal;
import gestores.GestorCantidadProductoOrden;
import gestores.GestorOrdenDeProvision;
import gestores.GestorProducto;
import gestores.GestorSucursal;
import main.Administrador;

public class CrearOrdenDeProvision extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public HashMap<Integer, Integer> lista= new HashMap<Integer, Integer>();
	private JTable table;
	private JTextField tiempoMaximo;

	public CrearOrdenDeProvision() throws ClassNotFoundException, SQLException {
		
		
		setTitle("Crear orden");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sucursal");
		lblNewLabel.setBounds(40, 40, 58, 14);
		contentPane.add(lblNewLabel);
		
		String[] sucursales = GestorSucursal.getInstance().getNombresSucursales();
        JComboBox<String> sucursalBox = new JComboBox<>(sucursales);
		sucursalBox.setBounds(108, 36, 111, 22);
		contentPane.add(sucursalBox);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder("Búsqueda de productos"));
		panel.setBounds(40, 85, 523, 118);
		contentPane.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(10, 55, 46, 14);
		panel.add(lblNewLabel_1);
		
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
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(87, 52, 109, 20);
		panel.add(textField);
		
		tiempoMaximo = new JTextField();
		tiempoMaximo.setBounds(399, 37, 94, 20);
		contentPane.add(tiempoMaximo);
		tiempoMaximo.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		
		btnCrear.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Sucursal sucu = GestorSucursal.getInstance().buscarSucursal(sucursalBox.getSelectedItem().toString());
		        Date fechaActual = new Date(System.currentTimeMillis());
		        try {
		            Time tiempoMax = Time.valueOf(tiempoMaximo.getText().toString() + ":00" + ":00");

		            if (lista.isEmpty()) {
		                JOptionPane.showMessageDialog(rootPane, "Agregue al menos un producto a la orden", "Error", JOptionPane.ERROR_MESSAGE);
		                return; 
		            }

		            int respuesta = JOptionPane.showConfirmDialog(rootPane, "¿Estás seguro que deseas crear esta orden?", "Confirmar", JOptionPane.YES_NO_OPTION);
		            OrdenDeProvision orden = GestorOrdenDeProvision.getInstance().CrearOrdenDeProvision(sucu, fechaActual, tiempoMax, lista);
		            if (respuesta == JOptionPane.YES_OPTION) {
		                for (Map.Entry<Integer, Integer> entry : lista.entrySet()) {
		                    int producto = entry.getKey();
		                    Integer cantidad = entry.getValue();
		                    GestorCantidadProductoOrden.getInstance().registrar(orden, producto, cantidad);
		                }
		                JOptionPane.showMessageDialog(rootPane, "Creado satisfactoriamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
		                OrdenesDeProvision ordenProv = new OrdenesDeProvision();
		                ordenProv.setVisible(true);
		                dispose();
		                
		                
		                
		            } else {       
		            }

		        } catch (ClassNotFoundException | SQLException e1) {
		            JOptionPane.showMessageDialog(rootPane, "Ha ocurrido un error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (IllegalArgumentException e2) {
		            JOptionPane.showMessageDialog(rootPane, "Error: No se ha ingresado el tiempo máximo de espera, verifique e inténtelo nuevamente", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});


		
		btnCrear.setBounds(40, 427, 89, 23);
		contentPane.add(btnCrear);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(130, 427, 89, 23);
		contentPane.add(btnCancelar);
		
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
					OrdenesDeProvision orden;
					orden = new OrdenesDeProvision();
					orden.setVisible(true);
	                dispose();
				} catch (ClassNotFoundException | SQLException e1) {
				}
            }
            
            });
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(40, 214, 520, 180);
		contentPane.add(panel_1);
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		panel_1.setLayout(null);
		
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(24, 11, 469, 118);
			panel_1.add(scrollPane);
		
		
		
		table = new JTable();
		table.setSurrendersFocusOnKeystroke(true);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		
		
		
		JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	try {
            	if (table.getValueAt(table.getSelectedRow(), 1) != null) {
            	JPanel panel = new JPanel(new GridLayout(2, 2));
		        JLabel label = new JLabel("Cantidad");
		        JTextField textField = new JTextField();
		        panel.add(label);
		        panel.add(textField);
		        
				int option = JOptionPane.showOptionDialog(rootPane,
		                panel,
		                "Cantidad:",
		                JOptionPane.OK_CANCEL_OPTION,
		                JOptionPane.PLAIN_MESSAGE,
		                null,
		                new Object[]{"Aplicar", "Cancelar"},
		                "Aplicar");

		        if (option == JOptionPane.OK_OPTION) {
		        	 int idprod = Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
		             Integer cant = Integer.parseInt(textField.getText());
					 lista.put(idprod, cant);
		        } 
		        else {
		        }
            }
            }
            catch(IndexOutOfBoundsException | NumberFormatException e3) {
            }
            }
        });
        btnAgregar.setBounds(10, 146, 95, 23);
        panel_1.add(btnAgregar);
		
	
		
		JLabel lblTiempoMaximo = new JLabel("Tiempo maximo espera");
		lblTiempoMaximo.setBounds(262, 40, 146, 14);
		contentPane.add(lblTiempoMaximo);
		
		
		JLabel lblHs = new JLabel("HS");
		lblHs.setBounds(497, 40, 129, 14);
		contentPane.add(lblHs);
		
		JButton btnVerLista = new JButton("Ver lista");
		btnVerLista.setBounds(104, 146, 89, 23);
		panel_1.add(btnVerLista);
		
		btnVerLista.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        DefaultTableModel model = new DefaultTableModel();
		        model.addColumn("Nombre Producto");
		        model.addColumn("Cantidad");

		        for (Map.Entry<Integer, Integer> entry : lista.entrySet()) {
		            Producto producto = GestorProducto.getInstance().buscarProductoXID(entry.getKey());
		            Integer cantidad = entry.getValue();
		            Object[] row = {producto.getNombre(), cantidad};
		            model.addRow(row);
		        }

		        JTable table = new JTable(model);

		     
		        JButton btnEliminar = new JButton("Eliminar");
		        btnEliminar.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		                int selectedRow = table.getSelectedRow();
		                if (selectedRow != -1) {
		                    Producto productoSeleccionado = (Producto) lista.keySet().toArray()[selectedRow];
		                    lista.remove(productoSeleccionado);
		                    model.removeRow(selectedRow);
		                } else {
		                    JOptionPane.showMessageDialog(rootPane, "Selecciona una tupla para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        });

		        // Crear panel para el botón "Eliminar"
		        JPanel panelBotonEliminar = new JPanel();
		        panelBotonEliminar.add(btnEliminar);

		        // Crear panel principal para la JTable y el botón "Eliminar"
		        JPanel panelPrincipal = new JPanel();
		        panelPrincipal.setLayout(new BorderLayout());
		        panelPrincipal.add(new JScrollPane(table), BorderLayout.CENTER);
		        panelPrincipal.add(panelBotonEliminar, BorderLayout.SOUTH);
		        panelPrincipal.setPreferredSize(new Dimension(300, 150));
		        
		        
		        
		        // Tamaño del cuadro de diálogo
		        int width = 300;
		        int height = 150;

		        // Crear cuadro de diálogo personalizado con botón "Aceptar"
		        int option = JOptionPane.showOptionDialog(rootPane, panelPrincipal, "Productos", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar"}, null);
		        if (option == JOptionPane.OK_OPTION) {
		            // El usuario hizo clic en el botón "Aceptar"
		        }
		        
		    }
		});

		
		datosTabla();
	}
	
	private void datosTabla() throws ClassNotFoundException, SQLException {
		String[] Titulos = {"IDProducto","Precio", "Nombre","Descripcion","Peso"}; 
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

