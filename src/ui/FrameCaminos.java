package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Arista;
import entidades.Vertice;
import javax.swing.JButton;

public class FrameCaminos extends JFrame {

    private JPanel contentPane;


    public FrameCaminos(ArrayList<Vertice> vertices, ArrayList<Arista> aristas) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane =new GraficoCaminos(vertices,aristas);;

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("Aceptar");
        btnNewButton.setBounds(245, 427, 89, 23);
        contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                dispose();

            }
            });

    }
}