package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ExitoRegistrarCamino extends JFrame{
    private JPanel contentPane;
    private JLabel mensajeExito;
    private JButton aceptar;


    public ExitoRegistrarCamino() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 336, 164);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(13, 30, 46, 47);
        contentPane.add(lblNewLabel);

        mensajeExito = new JLabel("Se ha registrado el camino con exito!");
        mensajeExito.setBounds(69, 38, 213, 31);
        contentPane.add(mensajeExito);

           /* setBtnNewButton(new JButton("Aceptar"));

            getBtnNewButton().setBounds();

            contentPane.add(getBtnNewButton());
            */


        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            //RegistrarSucu registrar1= new RegistrarSucu();
            //dispose();
                MenuPrincipal d= new MenuPrincipal();
                d.setVisible(true);
                dispose();
                }
            });
        aceptar.setBounds(102, 91, 89, 23);
        contentPane.add(aceptar);

        }
}