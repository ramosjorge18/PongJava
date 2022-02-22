/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vespertino
 */
public class Bola implements Runnable {

    int dirX, dirY;
    Escenario escenario;
    int velocidad;
    
    public Bola(Escenario escenario,int velocidad ) {
        this.escenario = escenario;
        dirX = escenario.lbBola.getBounds().x % 10 + 10;
        dirY = escenario.lbBola.getBounds().y % 10 + 10;
        this.velocidad = velocidad;
    }

    public void mueveBola() {
        int tamañoEscenarioX, posicionBolaX, tamañoEscenarioY, posicionBolaY;
        boolean flag = false;

        tamañoEscenarioX = escenario.getSize().width;
        tamañoEscenarioY = escenario.getSize().height;
        posicionBolaX = escenario.lbBola.getBounds().x + dirX;
        posicionBolaY = escenario.lbBola.getBounds().y + dirY;
        
        
        // Hacemos la colision
        Rectangle rBola = new Rectangle(escenario.lbBola.getBounds());
        Rectangle rPala1 = new Rectangle(escenario.lbPanel1.getBounds());
        Rectangle rPala2 = new Rectangle(escenario.lbPanel2.getBounds());

        if (rBola.intersects(rPala1)) {
            dirX *= -1;
            flag = true;
        } else if (rBola.intersects(rPala2)) {
            dirX *= -1;
            flag = true;
        }

        if (!flag) {
            //Controlo que cuando toca el borde, cambie de direccion
            if ((posicionBolaX) <= 0) {
                dirX *= -1;
                escenario.sumaMarcador1();
            } else if ((posicionBolaX + 50) >= tamañoEscenarioX) {
                dirX *= -1;
                escenario.sumaMarcador2();
            }

            if ((posicionBolaY - 0) <= 0) {
                dirY *= -1;

            } else if ((posicionBolaY + 100) >= tamañoEscenarioY) {
                dirY *= -1;
            }
        }
        //Controlar cuando toque el panel cambie de direcion
        // Repintamos en el panel la bola
        escenario.lbBola.setBounds(posicionBolaX, posicionBolaY, escenario.lbBola.getBounds().height, escenario.lbBola.getBounds().width);
        escenario.getContentPane().repaint();
    }

    @Override
    public void run() {
        while (true) {
            mueveBola();
            try {
                Thread.sleep(this.velocidad);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bola.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
