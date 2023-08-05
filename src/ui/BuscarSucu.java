package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import entidades.Sucursal;
import excepciones.NoSeleccionaTuplaException;
import gestores.GestorSucursal;
import main.Administrador;
import java.awt.Color;


public class BuscarSucu extends JFrame {
	private JTable table_2;
	private JPanel contentPane;
	private JTextField textField;
	private JComboBox<String> comboBox_1;
	
	
	public BuscarSucu() throws ClassNotFoundException, SQLException {
		setResizable(false);

		setTitle("Sucursales");
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//btnNewButton_1.addActionListener(buscar(textField));
		
		JButton btnNewButton_1_1 = new JButton("Aplicar");
		btnNewButton_1_1.setBounds(29, 409, 89, 23);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton_1_1);
		

		JButton btnNewButton_1_2 = new JButton("Cancelar");
		btnNewButton_1_2.setBounds(119, 409, 89, 23);
		contentPane.add(btnNewButton_1_2);
		
		btnNewButton_1_2.addActionListener(volverAnterior());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 202, 469, 118);
		contentPane.add(scrollPane);
	
		table_2 = new JTable();
		table_2.setSurrendersFocusOnKeystroke(true);
		
		table_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_2);
		datosTabla();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(29, 184, 523, 180);
		contentPane.add(panel_1);
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		panel_1.setLayout(null);
		

		JButton bEditar = new JButton("Editar");
		bEditar.setBounds(107, 146, 95, 23);
		panel_1.add(bEditar);
	
		
		JButton btnDarBaja = new JButton("Dar baja");
		btnDarBaja.setBounds(413, 146, 100, 23);
		panel_1.add(btnDarBaja);
	    
		btnDarBaja.addActionListener(darDeBaja());
		
	    JButton btnConsultarmodificarStock = new JButton("Consultar/modificar stock");
	    btnConsultarmodificarStock.setBounds(201, 146, 213, 23);
		panel_1.add(btnConsultarmodificarStock);
		
	
		
		JButton btnAgregar = new JButton("Dar alta");
		btnAgregar.setBounds(10, 146, 100, 23);
		panel_1.add(btnAgregar);
		
	    btnAgregar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            RegistrarSucu reg = new RegistrarSucu();
	            reg.setVisible(true);
	            dispose();
	        	}});
	    
		btnConsultarmodificarStock.addActionListener(productosSucursal());
		bEditar.addActionListener(mostrarPantalla2());
		
		JPanel panel = new JPanel();
		panel.setBounds(29, 55, 523, 118);
		panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
		contentPane.add(panel);
		panel.setLayout(null);
		
    
		JLabel lblNewLabel = new JLabel("Nombre");
	    lblNewLabel.setBounds(10, 55, 46, 14);
		panel.add(lblNewLabel);
				
	    textField = new JTextField();
	    textField.setBackground(new Color(255, 255, 255));
		textField.setBounds(87, 52, 109, 20);
	    panel.add(textField);
		textField.setColumns(10);
				
		onlyLet(textField);
		
	
		}	
	private ActionListener darDeBaja() {
	    ActionListener b = new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            
	        	try {
	        	if ((table_2.getValueAt(table_2.getSelectedRow(), 1) != null)) {
	        	
	        	String message = "<html><center><b>¿Está seguro de que desea dar de baja esta sucursal?</b><br>"
	                    + "Esta acción eliminará permanentemente los datos asociados a esta sucursal y no se podrán recuperar.</html>";

	            int option = JOptionPane.showOptionDialog(rootPane,
	                    message,
	                    "Confirmación",
	                    JOptionPane.YES_NO_OPTION,
	                    JOptionPane.QUESTION_MESSAGE,
	                    null,
	                    new Object[]{"Confirmar", "Cancelar"},
	                    "Cancelar");
	            Sucursal eliminar = new Sucursal();
	            
	            if (option == JOptionPane.YES_OPTION) {
	                    Sucursal unaSucu = new Sucursal();
	                    unaSucu= GestorSucursal.getInstance().buscarSucursal(table_2.getValueAt(table_2.getSelectedRow(), 1).toString());
	                    if (unaSucu!=null) {
	                        try {
	                            Administrador.getInstance().eliminarDatosSucursal(unaSucu);
	                            JOptionPane.showMessageDialog(rootPane, "Se dio de baja la sucursal exitosamente. Todos los datos asociados a esta sucursal han sido eliminados permanentemente.","Exito",JOptionPane.PLAIN_MESSAGE, new ImageIcon("path/to/green_tick_icon.png"));
	                            eliminar = unaSucu;
	                            BuscarSucu buscar = new BuscarSucu();
	                            buscar.setVisible(true);
	                            dispose();
	                        } catch (SQLException e1) {
	                        	JOptionPane.showMessageDialog(rootPane, "Error: La sucursal que se quiere dar de baja pertenece a un camino o a una orden en curso.", "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                        catch (IndexOutOfBoundsException | ClassNotFoundException  e2) {
	                        	JOptionPane.showMessageDialog(rootPane, "Error: " +e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                        }
	                    }
	                GestorSucursal.getInstance().getSucursales().remove(eliminar);
	            }
	        }
	        }
	        catch(IndexOutOfBoundsException e3) {
	        	
	        }
	        };
	    };
	    return b;
	}
	ActionListener mostrarPantalla2 () {
		ActionListener b = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
				EditarSucu pantalla2 = new EditarSucu((table_2.getValueAt(table_2.getSelectedRow(),1)).toString(),(table_2.getValueAt(table_2.getSelectedRow(),4)).toString(),(table_2.getValueAt(table_2.getSelectedRow(),5)).toString(),(table_2.getValueAt(table_2.getSelectedRow(),2)).toString());
				
				pantalla2.setVisible(true);
				dispose();
				}
				catch(NoSeleccionaTuplaException | NullPointerException | IndexOutOfBoundsException e1) {
				}
			}
	};
		return b;
	}
	
	ActionListener productosSucursal () {
		ActionListener b = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
				Sucursal sucu = new Sucursal();
				Time horarioAbre= Time.valueOf(table_2.getValueAt(table_2.getSelectedRow(),4).toString());
 				Time horarioCierra= Time.valueOf(table_2.getValueAt(table_2.getSelectedRow(),5).toString());
				int capacidad = Integer.parseInt(table_2.getValueAt(table_2.getSelectedRow(),2).toString());
				
				sucu.setNombre(table_2.getValueAt(table_2.getSelectedRow(),1).toString());
				sucu.setCapacidad(capacidad);
				sucu.setEstado(table_2.getValueAt(table_2.getSelectedRow(),3).toString());
				sucu.setHorarioApertura(horarioAbre);
				sucu.setHorarioCierre(horarioCierra);
					
				ProductosSucursal prod = new ProductosSucursal(sucu);
				
				prod.setVisible(true);
				dispose();
				}
				catch(NullPointerException | IndexOutOfBoundsException e1) {
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
	};
		return b;
	}
	
	
	
	
	
	
	

	ActionListener volverAnterior () {
        ActionListener b= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuPrincipal buscar = new MenuPrincipal();
               buscar.setVisible(true);
                dispose();
        }
    };
        return b;
    }
	
	/*void cargarDatos (String nombre, String idSucursal, String capacidad, JComboBox<String> combo) {
		textField.setText(nombre);
		textField_1.setText(idSucursal);
		textField_2.setText(capacidad);
	    comboBox.setSelectedItem(combo.getSelectedItem().toString());
	}
	*/
	private void onlyLet (final JTextField texto) {
		texto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isAlphabetic(c) || c == '.') {
					e.consume();
				}
			}

			
			@Override
			public void keyReleased(KeyEvent e) {
				String M_datos[][]= null;
				TableRowSorter<TableModel> trs; 
				String[] Titulos = {"IDSucursal", "Nombre", "Capacidad", "Estado", "Apertura", "Cierre"}; //Arreglo de los titulos para la tabla
			    DefaultTableModel dtm_datos = new DefaultTableModel(); //creamos  un modelo para la taba de datos
				try {
					M_datos = Administrador.getInstance().contarDatosQuerySucursal(textField);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dtm_datos = new DefaultTableModel(M_datos, Titulos) {
                public boolean isCellEditable(int row, int column) {//este metodo es muy util si no quieren que editen su tabla, 
                return false;  //si quieren modificar los campos al dar clic entonces borren este metodo
                }
                };
                table_2.setModel(dtm_datos);
                trs = new TableRowSorter<>(dtm_datos);
                table_2.setRowSorter(trs);
			}
		});	
	}







	/*
	ActionListener buscar (final JTextField nombre) {
        ActionListener b= new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					admin.buscarXNombre(nombre.getText().toString());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
    };
        return b;
    }
	*/
	private void datosTabla() throws ClassNotFoundException, SQLException {
		String[] Titulos = {"IDSucursal", "Nombre", "Capacidad", "Estado", "Apertura", "Cierre"}; //Arreglo de los titulos para la tabla
	    DefaultTableModel dtm_datos = new DefaultTableModel(); //creamos  un modelo para la taba de datos
	    TableRowSorter<TableModel> trs; //Hacemos el table row sorter para poder ordenar la tabla al presionar los encabezados de la misma
	    
	    String[][] M_datos = null;  //iniciamos una matriz donde pasaremos los datos de sql
	    Administrador.getInstance();   //iniciamos un objeto que se encargara de la conexion de datos
		
	  
		Integer contador = 0;  //creamos un contador para saber el numero de datos que obtendremos de la tabla datos de sql
                                                                      //para las consultas sql siempre vamos a ocupar un try catch por su ocurre un error
        contador = Administrador.getInstance().contarDatosSucursal();
       
        M_datos= Administrador.getInstance().cargarDatosSucursal(contador);
            
        dtm_datos = new DefaultTableModel(M_datos, Titulos) { //ahora agregaremos la matriz y los titulos al modelo de tabla
            public boolean isCellEditable(int row, int column) {//este metodo es muy util si no quieren que editen su tabla, 
                return false;  //si quieren modificar los campos al dar clic entonces borren este metodo
            }
        };
        table_2.setModel(dtm_datos); //ahora el modelo que ya tiene tanto los datos como los titulos lo agregamos a la tabla
        trs = new TableRowSorter<>(dtm_datos); //iniciamos el table row sorter para ordenar los datos (esto es si gustan)
        table_2.setRowSorter(trs); //y lo agregamos al jtable
	}
}
	

