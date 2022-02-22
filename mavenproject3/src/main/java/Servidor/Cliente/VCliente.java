/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor.Cliente;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Vespertino
 */
public class VCliente extends JFrame {

    HCliente hc;
    TCPCliente tcp;

    JLabel lbNombreJugador, lbPuerto, lbIp, lbGoles, lbVelocidad;
    JTextField txNombreJugador, txPuerto, txIp, txGoles, txVelocidad;
    JButton btStart;
    JPanel jp1;

    public void intanciarComponentes() {
        lbNombreJugador = new JLabel("Nombre");
        lbIp = new JLabel("Ip");
        lbPuerto = new JLabel("Puerto");
        txNombreJugador = new JTextField();
        txIp = new JTextField();
        lbGoles = new JLabel("Goles");
        txGoles = new JTextField();
        lbVelocidad = new JLabel("Velocidad");
        txVelocidad = new JTextField();
        txPuerto = new JTextField();
        btStart = new JButton("Start");

        jp1 = new JPanel();
        jp1.setLayout(new GridLayout(6, 2));

        getContentPane().add(jp1);

        jp1.add(lbNombreJugador);
        jp1.add(txNombreJugador);
        jp1.add(lbIp);
        jp1.add(txIp);
        jp1.add(lbPuerto);
        jp1.add(txPuerto);
        jp1.add(lbGoles);
        jp1.add(txGoles);
        jp1.add(lbVelocidad);
        jp1.add(txVelocidad);
        jp1.add(btStart);

        // Tamaño del panel y que se vea
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setFocusable(true);

        this.btStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // Creamos la Conexion TCP y UDP
                try {
                    tcp = new TCPCliente(VCliente.this);
                    Thread t1 = new Thread(tcp);
                    t1.start();
                    hc = new HCliente(VCliente.this);
                    Thread t2 = new Thread(hc);
                    t2.start();
                    getContentPane().setFocusable(true);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(VCliente.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        this.btStart.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent arg0) {
                if (KeyEvent.VK_W == arg0.getKeyCode()) {
                    getContentPane().setFocusable(true);
                    hc.subirPanel();
                } else if (KeyEvent.VK_S == arg0.getKeyCode()) {
                    getContentPane().setFocusable(true);
                    hc.bajarPanel();
                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public VCliente() {
        intanciarComponentes();
        getContentPane().setFocusable(true);
        /*hc = new HCliente("jugador1", "localhost", "2000");
        Thread t1 = new Thread(hc);
        t1.start();   */
    }

    public static void main(String[] args) {
        VCliente c = new VCliente();

    }

    public String getTxNombreJugador() {
        return txNombreJugador.getText();
    }

    public void setTxNombreJugador(JTextField txNombreJugador) {
        this.txNombreJugador = txNombreJugador;
    }

    public String getTxPuerto() {
        return txPuerto.getText();
    }

    public void setTxPuerto(JTextField txPuerto) {
        this.txPuerto = txPuerto;
    }

    public String getTxIp() {
        return txIp.getText();
    }

    public void setTxIp(JTextField txIp) {
        this.txIp = txIp;
    }

    public String getTxGoles() {
        return txGoles.getText();
    }

    public void setTxGoles(JTextField txGoles) {
        this.txGoles = txGoles;
    }

    public String getTxVelocidad() {
        return txVelocidad.getText();
    }

    public void setTxVelocidad(JTextField txVelocidad) {
        this.txVelocidad = txVelocidad;
    }

}
