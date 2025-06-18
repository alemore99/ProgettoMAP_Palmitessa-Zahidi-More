package com.mycompany.pro.e;

import com.mycompany.pro.e.Interfaccie.GUI;
import com.mycompany.pro.e.Interfaccie.messaggiErrore;
import com.mycompany.pro.e.Livelli.Inventario;
import com.mycompany.pro.e.Livelli.Livello_1;

import javax.swing.*;

public class Main {
    /**
     * Si occupa di mostrare solo la intro del gioco, poi richiama Livello 1
     * @param args
     */
    public static void main(String[] args) {
        GUI gui = null;
        Inventario inventario = new Inventario();
        //creo un istanza della gui, altrimenti lancio errore
        try {
            gui = new GUI(inventario);
            gui.setVisible(true);
        } catch (Exception e) {
            messaggiErrore.mostraErrore(null, "Errore: Impossibile avviare il gioco con la GUI");
        }

        //variabili contatore
        final int[] stato = {0};
        final int[] ripetizioni = {0};
        final int maxRipetizioni = 1;

        Timer timer = new Timer(2000, null);
        GUI finalGui = gui;

        //eseguo, scandendo il tempo, il cambio delle immagini
        timer.addActionListener(_ -> {
            //avvia il livello sopo aver eseguito le ripetizioni
            if (ripetizioni[0] >= maxRipetizioni) {
                timer.stop();

                //cambio il layout e avvio il livello
                assert finalGui != null;
                finalGui.getCardLayout().show(finalGui.getPadrePanel(), "card2");

                SwingUtilities.invokeLater(() -> {
                    Livello_1 livello1 = new Livello_1();
                    livello1.main(finalGui, inventario);
                });

                return;
            }
            //switch per le due immagini iniziali
            assert finalGui != null;
            if (stato[0] == 0) {
                finalGui.aggiornaImmagine("/foto/Intro/inizio_1.0.png");
            } else if (stato[0] == 1) {
                finalGui.aggiornaImmagine("/foto/Intro/inizio_1.1.png");
            }

            stato[0]++;

            if (stato[0] >= 2) {
                stato[0] = 0;
                ripetizioni[0]++;
            }

            timer.setDelay(1500);
        });

        timer.setInitialDelay(0);
        timer.start();

    }

}
