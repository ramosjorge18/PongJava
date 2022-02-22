/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor.Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vespertino
 */
public class HCliente implements Runnable {

    private InetAddress ip;
    private VCliente vista;
    public DatagramSocket mySocket;

    public HCliente(VCliente vista) throws UnknownHostException {
        try {
            this.vista = vista;
            this.ip = InetAddress.getByName(vista.getTxIp());
            //Creamos el puerto del cliente
            mySocket = new DatagramSocket();

        } catch (SocketException ex) {
            Logger.getLogger(HCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void subirPanel() {
        try {
            String mensaje;
            byte[] buffer;

            mensaje = this.vista.getTxNombreJugador() + "#arr";
            buffer = new byte[mensaje.length()];
            buffer = mensaje.getBytes();
            // Creamos el paquete para mandarlo
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, ip, Integer.parseInt(vista.getTxPuerto()));
            // Mandamos el paquete
            mySocket.send(datagram);
        } catch (IOException ex) {
            Logger.getLogger(HCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bajarPanel() {
        try {
            String mensaje;
            byte[] buffer;

            mensaje = this.vista.getTxNombreJugador() + "#deb";
            buffer = new byte[mensaje.length()];
            buffer = mensaje.getBytes();
            // Creamos el paquete para mandarlo
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, ip, Integer.parseInt(vista.getTxPuerto()));
            // Mandamos el paquete
            mySocket.send(datagram);
        } catch (IOException ex) {
            Logger.getLogger(HCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

    }

}
