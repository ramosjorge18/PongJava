/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor.Cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcbox
 */
public class TCPCliente implements Runnable {

    private VCliente vista;
    private InetAddress ip;
    private Socket clientSocket;
    private String mensaje;
    
    public TCPCliente(VCliente vista) throws UnknownHostException {
        try {
            this.vista = vista;
            this.mensaje = this.vista.getTxNombreJugador()+"#"+this.vista.getTxVelocidad()+"#"+this.vista.getTxGoles();
            this.ip = InetAddress.getByName(vista.getTxIp());
            this.clientSocket = new Socket(ip, Integer.parseInt(vista.getTxPuerto()));
            PrintWriter mandar = new PrintWriter(clientSocket.getOutputStream(), true);
            mandar.println(mensaje);
            
            //Cerramos todo
            mandar.close();
            clientSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(TCPCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void run() {

    }

}
