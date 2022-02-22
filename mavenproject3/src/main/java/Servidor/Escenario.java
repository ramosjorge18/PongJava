/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Vespertino
 */
public class Escenario extends JFrame {

    DatagramPacket paquete;
    DatagramSocket mySocket;
    String[] datosRecibidos;
    JPanel jp1, jp2;
    JLabel lbBola, lbPanel1, lbPanel2, lbJugador1, lbJugador2;
    String nombreJugador1, nombreJugador2;
    int resJug1, resJug2, contJug, goles, velocidad;

    public Escenario() throws SocketException, IOException {
        instanciarComponentesYSetPropiedades();
        
        // Crear servicio TCP para los clientes que entran
        ServerSocket serverSocket = new ServerSocket(2000);
        while(contJug != 2){
            try {
                TCPServidor s1 = new TCPServidor(this, serverSocket.accept());
                Thread t1 = new Thread(s1);
                t1.start();
                Thread.sleep(10);
                contJug++;
            } catch (InterruptedException ex) {
                Logger.getLogger(Escenario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Recibimos los datos de los clientes UDP 
        HServidor s2 = new HServidor(this, 2000);
        Thread t2 = new Thread(s2);
        t2.start();

        // Iniciamos la bola
        Bola b = new Bola(this, velocidad);
        Thread t3 = new Thread(b);
        t3.start();
        
    }

    

    public void instanciarComponentesYSetPropiedades() {

        // Colocamos el icono en los labels
        lbBola = new JLabel(new ImageIcon(getClass().getResource("/img/pelota.png")));
        lbPanel1 = new JLabel(new ImageIcon(getClass().getResource("/img/padel.png")));
        lbPanel2 = new JLabel(new ImageIcon(getClass().getResource("/img/padel.png")));
        lbJugador1 = new JLabel("Jugador1");
        lbJugador2 = new JLabel("Jugador2");

        // Indicamos donde ponemos los labels
        lbBola.setBounds(10, 100, 50, 50);
        lbPanel1.setBounds(10, 10, 50, 50);
        lbPanel2.setBounds(820, 10, 50, 50);

        // Añadimos los paneles al panel principal
        getContentPane().setLayout(new BorderLayout());
        // Panel 1
        jp1 = new JPanel();
        jp1.setLayout(null);
        // Panel 2
        jp2 = new JPanel();
        jp2.setLayout(new GridLayout(1, 2));
        // Los añadimos en sus posiciones
        getContentPane().add(jp1, BorderLayout.CENTER);
        getContentPane().add(jp2, BorderLayout.SOUTH);
        // Metemos los labels al panel1
        jp1.setBackground(Color.BLUE);
        jp1.add(lbBola);
        jp1.add(lbPanel1);
        jp1.add(lbPanel2);
        // Metemos los labels al panel2
        jp2.add(lbJugador1);
        jp2.add(lbJugador2);

        // Variables para el marcador
        contJug = 0;
        resJug1 = 0;
        resJug2 = 0;
        nombreJugador1 = "jug1";
        nombreJugador2 = "jug2";

        // Tamaño del panel y que se vea
        setSize(900, 700);
        setTitle("Pong");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) throws SocketException, IOException {
        Escenario es = new Escenario();
    }

    public void abajoPanel1() {
        // Mueve el panel 1 hacia abajo
        int topeArribaEscenario, posicionPanel;

        // Cogemos el tamaño del panel
        topeArribaEscenario = this.getSize().height;
        posicionPanel = this.lbPanel1.getBounds().y + 10;

        // Si la posicion del panel es mayor al tamaño no hace nada
        if (posicionPanel + 100 < topeArribaEscenario) {
            this.lbPanel1.setBounds(this.lbPanel1.getBounds().x, posicionPanel, this.lbPanel1.getBounds().height, this.lbPanel1.getBounds().width);
            this.getContentPane().repaint();
        }

    }

    public void arribaPanel1() {
        // Mueve el panel 1 hacia abajo
        int topeArribaEscenario, posicionPanel;

        // Cogemos el tamaño del panel
        topeArribaEscenario = this.getSize().height;
        posicionPanel = this.lbPanel1.getBounds().y - 10;

        // Si la posicion del panel es mayor al tamaño no hace nada
        if (posicionPanel > 0) {
            this.lbPanel1.setBounds(this.lbPanel1.getBounds().x, posicionPanel, this.lbPanel1.getBounds().height, this.lbPanel1.getBounds().width);
            this.getContentPane().repaint();
        }
    }

    public void abajoPanel2() {
        // Mueve el panel 1 hacia abajo
        int topeArribaEscenario, posicionPane2;

        // Cogemos el tamaño del panel
        topeArribaEscenario = this.getSize().height;
        posicionPane2 = this.lbPanel2.getBounds().y + 10;

        // Si la posicion del panel es mayor al tamaño no hace nada
        if (posicionPane2 + 100 < topeArribaEscenario) {
            this.lbPanel2.setBounds(this.lbPanel2.getBounds().x, posicionPane2, this.lbPanel2.getBounds().height, this.lbPanel2.getBounds().width);
            this.getContentPane().repaint();
        }

    }

    public void arribaPanel2() {
        // Mueve el panel 1 hacia abajo
        int topeArribaEscenario, posicionPane2;

        // Cogemos el tamaño del panel
        topeArribaEscenario = this.getSize().height;
        posicionPane2 = this.lbPanel2.getBounds().y - 10;

        // Si la posicion del panel es mayor al tamaño no hace nada
        if (posicionPane2 > 0) {
            this.lbPanel2.setBounds(this.lbPanel2.getBounds().x, posicionPane2, this.lbPanel2.getBounds().height, this.lbPanel2.getBounds().width);
            this.getContentPane().repaint();
        }
    }

    public void meteNombre1(String nombre) {
        this.nombreJugador1 = nombre;
        this.lbJugador1.setText(nombreJugador1 + " : " + resJug1);
        this.getContentPane().repaint();
    }

    public void meteNombre2(String nombre) {
        this.nombreJugador2 = nombre;
        this.lbJugador2.setText(nombreJugador2 + " : " + resJug2);
        this.getContentPane().repaint();
    }

    public void sumaMarcador1() {
        resJug1++;
        this.lbJugador1.setText(nombreJugador1 + " : " + resJug1);
        this.getContentPane().repaint();
    }

    public void sumaMarcador2() {
        resJug2++;
        this.lbJugador2.setText(nombreJugador2 + " : " + resJug2);
        this.getContentPane().repaint();
    }
    
    public int getContJug() {
        return contJug;
    }

    public void setContJug(int contJug) {
        this.contJug = contJug;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }


    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    
    public String getNombreJugador1() {
        return nombreJugador1;
    }

    public void setNombreJugador1(String nombreJugador1) {
        this.nombreJugador1 = nombreJugador1;
    }

    public String getNombreJugador2() {
        return nombreJugador2;
    }

    public void setNombreJugador2(String nombreJugador2) {
        this.nombreJugador2 = nombreJugador2;
    }

}
