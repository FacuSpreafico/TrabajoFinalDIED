package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entidades.Arista;
import entidades.Camino;
import entidades.OrdenDeProvision;
import entidades.Producto;
import entidades.Sucursal;
import entidades.Vertice;
import gestores.GestorCamino;
import gestores.GestorCantidadProductoOrden;
import gestores.GestorOrdenDeProvision;
import gestores.GestorProducto;
import gestores.GestorSucursal;
import main.Administrador;

public class OrdenesDeProvision extends JFrame {

	private JPanel contentPane;
	private JTable table;

	public OrdenesDeProvision() throws ClassNotFoundException, SQLException {
		setTitle("Ordenes de provision");
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setBounds(21, 427, 89, 23);
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnAplicar);
		

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(109, 427, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menu = new MenuPrincipal();
				menu.setVisible(true);
				dispose();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(56, 107, 469, 203);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    datosTabla();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(21, 52, 541, 320);
		contentPane.add(panel_1);
		panel_1.setBorder(BorderFactory.createTitledBorder(""));
		panel_1.setLayout(null);
		
		JButton btnVerProductos = new JButton("Ver productos");
		btnVerProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				if ((table.getValueAt(table.getSelectedRow(), 1) != null)) {
					int idOrden = Integer.valueOf(table.getValueAt(table.getSelectedRow(), 0).toString());
					OrdenDeProvision orden = GestorOrdenDeProvision.getInstance().buscarOrdenXID(idOrden);
					
					
					 DefaultTableModel model = new DefaultTableModel();
				        model.addColumn("Nombre Producto");
				        model.addColumn("Cantidad");
	
					for (Map.Entry<Integer, Integer> entry : orden.getProductos().entrySet()) {
	                    Producto producto = GestorProducto.getInstance().buscarProductoXID(entry.getKey());
	                    Integer cantidad = entry.getValue();
	                    Object[] row = {producto.getNombre(), cantidad};
			            model.addRow(row);
	                }
					
					 JTable table = new JTable(model);
					
					 JPanel panelPrincipal = new JPanel();
				        panelPrincipal.setLayout(new BorderLayout());
				        panelPrincipal.add(new JScrollPane(table), BorderLayout.CENTER);
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
				else {
					
				}
				}
				catch(IndexOutOfBoundsException e1) {
					
				}
			}
		});
		
		btnVerProductos.setBounds(142, 286, 128, 23);
		panel_1.add(btnVerProductos);
		
		
		JButton btnAsignarCamino = new JButton("Asignar camino");
		btnAsignarCamino.setBounds(0, 286, 144, 23);
		panel_1.add(btnAsignarCamino);
		
		
		btnAsignarCamino.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		        	    try {
		        	    if (table.getValueAt(table.getSelectedRow(), 2) != null){
		        	    String[] opciones = GestorCamino.getInstance().getNombresCaminos(table.getValueAt(table.getSelectedRow(), 2).toString());
		        	    opciones = Arrays.stream(opciones)
		                         .filter(Objects::nonNull)
		                         .filter(str -> !str.trim().isEmpty())
		                         .toArray(String[]::new);
		        	    JComboBox<String> comboBox = new JComboBox<>(opciones);

		            
		                JPanel panelCombo = new JPanel();
		                panelCombo.add(new JLabel("Selecciona un camino: "));
		                panelCombo.add(comboBox);

		             
		                int option = JOptionPane.showOptionDialog(rootPane, panelCombo, "Asignar camino", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar", "Cancelar"}, null);
		                if (option == JOptionPane.OK_OPTION) {
		                    String caminoSeleccionado = (String) comboBox.getSelectedItem();
		                    int idOrden = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString()); 
		                    try {
								GestorOrdenDeProvision.getInstance().actualizarOrden(idOrden,caminoSeleccionado);
								OrdenesDeProvision orden = new OrdenesDeProvision();
								orden.setVisible(true);
								dispose();
							} catch (ClassNotFoundException | SQLException e1) {
							 
							}
		                    
		                } else {
		                 
		                } 
		        }
		        	    }
		        	    catch(IndexOutOfBoundsException e1) {
		        	    	
		        	    }
		        }
		        
		});
		
		JButton btnCrearNuevaProvision = new JButton("Crear nueva orden");
		btnCrearNuevaProvision.setBounds(269, 286, 161, 23);
		panel_1.add(btnCrearNuevaProvision);
		
		JButton btnVerCaminos = new JButton("Ver caminos");
		btnVerCaminos.setBounds(428, 286, 113, 23);
		panel_1.add(btnVerCaminos);
		
		btnVerCaminos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				
				ArrayList<Vertice> vertices = new ArrayList<>();
				
			       int c=0;
			       int x=0,y=0;
			       
			       ArrayList<Sucursal> listaSucu = GestorSucursal.getInstance().getSucursales();
			   /*    for(Sucursal sucur: GestorSucursal.getInstance().getSucursales()) {
                       int id = Integer.parseInt((table.getValueAt(table.getSelectedRow(), 2)).toString());
                       if(sucur.getIdSucursal()==id) {
                    	   listaSucu.add(sucur);
                       }
 
                       int idOrden = Integer.parseInt((table.getValueAt(table.getSelectedRow(), 0)).toString());
                       OrdenDeProvision orden = GestorOrdenDeProvision.getInstance().buscarOrdenXID(idOrden);
                       boolean todosPresentesYMayores = true;
                       ArrayList<Sucursal> check = new ArrayList<Sucursal>();
                       
                       for (Map.Entry<Producto, Integer> entry : sucur.getProductos().entrySet()) {
                           Producto producto = entry.getKey();
                           int cantidadProducto = entry.getValue();
                           System.out.print("Hola");
                           
                           if (orden.getProductos().containsKey(producto.getIdProducto())) {
                        	   System.out.print("Entre a primer if");
                               int cantidadProductoOrden = orden.getProductos().get(producto.getIdProducto());
                               if (cantidadProducto > cantidadProductoOrden) {
//                                   listaSucu.add(sucur);
                                   todosPresentesYMayores = true;
                                   check.add(sucur);
                                   System.out.print("Entre a segundo if");
                               }
                               else todosPresentesYMayores = false;
                           }
                           else todosPresentesYMayores = false;
                       }
                       if(todosPresentesYMayores && !listaSucu.contains(sucur) && check.contains(sucur)) listaSucu.add(sucur);

                   }
			       */
			       for(Sucursal sucu: listaSucu) {
			    if(c==(listaSucu.size()/2)) y-=100;
			    if(c==((listaSucu.size()/2)-1))y+=50;
			    	  if(c%2==0) {
			    		  y+=60;
			    	   x+=50;
			    	   y+=90;
			    	   }
			    	  else { 
			    		  y+=20;
			    		x+=50;
			    	  	y-=115;}
//			       	int numero = (int)(Math.random()*220+1);
//			       	int numero1 = (int)(Math.random()*220+1);
//			       
//			       	x= (int) (x + numero+ Math.sqrt(numero1));
//			       
//			       	y= (int) (y + numero1 + Math.sqrt(numero));
			  //   if(sucu.getNombre().equals("SucursalPuerto")||sucu.getNombre().equals("sucursalpuerto"))vertices.add(new Vertice(sucu.getNombre(),0,0));
			  //   else if(sucu.getNombre().equals("SucursalCentro")||sucu.getNombre().equals("sucursalcentro"))vertices.add(new Vertice(sucu.getNombre(),400,400));
			  //   else
			    	vertices.add(new Vertice(sucu.getNombre(),x,y));
			    	c++;
			       }
			        ArrayList<Arista> aristas = new ArrayList<>();
			        
			        ArrayList<Camino> caminos = new ArrayList<Camino>();
			        for(Camino unCami : GestorCamino.getInstance().getCaminos()) {
			        	if(!caminos.contains(unCami)) {
			        	if(listaSucu.contains(unCami.getInicio())||listaSucu.contains(unCami.getFin())) {
			        		caminos.add(unCami);
			        	}
			        	}
			        }
			        
			        for(Camino camino: caminos) {
			        	for(int i=0; i<vertices.size();i++) {
			        		if(vertices.get(i).getEtiqueta().equals(camino.getInicio().getNombre())) {
			        	for(int j=0; j<vertices.size();j++) {
			        		if(vertices.get(j).getEtiqueta().equals(camino.getFin().getNombre())) {
			        			aristas.add(new Arista(camino.getTiempoRecorrido(), camino.getNombre(),vertices.get(i),vertices.get(j)));
			        	}
			        	}
			        	}
			        	}
			        }

			       
			        FrameCaminos camino = new FrameCaminos(vertices,aristas);
			        camino.setVisible(true);
			        	
			}});
		
		
		
		
	    btnCrearNuevaProvision.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            
				CrearOrdenDeProvision orden;
				try {
					orden = new CrearOrdenDeProvision();
					orden.setVisible(true);
					dispose();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
	            
	           
	        	}});
				
	}
	private void datosTabla() throws ClassNotFoundException, SQLException {
		String[] Titulos = {"ID Orden", "Fecha emision", "Destino", "Hora maxima","Estado","Camino"}; 
	    DefaultTableModel dtm_datos = new DefaultTableModel();
	    TableRowSorter<TableModel> trs;
	    
	    String[][] M_datos = null;  
	    Administrador.getInstance();  
	  
		Integer contador = 0; 
                                                                     
        contador = Administrador.getInstance().contarDatosOrdenes();
       
        M_datos= Administrador.getInstance().cargarDatosOrdenes(contador);
            
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
