/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pcbox
 */
public class TCPServidor implements Runnable{
    
    private Escenario escenario;
    private String mensaje;
    private String[] palabras;
    private byte[] buffer;
    private Socket clientSocket;

    public TCPServidor(Escenario escenario,Socket serverSocket) throws IOException {
        this.escenario = escenario;
        clientSocket = serverSocket;
        palabras = new String[3];
    }
    
    @Override
    public void run() {
        try {
            // Recibimos el mensaje y lo metemos en su lugar del usuario
            BufferedReader lee = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            mensaje = lee.readLine();
            palabras = mensaje.split("#");
            // Comprobamos que jugador es y metemos datos
            if(escenario.getContJug() == 0){
                escenario.meteNombre1(palabras[0]);
                escenario.setVelocidad(Integer.parseInt(palabras[1]));
                escenario.setGoles(Integer.parseInt(palabras[2]));
            }else{
                escenario.meteNombre2(palabras[0]);
            }
            // Cerramos todo
            lee.close();
            clientSocket.close();            
        } catch (IOException ex) {
            Logger.getLogger(TCPServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
