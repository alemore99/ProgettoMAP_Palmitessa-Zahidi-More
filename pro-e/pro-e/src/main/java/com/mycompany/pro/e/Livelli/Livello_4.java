package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.GUI;
import com.mycompany.pro.e.Interfaccie.messaggiErrore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * Livello 4 dell'avventura
 */
public class Livello_4 {

    /**
     * @param gui
     * @param indovinelli
     * @param inventario
     */
    public static void main(GUI gui, List<Indovinello> indovinelli, Inventario inventario) {
    GUI.PulisciListener.pulisciTuttiListener(gui);

    gui.disablebottoni();

        gui.PulisciTextArea();
        gui.aggiornaImmagine2("/foto/Livello4/caduta.png");

        //setto i vari listener per ogni oggetto
        gui.buttAvanti.addActionListener(_ -> {
            GUI.PulisciListener.pulisciTuttiListener(gui);
            gui.aggiornaImmagine2("/foto/Livello4/scantinato.png");
            gui.ScriviTextArea("Premi il bottone per continuare...");
            gui.abilitabottoni();
            gui.buttAvanti.addActionListener(_ -> {
                gui.PulisciTextArea();
                gui.disablebottoni();
                gui.aggiornaImmagine2("/foto/Livello4/ric.png");
                gui.ScriviTextArea("""
                       Sei arrivato nella cantina. E’ tutto scuro, non si vede nulla! 
                       Servirebbe qualcuno o qualcosa che ci facesse luce! Vedi qualcosa? Vedi una torcia?? Cliccaci sopra se la vedi!
                       \n \n 
                       """);
                //imposto il click per la torcia
                gui.setImageClickListener((x, y) -> {
                    Rectangle torcia = new Rectangle(26, 304, 86, 354);
                    //se la clicca
                    if (torcia.contains(x, y)) {
                        inventario.aggiungi("Torcia: usata nel livello 4");
                        GUI.PulisciListener.pulisciTuttiListener(gui);
                        gui.PulisciTextArea();
                        gui.ScriviTextArea("Narratore: Bravissimo! L’hai trovata! Ora Cliccaci sopra per accenderla! \n \n");
                        //imposto il click sulla torcia per accenderla
                        Rectangle torcia2 = new Rectangle(142, 370, 201, 420);
                        gui.aggiornaImmagine2("/foto/Livello4/prend_tor.png");
                        //una volta accesa
                        gui.setImageClickListener((z, c) -> {
                            if (torcia2.contains(z, c)) {
                                GUI.PulisciListener.pulisciTuttiListener(gui);
                                gui.aggiornaImmagine2("/foto/Livello4/acc_torc.png");
                                gui.PulisciTextArea();
                                gui.ScriviTextArea("Narratore: Adesso ci vediamo di più!! Guarda che disastro, guarda tutto sporco!! " +
                                        "Noi, visto che siamo qui perlustriamo un pò in questa robaccia e vediamo se c’è qualcosa di utile " +
                                        "e prezioso per portarci in ricordo di quest’esperienza PAZZESCA!! \n \n" +
                                        "\n Clicca sul bastone per continuare...\n\n");
                                //clicca sul bastone ed esce dal livello
                                Rectangle oggetto = new Rectangle(272,306,347,381);
                                gui.setImageClickListener((a, b) -> {
                                    if (oggetto.contains(a, b)) {
                                        inventario.aggiungi("Bastone pastorale: usato nel livello 4");
                                        GUI.PulisciListener.pulisciTuttiListener(gui);
                                        gui.PulisciTextArea();
                                        gui.aggiornaImmagine2("/foto/Livello4/bast_re.png");
                                        gui.ScriviTextArea("Ah ecco, bella scelta! " +
                                                "Hai preso un oggetto antichissimo,appartenente alla famiglia reale che soggiornava qui nel 1200 a.C. " +
                                                "Sarebbe il pastorale reale, simbolo della maestosità e padronanza di potere del re nella città. \n \n");

                                        SwingUtilities.invokeLater(() -> {
                                            try {
                                                Thread.sleep(1000);
                                                gui.ScriviTextArea("Ora usciamo dallo scantinato e saliamo nuovamente al piano di " +
                                                        "sopra, dove ritenteremo a salire quelle maledette scale. \n\n");
                                            } catch (InterruptedException ex) {
                                               messaggiErrore.mostraErrore(null,"errore nel sospendere il thread" + ex.getMessage() );
                                            }
                                        });

                                       gui.abilitabottoni();
                                        gui.buttAvanti.addActionListener(_ -> {
                                            gui.disablebottoni();
                                            Suoni.audio("/Audio/Livello3/salitaScale.wav");
                                            gui.aggiornaImmagine2("/foto/Livello4/uscita.png");
                                            Timer timer = new Timer(1500, _-> {
                                                gui.aggiornaImmagine2("/foto/Livello4/salita_lvl3.png");
                                                Timer lo = new Timer(1500, _-> {
                                                    gui.PulisciTextArea();
                                                    GUI.PulisciListener.pulisciTuttiListener(gui);
                                                    Livello_3.main(gui, indovinelli, inventario);
                                                });
                                                lo.setRepeats(false);
                                                lo.start();
                                            });
                                            timer.setRepeats(false);
                                            timer.start();
                                        });
                                    } else {
                                        messaggiErrore.mostraWarning(null, "Punto sbagliato");
                                    }
                                });
                            } else {
                                messaggiErrore.mostraWarning(null, "Punto sbagliato");
                            }
                        });
                    } else {
                        messaggiErrore.mostraWarning(null, "Punto sbagliato");
                    }
                });
            });
        });
        gui.abilitabottoni();
    }


}