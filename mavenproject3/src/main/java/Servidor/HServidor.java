/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vespertino
 */
public class HServidor implements Runnable {

    private Escenario escenario;
    private String mensaje, jugador1, jugador2;
    private String[] mens;
    private byte[] buffer;
    private int puerto;
    private DatagramPacket datagram;
    private DatagramSocket mySocket;

    public HServidor(Escenario escenario, int puerto) throws SocketException {
        this.escenario = escenario;
        this.puerto = puerto;
        buffer = new byte[100];
        jugador1 = escenario.getNombreJugador1();
        jugador2 = escenario.getNombreJugador2();
        mens = new String[2];
    }

    @Override
    public void run() {
        try {
            // Creamos el conducto para recibir el mensaje            
            mySocket = new DatagramSocket(puerto);
            while (true) {
                try {
                    // Inicializamos el paquete en que vamos a recibir los datos
                    datagram = new DatagramPacket(buffer, 100);
                    // Recibimos los datos y los metemos en paquete
                    mySocket.receive(datagram);
                    // Leemos los datos del paquete y lo metemos en un String
                    mensaje = new String(buffer);
                    mensaje.trim();
                    mens  = mensaje.split("#");
                    String m2 = mens[1];
                    // Escribimos el mensaje en la vista segun el jugador que sea
                    if (mens[0].equals(jugador1)) {
                        if(mensaje.contains("arr")){
                            escenario.arribaPanel1();
                        }else{
                            escenario.abajoPanel1();
                        }
                    } else if (mens[0].equals(jugador2)) {
                        if(mensaje.contains("arr")){
                            escenario.arribaPanel2();
                        }else{
                            escenario.abajoPanel2();
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(HServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(HServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
