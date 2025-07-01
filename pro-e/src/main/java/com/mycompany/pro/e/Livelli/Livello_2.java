package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfacce.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * La classe Livello_2 gestisce il secondo livello del gioco,
 * suddiviso in due sottolivelli: Livello 2A (Stanza dei misteri) e Livello 2B (Stanza del fantasma).
 *
 * <p>Il livello presenta:
 * <ul>
 *   <li>Una scelta iniziale tra due porte che conducono ai sottolivelli</li>
 *   <li>Meccaniche di risposta a indovinelli (multipla e aperta)</li>
 *   <li>Sistema di tentativi e possibilità di fuga</li>
 *   <li>Gestione dell'inventario e progressione della storia</li>
 * </ul>
 *
 * <p>Utilizza immagini, suoni e dialoghi per creare un'esperienza interattiva.
 *
 * @see GUI
 * @see Inventario
 * @see Indovinello
 */
public class Livello_2 {

    public Livello_2() {
        VarGlobali.setUltimoLivello('a');
        VarGlobali.setPassatoA(false);
        VarGlobali.setPassatoB(false);
    }

    /**
     * Metodo principale che avvia il livello 2, mostrando la scelta iniziale tra le due porte.
     *
     * @param gui         L'interfaccia grafica del gioco
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli disponibili per il livello
     */
    public void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        VarGlobali.setLivello(2);
        Suoni.fermaAudio();
        gui.disablebottoni();
        gui.aggiornaImmagine2("/foto/Livello2/intro.png");
        VarGlobali.avviaConRitardo(1000, () -> {
            gui.aggiornaImmagine2("/foto/Livello2/scelta_porte.png");
            gui.ScriviTextArea(" Narratore: Sei entrato nel castello! " +
                    "Da qui ci sono due stanze da visitare, le quali sono: Stanza dei misteri ed Stanza del fantasma." +
                    "\n\nDecidi tu da dove vuoi iniziare cliccando su una delle due freccie. \n\n");

            gui.setImageClickListener((x, y) -> {
                Rectangle freccia_sx = new Rectangle(120, 225, 206, 274);
                Rectangle freccia_dx = new Rectangle(301, 176, 377, 241);

                if (freccia_sx.contains(x, y)) {
                    transizioneFreccia(gui);
                    VarGlobali.avviaConRitardo(2000, () -> {
                        gui.PulisciTextArea();
                        new livello2_a(gui, inventario, indovinelli).start();
                    });
                } else if (freccia_dx.contains(x, y)) {
                    transizioneFreccia(gui);
                    VarGlobali.avviaConRitardo(2000, () -> {
                        gui.PulisciTextArea();
                        new livello2_b(gui, inventario, indovinelli).start();
                    });
                } else {
                    messaggiErrore.mostraWarning(null, "\n \nPunto cliccato non valido, riprova cliccando sulle due frecce \n\n");
                }
            });
        });
    }

    /**
     * Gestisce la transizione quando il giocatore sceglie una porta.
     *
     * @param gui L'interfaccia grafica
     */
    private void transizioneFreccia(GUI gui) {
        gui.setImageClickListener(null);
        gui.ScriviTextArea("\n\nBene, hai scelto la porta peggiore delle due, divertiti");
        Suoni.audio("/Audio/Livello2/ride.wav");
    }

    /**
     * Classe interna che gestisce il sottolivello 2A (Stanza dei misteri).
     */
    public class livello2_a extends Thread {
        private final GUI gui;
        private final List<Indovinello> indovinelli;
        private final Inventario inventario;

        /**
         * Costruttore per il sottolivello 2A.
         *
         * @param gui         L'interfaccia grafica
         * @param inventario  L'inventario del giocatore
         * @param indovinelli Lista degli indovinelli
         */
        public livello2_a(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
            this.gui = gui;
            this.indovinelli = indovinelli;
            this.inventario = inventario;
        }

        @Override
        public void run() {
            Suoni.fermaAudio();
            SwingUtilities.invokeLater(() -> {
                gui.PulisciTextArea();
                Suoni.audio("/Audio/Livello2/porta.wav");

                gui.abilitabottoni();

                String[] immagini = {
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png"
                };

                String[] testi = {
                        "Falegname: Salve caro. Questa è la stanza dei misteri, chiamata così perché succedono cose strane e spettrali. \n \n",
                        "Francesco: Inizia bene questa esplorazione, ora come faccio ad uscire da qui?\n \n",
                        "Falegname: Devi risolvere 4 indovinelli a risposta multipla e io sparirò. " +
                                "Se non sai le risposte io non mi sposterò e l’unica tua via di uscita sarà scappare dalla" +
                                "finestra. \n \n",
                        "Francesco: OH MADOO! Questa ci mancava come via di fuga! \n \n",
                        "Falegname:Iniziamo…  \n \n"
                };

                SwingUtilities.invokeLater(() -> VarGlobali.mostraDialoghi(gui, testi, immagini, () -> rispMult(gui, inventario, indovinelli)));
            });
        }
    }

    /**
     * Gestisce il meccanismo di risposta multipla per gli indovinelli del livello 2A.
     *
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void rispMult(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        int tentativi = 0, i = 0, numeroCasuale;
        char rispostaCorretta, risposta;
        VarGlobali.setUltimoLivello('a');
        VarGlobali.setPassatoA(false);
        VarGlobali.setLivello2A_Eseguito(false);

        if (indovinelli.size() < 3) {
            messaggiErrore.mostraWarning(null, "Non ci sono abbastanza indovinelli disponibili!");
            return;
        }

        do {
            gui.disablebottoni();

            if (indovinelli.isEmpty()) break;

            //genera un numero casuale
            numeroCasuale = VarGlobali.generaRand(indovinelli.size() - 1);
            Indovinello indovinello = indovinelli.get(numeroCasuale);
            String domanda = indovinello.getDomanda();
            String[] opzioni = indovinello.getOpzioni();
            rispostaCorretta = indovinello.getRispostaCorretta();

            //mischia gli indovinelli
            List<String> opzioniList = new ArrayList<>(List.of(opzioni));
            List<String> opzioniMischiate = new ArrayList<>(opzioniList);
            Collections.shuffle(opzioniMischiate);

            int indiceCorretta = opzioniMischiate.indexOf(opzioni[rispostaCorretta - 'a']);
            char nuovaRispostaCorretta = (char) ('a' + indiceCorretta);

            RispChiuse risp = new RispChiuse(null, true);

            risp.TextAreaRispChiu(domanda);
            risp.setBottoneAText("a) " + opzioniMischiate.get(0));
            risp.setBottoneBText("b) " + opzioniMischiate.get(1));
            risp.setBottoneCText("c) " + opzioniMischiate.get(2));
            risp.setBottoneDText("d) " + opzioniMischiate.get(3));
            risp.setVisible(true);

            risposta = risp.getRisposta().charAt(0);
            if (risposta == nuovaRispostaCorretta) {
                messaggiErrore.mostraConferma(null, "La risposta è giusta, bravo");
                i++;
                indovinelli.remove(numeroCasuale);

                if (i == 3) {
                    VarGlobali.setPassatoA(true);
                    VarGlobali.setLivello2A_Eseguito(true);
                    risp.setVisible(false);
                    uscitaLivello2a(gui, inventario, indovinelli);
                    break;
                }
            } else {
                messaggiErrore.mostraWarning(null, "\n \nLa risposta è sbagliata, passiamo al prossimo");
                tentativi++;
                verificaFuga(tentativi, gui, inventario, indovinelli);
                //rimuovo l'indovinello dalla lista per evitare ripetizioni
                indovinelli.remove(numeroCasuale);
            }
        } while (!VarGlobali.getPassatoA());
    }

    /**
     * Gestisce l'uscita dal livello 2A dopo aver completato gli indovinelli.
     *
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void uscitaLivello2a(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        VarGlobali.setLivello2A_Eseguito(true);
        gui.ScriviTextArea("Prego messier! Ora puoi passare da questa porta e raggiungere il tuo prossimo livello. " +
                "\n\nPer proseguire al prossimo livello, clicca sulla porta per continuare.... \n");
        gui.aggiornaImmagine2("/foto/Livello2/porta_SX_senza_mustaccio.png");
        //creo il rettangolo (la porta nell'immagine) dopo può cliccare per proseguire
        gui.setImageClickListener((x, y) -> {
            Rectangle porta = new Rectangle(354, 183, 474, 410);
            //se il livello b non è stato eseguito, vai in quello, altrimenti vai al 3
            if (porta.contains(x, y)) {
                gui.setImageClickListener(null);

                if (!VarGlobali.getLivello2B_Eseguito()) {
                    gui.setImageClickListener(null);
                    new livello2_b(gui, inventario, indovinelli).start();

                } else {
                    gui.setImageClickListener(null);
                    VarGlobali.setUltimoLivello('a');
                    gui.disablebottoni();
                    gui.PulisciTextArea();
                    Suoni.audio("/Audio/tromba.wav");
                    gui.aggiornaImmagine2("/foto/Livello3/intro.png");
                    VarGlobali.avviaConRitardo(2000, () -> Livello_3.main(gui, inventario, indovinelli));
                }
            } else {
                messaggiErrore.mostraWarning(null, "Punto sbagliato");
            }
        });
    }

    /**
     * Classe interna che gestisce il sottolivello 2B (Stanza del fantasma).
     */
    public class livello2_b extends Thread {


        private final GUI gui;
        private final List<Indovinello> indovinelli;
        private final Inventario inventario;

        /**
         * Costruttore per il sottolivello 2B.
         *
         * @param gui         L'interfaccia grafica
         * @param inventario  L'inventario del giocatore
         * @param indovinelli Lista degli indovinelli
         */
        public livello2_b(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
            this.gui = gui;
            this.indovinelli = indovinelli;
            this.inventario = inventario;
        }

        @Override
        public void run() {

            Suoni.fermaAudio();
            gui.PulisciTextArea();
            gui.abilitabottoni();

            String[] immagini = {
                    "/foto/Livello2/portaDX_no_fantasma.png",
                    "/foto/Livello2/porta_DX.png",
                    "/foto/Livello2/porta_DX.png",
                    "/foto/Livello2/porta_DX.png",
                    "/foto/Livello2/porta_DX.png"

            };

            String[] testi = {
                    "Narratore: Questa è la stanza del fantasma, fai attenzione che può apparire da un momento all’altro\n\n",
                    "Francesco: Ma che brutto castello! E’ un continuo spavento!\n\n",
                    "Narratore: Se vuoi passare al prossimo livello cosicché il fantasma la finisce di cantare le sue meravigliose melodie, " +
                            "devi trovare lo spray nocivo in modo tale da uccidere il fantasma intossicandolo e uscire dalla stanza dall’uscita secondaria.  \n\n",
                    "Narratore: per fare questo, ti indico un indovinello che ti saprà dire il posto esatto dove è nascosto lo spray che ti serve.\n\n "
            };

            SwingUtilities.invokeLater(() -> VarGlobali.mostraDialoghiConAudio(gui, testi, immagini, 1, "/Audio/Livello2/fantasma.wav", () -> domandaAperta(gui, inventario, indovinelli)));

        }

    }

    /**
     * Gestisce l'uscita dal livello 2B dopo aver completato l'indovinello.
     *
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void uscitaLivello2b(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        //creo il rettangolo della bomboletta per cacciare il fantasma
        gui.setImageClickListener((x, y) -> {
            Rectangle bomboletta = new Rectangle(246, 355, 294, 413);
            if (bomboletta.contains(x, y)) {
                inventario.aggiungi("bomboletta: usata nel livello 2b");
                gui.setImageClickListener(null);
                Suoni.audio("/Audio/Livello2/bomboletta.wav");
                gui.aggiornaImmagine2("/foto/Livello2/portaDX_no_fantasma.png");
                gui.ScriviTextArea("\n \nClicca sulla freccia a sinistra per continuare");
                gui.setImageClickListener((x1, y1) -> {
                    Rectangle porta = new Rectangle(126, 276, 211, 323);
                    //se il livello a non è stato eseguito, vai in quello, altrimenti vai al 3
                    if (porta.contains(x1, y1)) {
                        gui.setImageClickListener(null);
                        if (!VarGlobali.Livello2A_Eseguito()) {
                            gui.setImageClickListener(null);
                            new livello2_a(gui, inventario, indovinelli).start();
                        } else {
                            gui.setImageClickListener(null);
                            VarGlobali.setUltimoLivello('b');
                            gui.disablebottoni();
                            gui.PulisciTextArea();
                            Suoni.audio("/Audio/tromba.wav");
                            gui.aggiornaImmagine2("/foto/Livello3/intro.png");
                            VarGlobali.avviaConRitardo(2000, () -> Livello_3.main(gui, inventario, indovinelli));
                        }
                    } else {
                        messaggiErrore.mostraWarning(null, "Punto sbagliato");
                    }
                });
            } else {
                messaggiErrore.mostraWarning(null, "Hai cliccato nel punto sbagliato");
            }
        });
    }

    /**
     * Gestisce il meccanismo di risposta aperta per l'indovinello del livello 2B.
     *
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void domandaAperta(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        boolean giusto = false;
        int tentativi = 0;
        VarGlobali.setUltimoLivello('b');

        do {
            String Risposta;
            RispAperte risp = new RispAperte(null, true);
            risp.ScriviTextArea("Sono una finestra senza vetri," +
                    "un mondo fermo nel tempo. \n" +
                    "Posso essere un volto, un paesaggio," +
                    "o persino un sogno... cosa sono? ");
            risp.setVisible(true);
            Risposta = risp.TestoAreaInput();
            if (Parser.verificaRisposta(Risposta, "quadro")) {
                risp.setVisible(false);
                VarGlobali.setLivello2B_Eseguito(true);
                giusto = true;
                gui.disablebottoni();
                messaggiErrore.mostraConferma(null, "Giusto, clicca sulla bomboletta per proseguire");

                uscitaLivello2b(gui, inventario, indovinelli);

            } else {
                messaggiErrore.mostraWarning(null, "\n \nLa risposta è sbagliata");
                tentativi++;
                verificaFuga(tentativi, gui, inventario, indovinelli);
            }
        } while (!giusto && !VarGlobali.getPassatoB());
    }

    /**
     * Verifica se il giocatore ha superato il numero massimo di tentativi e gestisce la fuga.
     *
     * @param tentativi   Numero di tentativi falliti
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void verificaFuga(int tentativi, GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        if (tentativi > 2) {
            char dec = VarGlobali.Decisioni("Vuoi continuare a rispondere, oppure vuoi scappare? \n\nRispondi con no se vuoi scappare o con si se vuoi continuare");
            if (dec == 'S') {
                gui.ScriviTextArea("Hai scelto di continuare! \n");
            } else if (dec == 'N') {

                //verifico l'ultimo livello chiamante per interrompere le domande
                if (VarGlobali.getUltimoLivello() == 'a') {
                    VarGlobali.setPassatoA(true);
                } else if (VarGlobali.getUltimoLivello() == 'b') {
                    VarGlobali.setPassatoB(true);
                }
                gui.ScriviTextArea("\n Hai scelto di uscire dal livello.");
                gui.ScriviTextArea("\n Clicca sulla finestra per fuggire");
                if (VarGlobali.getUltimoLivello() == 'a') {
                    fuga(gui, 29, 23, 155, 167, "/foto/Livello2/caduta_win_SX.png", "/foto/Livello2/caduta_SX.png", inventario, indovinelli);
                } else {
                    fuga(gui, 1, 116, 120, 489, "/foto/Livello2/caduta_win_DX.png", "/foto/Livello2/caduta_DX.png", inventario, indovinelli);
                }
            } else {
                messaggiErrore.mostraWarning(null, "Non hai selezionato nessuna risposta.");
            }
        }
    }

    /**
     * Gestisce la sequenza di fuga dal livello.
     *
     * @param gui         L'interfaccia grafica
     * @param x           Coordinata x dell'area cliccabile
     * @param y           Coordinata y dell'area cliccabile
     * @param largh       Larghezza dell'area cliccabile
     * @param alt         Altezza dell'area cliccabile
     * @param perc        Percorso immagine vittoria fuga
     * @param perc2       Percorso immagine transizione
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private void fuga(GUI gui, int x, int y, int largh, int alt, String perc, String perc2, Inventario inventario, List<Indovinello> indovinelli) {

        gui.disablebottoni();
        Rectangle contenitore = new Rectangle(x, y, largh, alt);
        //in base alle coordinate del livello, setto l'uscita
        gui.setImageClickListener((x1, y1) -> {
            if (contenitore.contains(x1, y1)) {
                gui.setImageClickListener(null);
                Suoni.audio("/Audio/Livello2/caduta.wav");
                gui.setImageClickListener(null);
                gui.aggiornaImmagine2(perc);

                VarGlobali.avviaConRitardo(1000, () -> {
                    gui.aggiornaImmagine2(perc2);
                    GUI.PulisciListener.pulisciTuttiListener(gui);
                    VarGlobali.avviaConRitardo(750, () -> {
                        gui.PulisciTextArea();
                        Livello_2 livello = new Livello_2();
                        livello.main(gui, inventario, indovinelli);
                    });
                });
            }
        });
    }

}



