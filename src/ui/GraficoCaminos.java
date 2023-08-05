package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import entidades.Arista;
import entidades.Vertice;

public class GraficoCaminos extends JPanel {

    private ArrayList<Vertice> vertices;
    private ArrayList<Arista> aristas;

    public GraficoCaminos(ArrayList<Vertice> vertices, ArrayList<Arista> aristas) {
        this.vertices = vertices;
        this.aristas = aristas;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibuja las aristas con colores más suaves
     // Dibuja las aristas con nombres de camino
     // Dibuja las aristas con nombres de camino
        for (Arista arista : aristas) {
            // Otro código existente...
            g.setColor(Color.RED);
            int x1 = arista.getVerticeOrigen().getX() + 15;
            int y1 = arista.getVerticeOrigen().getY() + 15;
            int x2 = arista.getVerticeDestino().getX() + 15;
            int y2 = arista.getVerticeDestino().getY() + 15;

            g.drawLine(x1, y1, x2, y2);

            int coordx = (x1 + x2) / 2;
            int coordy = (y1 + y2) / 2;

            // Ajustar la posición del texto para centrarlo a lo largo de la línea de la arista
            FontMetrics fm = g.getFontMetrics();
            String textoEtiqueta = arista.getNombre() + " con tiempo: " + arista.getTiempo();
            int textWidth = fm.stringWidth(textoEtiqueta);
            int textHeight = fm.getHeight();
            
            // Calcular el desplazamiento a lo largo de la línea de la arista
            int deltaX = (x2 - x1);
            int deltaY = (y2 - y1);
            int textOffsetX = deltaX > 0 ? textWidth / 2 : -textWidth / 2;
            int textOffsetY = deltaY > 0 ? textHeight / 2 : -textHeight / 2;
            
            coordx = coordx + textOffsetX;
            coordy = coordy + textOffsetY;

            g.drawString(textoEtiqueta, coordx, coordy);
        }



        // Dibuja los vértices con bordes
        for (Vertice vertice : vertices) {
            int x = vertice.getX();
            int y = vertice.getY();

            g.setColor(new Color(0, 255, 255, 180)); // R, G, B, Opacidad
            g.fillOval(x, y, 30, 30);
            g.setColor(Color.BLACK);
            g.drawOval(x, y, 30, 30);

            // Ajustar la posición del texto para centrarlo en el vértice
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(vertice.getEtiqueta());
            int textHeight = fm.getHeight();
            int coordx = x + (30 - textWidth) / 2;
            int coordy = y + (30 - textHeight) / 2 + fm.getAscent();

            g.drawString(vertice.getEtiqueta(), coordx, coordy);
        }
    }



    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    public void setAristas(ArrayList<Arista> aristas) {
        this.aristas = aristas;
    }
}