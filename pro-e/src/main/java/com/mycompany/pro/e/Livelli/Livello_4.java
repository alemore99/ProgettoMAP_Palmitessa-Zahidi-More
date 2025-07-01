package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfacce.GUI;
import com.mycompany.pro.e.Interfacce.VarGlobali;
import com.mycompany.pro.e.Interfacce.messaggiErrore;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Classe che rappresenta il quarto livello del gioco.
 * In questo livello, il giocatore si ritrova in una cantina buia e deve
 * interagire con oggetti nella scena cliccando in punti specifici dell'immagine.
 * <p>
 * Le fasi principali includono:
 * - Il ritrovamento e l'accensione della torcia
 * - L'esplorazione della stanza
 * - Il recupero di un oggetto storico (bastone pastorale)
 * - Il ritorno al livello precedente
 */
public class Livello_4 {

    //coordinate delle aree cliccabili per la torcia, l'accensione della torcia e del pastorale
    private static final Rectangle pastorale = new Rectangle(272, 306, 347, 381);
    private static final Rectangle accTorcia = new Rectangle(142, 370, 201, 420);
    private static final Rectangle torcia = new Rectangle(26, 304, 86, 354);

    /**
     * Metodo principale che avvia il livello 4 dell'avventura.
     *
     * @param gui         Istanza della GUI del gioco.
     * @param inventario  Oggetto che rappresenta l'inventario del giocatore.
     * @param indovinelli Lista degli indovinelli (non utilizzata direttamente in questo livello).
     */
    public static void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        VarGlobali.setLivello(4);
        GUI.PulisciListener.pulisciTuttiListener(gui);
        gui.aggiornaImmagine2("/foto/Livello4/intro.png");
        VarGlobali.avviaConRitardo(2000, () -> {
            gui.disablebottoni();

            gui.PulisciTextArea();
            gui.aggiornaImmagine2("/foto/Livello4/caduta.png");
            gui.getButtAvanti().addActionListener(_ -> {
                GUI.PulisciListener.pulisciTuttiListener(gui);
                gui.aggiornaImmagine2("/foto/Livello4/scantinato.png");
                gui.ScriviTextArea("Premi il bottone per continuare...");
                gui.abilitabottoni();

                gui.getButtAvanti().addActionListener(_ -> {
                    gui.disablebottoni();
                    pulisciAggiorna(gui, "/foto/Livello4/ric.png");
                    gui.ScriviTextArea("""
                            Sei arrivato nella cantina. E’ tutto scuro, non si vede nulla! Questo castello da sempre gioia. 
                            Servirebbe qualcuno o qualcosa che ci facesse luce! Vedi qualcosa? Vedi una torcia?? Cliccaci sopra se la vedi!
                            \n \n 
                            """);
                    torcia(gui, inventario, indovinelli);
                });
            });
            gui.abilitabottoni();
        });
    }

    /**
     * Gestisce l'interazione con la torcia. Se cliccata, viene aggiunta all'inventario
     * e si passa alla fase successiva.
     *
     * @param gui         La GUI del gioco.
     * @param inventario  Inventario del giocatore.
     * @param indovinelli Lista degli indovinelli.
     */
    private static void torcia(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.setImageClickListener((x, y) -> {
            if (torcia.contains(x, y)) {
                inventario.aggiungi("Torcia: usata nel livello 4");
                pulisciAggiorna(gui, "/foto/Livello4/prend_tor.png");
                gui.ScriviTextArea("Narratore: Sperando che funzioni, lei manca solo che non va. Ora Cliccaci sopra per accenderla! \n \n");
                accendiTorcia(gui,inventario, indovinelli);
            } else {
                messaggiErrore.mostraWarning(null, "Punto sbagliato");
            }
        });
    }


    /**
     * Gestisce l'accensione della torcia tramite click in una determinata area.
     *
     * @param gui         La GUI del gioco.
     * @param inventario  Inventario del giocatore.
     * @param indovinelli Lista degli indovinelli.
     */
    private static void accendiTorcia(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.setImageClickListener((z, c) -> {
            if (accTorcia.contains(z, c)) {
                pulisciAggiorna(gui, "/foto/Livello4/acc_torc.png");
                gui.ScriviTextArea("Narratore: Menomale, adesso ci vediamo di più!! Guarda che disastro!! " +
                        "Visto che siamo qui perlustriamo un pò in questa robaccia e vediamo se c’è qualcosa di utile " +
                        "e prezioso da prendere come ricordo di quest’esperienza PAZZESCA!! \n \n" +
                        "\n Clicca sul bastone per continuare...\n\n");
                pastorale(gui, inventario, indovinelli);
            } else {
                messaggiErrore.mostraWarning(null, "Punto sbagliato");
            }
        });
    }

    /**
     * Gestisce il click sul pastorale (il bastone) e mostra la narrazione finale del livello.
     *
     * @param gui         La GUI del gioco.
     * @param inventario  Inventario del giocatore.
     * @param indovinelli Lista degli indovinelli.
     */
    private static void pastorale(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.setImageClickListener((a, b) -> {
            if (pastorale.contains(a, b)) {
                inventario.aggiungi("Bastone pastorale: usato nel livello 4");
                pulisciAggiorna(gui, "/foto/Livello4/bast_re.png");
                gui.ScriviTextArea("Ah ecco, bella scelta! " +
                        "Hai preso un oggetto antichissimo,appartenente alla famiglia reale che soggiornava qui nel 1200 a.C. " +
                        "Sarebbe il pastorale reale, simbolo della maestosità e padronanza di potere del re nella città. \n \n");

                SwingUtilities.invokeLater(() -> {
                    //essendo problematico il thread.sleep, blocco il programma nel caso dia errore
                    try {
                        Thread.sleep(1000);
                        gui.ScriviTextArea("Ora usciamo da questo scantinato e saliamo nuovamente al piano di " +
                                "sopra, dove ritenteremo a salire quelle maledette scale. \n\n");
                    } catch (InterruptedException ex) {
                        messaggiErrore.mostraErrore(null, "errore nel sospendere il thread" + ex.getMessage());
                    }
                });

                finale(gui, inventario, indovinelli);

            } else {
                messaggiErrore.mostraWarning(null, "Punto sbagliato");
            }
        });
    }

    /**
     * Gestisce la parte conclusiva del livello e il passaggio al livello 3.
     *
     * @param gui         La GUI del gioco.
     * @param inventario  Inventario del giocatore.
     * @param indovinelli Lista degli indovinelli.
     */
    private static void finale(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.abilitabottoni();
        gui.getButtAvanti().addActionListener(_ -> {
            gui.disablebottoni();
            Suoni.audio("/Audio/Livello3/salitaScale.wav");
            gui.aggiornaImmagine2("/foto/Livello4/uscita.png");

            VarGlobali.avviaConRitardo(1500, () -> {
                gui.aggiornaImmagine2("/foto/Livello4/salita_lvl3.png");
                VarGlobali.avviaConRitardo(1500, () -> {
                    gui.PulisciTextArea();
                    GUI.PulisciListener.pulisciTuttiListener(gui);
                    Livello_3.main(gui, inventario, indovinelli);
                });
            });
        });
    }

    /**
     * Metodo usato per aggiornare l'immagine e pulire i listener e la text area.
     *
     * @param gui      La GUI del gioco.
     * @param percorso Percorso dell'immagine da visualizzare.
     */
    private static void pulisciAggiorna(GUI gui, String percorso) {
        gui.aggiornaImmagine2(percorso);
        GUI.PulisciListener.pulisciTuttiListener(gui);
        gui.PulisciTextArea();
    }
}