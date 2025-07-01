package com.mycompany.pro.e;

import com.mycompany.pro.e.Database.Database;
import com.mycompany.pro.e.Database.StatoGioco;
import com.mycompany.pro.e.Interfacce.GUI;
import com.mycompany.pro.e.Interfacce.VarGlobali;
import com.mycompany.pro.e.Interfacce.messaggiErrore;
import com.mycompany.pro.e.Livelli.*;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe principale del gioco che gestisce l'avvio e il flusso di gioco.
 * <p>
 * Carica gli indovinelli da un file, gestisce lo stato del gioco e avvia i livelli.
 * Permette di scegliere se iniziare una nuova partita o continuare una partita esistente,
 * nel caso sia gia presente una in corso.
 * </p>
 */
public class Main {

    private static final String indovinelliChiusi = "indovinelli.txt";

    public static void main(String[] args) {
        Database statoDAO = new Database();
        StatoGioco statoGioco = statoDAO.carica();
        Inventario inventario = new Inventario();

        List<Indovinello> Indovinelli;
        try {
            Indovinelli = Indovinello.caricaDaFile(indovinelliChiusi);
        } catch (IOException e) {
            messaggiErrore.mostraErrore(null, "File degli indovinelli chiusi non caricato correttamente");
            Indovinelli = new ArrayList<>();
        }
        VarGlobali.setLivello(1);
        
        //se l'utente ha già una partita in corso, gli viene chiesto se vuole continuare o iniziare una nuova partita.
        char Decisione = 'S';
            if (!VarGlobali.getFinito()) {
                Decisione = VarGlobali.Decisioni("Vuoi iniziare una nuova partita od continuare la vecchia partita?" +
                        "\n\nRispondi con no se vuoi continuare con la vecchia partita o con si se vuoi iniziare una nuova partita");
            }

        if (Decisione == 'N') {
            try {
                GUI gui = new GUI(inventario);
                gui.setVisible(true);
                VarGlobali.setFinito(false);
                if (statoGioco != null) {
                    VarGlobali.setLivello(statoGioco.getLivello());
                    VarGlobali.setFinito(statoGioco.getFinito());
                } else {
                    VarGlobali.setLivello(1);
                }

                List<Indovinello> indovinelli = (statoGioco != null && statoGioco.getIndovinelli() != null)
                        ? statoGioco.getIndovinelli()
                        : Indovinelli;

                //salva lo stato del gioco all'uscita
                gui.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        Database.salva(new StatoGioco(inventario, VarGlobali.getLivello(), indovinelli, VarGlobali.getFinito()));
                    }
                });

                gui.getCardLayout().show(gui.getPadrePanel(), "card2");
                int livelloAttuale = VarGlobali.getLivello();
                switch (livelloAttuale) {
                    case 1 -> new Livello_1().main(gui, inventario, indovinelli);
                    case 2 -> new Livello_2().main(gui, inventario, indovinelli);
                    case 3 -> Livello_3.main(gui, inventario, indovinelli);
                    case 4 -> Livello_4.main(gui, inventario, indovinelli);
                    case 5 -> Livello_5.main(gui, inventario, indovinelli);
                    default -> {
                        VarGlobali.setLivello(1);
                        new Livello_1().main(gui, inventario, indovinelli);
                    }
                }
            } catch (Exception e) {
                messaggiErrore.mostraErrore(null, "Errore: Impossibile avviare il gioco");
            }
        }else if (Decisione == 'S') {
            try {
                GUI gui = new GUI(inventario);
                gui.setVisible(true);
                VarGlobali.setFinito(false);
                Database.reset();
                inventario.svuota();

                List<Indovinello> finalIndovinelli = Indovinelli;
                gui.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        Database.salva(new StatoGioco(inventario, VarGlobali.getLivello(), finalIndovinelli, VarGlobali.getFinito()));
                    }
                });

                VarGlobali.setLivello(1);
                List<Indovinello> finalIndovinelli1 = Indovinelli;
                avviaIntro(gui, () -> new Livello_1().main(gui, inventario, finalIndovinelli1));

            } catch (Exception e) {
                messaggiErrore.mostraErrore(null, "Errore: Impossibile avviare il gioco");
            }
        }else {
            messaggiErrore.mostraErrore(null, "Errore: Risposta non valida");
        }
    }

    /**
     * Avvia l'introduzione del gioco con un'animazione.
     * <p>
     * Mostra una serie di immagini in sequenza per introdurre il gioco.
     * Dopo un certo numero di ripetizioni, mostra la schermata del primo livello.
     * </p>
     *
     * @param gui                L'interfaccia grafica del gioco
     * @param azioneFineIntro    Azione da eseguire al termine dell'introduzione
     */
    private static void avviaIntro(GUI gui, Runnable azioneFineIntro) {
        final int[] stato = {0};
        final int[] ripetizioni = {0};
        final int maxRipetizioni = 1;

        Timer[] timer = new Timer[1];
        timer[0] = new Timer(2000, _ -> {
            if (ripetizioni[0] >= maxRipetizioni) {
                timer[0].stop();
                gui.getCardLayout().show(gui.getPadrePanel(), "card2");
                SwingUtilities.invokeLater(azioneFineIntro);
                return;
            }

            if (stato[0] == 0) {
                gui.aggiornaImmagine("/foto/Intro/inizio_1.0.png");
            } else if (stato[0] == 1) {
                gui.aggiornaImmagine("/foto/Intro/inizio_1.1.png");
            }

            stato[0]++;
            if (stato[0] >= 2) {
                stato[0] = 0;
                ripetizioni[0]++;
            }

            timer[0].setDelay(1500);
        });

        timer[0].setInitialDelay(0);
        timer[0].start();
    }
}