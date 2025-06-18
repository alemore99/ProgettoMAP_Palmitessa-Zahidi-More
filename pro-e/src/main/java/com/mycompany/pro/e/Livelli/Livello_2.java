package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * Livello 2 dell'avventura
 */
public class Livello_2 {

    /**
     *
     */
    public Livello_2() {
    }

    /**
     * @param gui
     * @param indovinelli
     */
    public void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        Suoni.fermaAudio();
            gui.aggiornaImmagine2("/foto/Livello2/scelta_porte.png");

            gui.ScriviTextArea(" Narratore: Sei entrato nel castello! " +
                    "Qui ci sono due stanze da visitare, le quali sono: Stanza dei misteri ed l’altra Stanza del fantasma." +
                    "\n\nDecidi tu da dove vuoi iniziare cliccando sulla porta. \n\n");

            gui.setImageClickListener((x, y) -> {
                Rectangle freccia_sx = new Rectangle(120,225,206,274);
                Rectangle freccia_dx = new Rectangle(301,176,377,241);

                //freccia sinistra
                if (freccia_sx.contains(x, y)) {
                    gui.setImageClickListener(null);
                    gui.ScriviTextArea("\n\nBene, hai scelto la porta peggiore delle due, divertiti");
                    Suoni.audio("/Audio/Livello2/ride.wav");
                    //avvio dopo 2 secondi il livello scelto
                    Timer t3 = new Timer(2500, _ -> {
                        gui.PulisciTextArea();
                        new livello2_a(gui, indovinelli, inventario).start();
                    });
                    t3.setRepeats(false);
                    t3.start();

                    //freccia destra
                } else if (freccia_dx.contains(x, y)) {
                    gui.setImageClickListener(null);
                    gui.ScriviTextArea("\n\nBene, hai scelto la porta peggiore delle due, divertiti");
                    Suoni.audio("/Audio/Livello2/ride.wav");
                    //avvio dopo 2 secondi il livello scelto
                    Timer t4 = new Timer(2500, t2 -> {
                        gui.PulisciTextArea();
                        new livello2_b(gui, indovinelli, inventario).start();
                    });
                    t4.setRepeats(false);
                    t4.start();
                } else {
                    messaggiErrore.mostraWarning(null,"\n \nPunto cliccato non valido, riprova cliccando sulle due frecce \n\n");
                }
            });
        }

    /**
     * Thread usato per la porta di sinistra
     */
    public class livello2_a extends Thread {
        private final GUI gui;
        private final List<Indovinello> indovinelli;
        private final Inventario inventario;

        /**
         *
         * @param gui
         * @param indovinelli
         * @param inventario
         */
        public livello2_a(GUI gui, List<Indovinello> indovinelli, Inventario inventario) {
            this.gui = gui;
            this.indovinelli = indovinelli;
            this.inventario = inventario;
        }

        /**
         *
         */
        @Override
        public void run() {
            Suoni.fermaAudio();
            SwingUtilities.invokeLater(() -> {
                gui.PulisciTextArea();
                Suoni.audio("/Audio/Livello2/porta.wav");

                gui.abilitabottoni();

                //stringa di immagini e testi da vederesi in successione
                String[] immagini = {
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png",
                        "/foto/Livello2/porta_SX.png"
                };

                String[] testi = {
                        "Falegname: Salve caro. Questa è la stanza dei misteri, chiamata così perché succedono cose strane e spettrali. \n \n",
                        "Francesco: E ora come faccio ad uscire?\n \n",
                        "Falegname: Devi risolvere 4 indovinelli a risposta multipla e io sparirò. " +
                                "Se non sai le risposte io non mi sposterò e l’unica tua via di uscita sarà scappare dalla" +
                                "finestra. \n \n",
                        "Francesco: OH MIO DIO! Spero di rispondere correttamente a tutto allora! \n \n",
                        "Falegname: Vedremo! Iniziamo…  \n \n"
                };
                final int[] i = {0};

                gui.aggiornaImmagine2(immagini[i[0]]);
                gui.ScriviTextArea(testi[i[0]]);
                gui.abilitabottoni();

                GUI.PulisciListener.pulisciTuttiListener(gui);

                //mostro i vari test a ogni click del bottone
                gui.buttAvanti.addActionListener(_ -> {
                    i[0]++;
                    if (i[0] < testi.length) {
                        gui.aggiornaImmagine2(immagini[i[0]]);
                        gui.ScriviTextArea(testi[i[0]]);
                    } else {
                        rispMult(gui, indovinelli, inventario);
                    }
                });
            });
        }
    }


    /**
     * Thread del sotto livello destro
     */
    public class livello2_b extends Thread {


        //creo il nuovo oggetto
        public livello2_b(GUI gui, List<Indovinello> indovinelli, Inventario inventario) {
            this.gui = gui;
            this.indovinelli = indovinelli;
            this.inventario = inventario;
        }

        private final GUI gui;
        private final List<Indovinello> indovinelli;
        private final Inventario inventario;

        /**
         * Executes the sequence of actions for the second level of the game. This method is responsible
         * for managing the visual and textual updates, playing audio, and setting up interactions
         * for navigating through the level.
         * <p>
         * The*/
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

            final int[] i = {0};

            gui.aggiornaImmagine2(immagini[i[0]]);
            gui.ScriviTextArea(testi[i[0]]);
            gui.abilitabottoni();

            GUI.PulisciListener.pulisciTuttiListener(gui);

            //mostro i vari testi
            gui.buttAvanti.addActionListener(e -> {
                i[0]++;
                if (i[0] < testi.length) {
                    gui.aggiornaImmagine2(immagini[i[0]]);
                    gui.ScriviTextArea(testi[i[0]]);
                    //suono quando compare il fantasma
                    if (i[0] == 1) {
                        Suoni.audio("/Audio/Livello2/fantasma.wav");
                    };
                } else {
                    domandaAperta(gui, indovinelli, inventario);
                }
            });
        }
    }

    //metodo che si occupa di far vedere gli indovinelli chiusi ed poi proseguire
    private void rispMult (GUI gui, List < Indovinello > indovinelli, Inventario inventario){

        int tentativi = 0, i = 0, numeroCasuale = 0;
        boolean passato = false;
        char rispostaCorretta, risposta;

        //chiedo gli indovinelli
        while (!indovinelli.isEmpty() && !passato) {
            gui.disablebottoni();

            numeroCasuale = VarGlobali.generaRand(indovinelli.size());
            Indovinello indovinello = indovinelli.get(numeroCasuale);
            String domanda = indovinello.domanda;
            String[] opzioni = indovinello.opzioni;
            rispostaCorretta = indovinello.rispostaCorretta;

            //mischio gli indovinelli ed trovo la nuova posizione da assegnare
            List<String> opzioniList = new ArrayList<>(List.of(opzioni));
            List<String> opzioniMischiate = new ArrayList<>(opzioniList);
            Collections.shuffle(opzioniMischiate);

            //assegno la nuova lettera corretta
            int indiceCorretta = opzioniMischiate.indexOf(opzioni[rispostaCorretta - 'a']);
            char nuovaRispostaCorretta = (char) ('a' + indiceCorretta);

            RispChiuse risp = new RispChiuse(null, true);

            //passo i vari testi alla jdialog
            risp.TextAreaRispChiu(domanda);
            risp.setBottoneAText("a) " + opzioniMischiate.get(0));
            risp.setBottoneBText("b) " + opzioniMischiate.get(1));
            risp.setBottoneCText("c) " + opzioniMischiate.get(2));
            risp.setBottoneDText("d) " + opzioniMischiate.get(3));
            risp.setVisible(true);

            //prendo solo il cattere singola della risposta, essendo risp.getRisposta una stringa
            risposta = risp.getRisposta().charAt(0);
            if (risposta == nuovaRispostaCorretta) {
                messaggiErrore.mostraConferma(null, "La risposta è giusta, bravo");
                i++;
                indovinelli.remove(indovinelli.get(numeroCasuale)); //elimino l'indovinello se ha risposto correttamente
                if (i == 3) {
                    risp.setVisible(false);
                    passato = true;
                    VarGlobali.livello2A_Eseguito = true;
                    gui.ScriviTextArea("Prego messier! Ora puoi passare da questa porta e raggiungere il tuo prossimo livello. " +
                            "\n\nPer proseguire al prossimo livello, clicca sulla porta per continuare.... \n");
                    gui.aggiornaImmagine2("/foto/Livello2/porta_SX_senza_mustaccio.png");
                    //creo il rettangolo (la porta nell'immagine) dopo può cliccare per proseguire
                    gui.setImageClickListener((x, y) -> {
                        Rectangle porta = new Rectangle(354, 183, 474, 410);
                        //se il livello b non è stato eseguito, vai in quello, altrimenti vai al 3
                        if (porta.contains(x, y)) {
                            gui.setImageClickListener(null);
                            if (!VarGlobali.livello2B_Eseguito) {
                                gui.setImageClickListener(null);
                                new livello2_b(gui, indovinelli, inventario).start();
                            } else {
                                gui.setImageClickListener(null);
                                VarGlobali.ultimoLivello = 'a';
                                gui.disablebottoni();
                                gui.PulisciTextArea();
                                gui.aggiornaImmagine2("/foto/Livello3/intro.png");
                                Suoni.audio("/Audio/tromba.wav");
                                javax.swing.Timer timer = new javax.swing.Timer(1500, _ -> {
                                    Livello_3.main(gui, indovinelli, inventario);
                                });
                                timer.setRepeats(false);
                                timer.start();
                            }
                        } else {
                            messaggiErrore.mostraWarning(null, "Punto sbagliato");
                        }
                    });
                }
            } else {
                messaggiErrore.mostraWarning(null, "\n \nLa risposta è sbagliata, passiamo al prossimo");
                tentativi++;
                //se al terzo tentativo, allora li proponga la fuga
                if (tentativi > 2) {
                    char dec = decisioneUtente();
                    if (dec == 'S') {
                        gui.ScriviTextArea("Hai scelto di continuare!");
                    } else if (dec == 'N') {
                        gui.ScriviTextArea("\n Hai scelto di uscire dal livello.");
                        gui.ScriviTextArea("\n Clicca sulla finestra per fuggire");
                        fuga(gui, 29, 23, 155, 167, "/foto/Livello2/caduta_win_SX.png", "/foto/Livello2/caduta_SX.png", inventario, indovinelli);
                        passato = true;
                    } else {
                        messaggiErrore.mostraWarning(null, "Non hai selezionato nessuna risposta.");
                    }
                }
            }
        }
    }

    //metodo che si occupa di mostrare la domanda aperta del fantasma, continuando poi se risponde giusto
    private void domandaAperta(GUI gui, List < Indovinello > indovinelli, Inventario inventario) {
        boolean passato = false;
        boolean giusto = false;

        int tentativi = 0;
        //ciclo per far chiedere la domanda fin quando non è giusta
        while (!giusto && !passato) {
            String Risposta;
            RispAperte risp = new RispAperte(null, true);
            risp.ScriviTextArea("Non sono vivo. Ti " +
                    "osservo da lontano e nascondo qualcosa di prezioso. Cosa sono? ");
            risp.setVisible(true);
            Risposta = risp.TestoAreaInput();
            //membro statico, quindi lo chiamo tramite la classe e non tramite un oggetto
            if (Parser.verificaRisposta(Risposta, "quadro")) {
                risp.setVisible(false);
                VarGlobali.livello2B_Eseguito = true;
                giusto = true;
                gui.disablebottoni();
                messaggiErrore.mostraConferma(null,"Risposta giusta, clicca sulla bomboletta per proseguire");

                //creo il rettangolo della bomboletta per cacciare il fantasma
                gui.setImageClickListener((x, y) -> {
                    Rectangle bomboletta = new Rectangle(246, 355, 294, 413);
                    //se il livello a non è stato eseguito, vai in quello, altrimenti vai al 3
                    if (bomboletta.contains(x, y) ) {
                        inventario.aggiungi("bomboletta: usata nel livello 2b");
                        gui.setImageClickListener(null);
                        Suoni.audio("/Audio/Livello2/bomboletta.wav");
                        gui.aggiornaImmagine2("/foto/Livello2/portaDX_no_fantasma.png");
                        gui.ScriviTextArea("\n \nClicca sulla freccia a sinistra per continuare");
                        gui.setImageClickListener(new ImageClickListener() {
                            @Override
                            public void onImageClick(int x, int y) {
                                Rectangle porta = new Rectangle(126, 276, 211, 323);
                                //se il livello a non è stato eseguito, vai in quello, altrimenti vai al 3
                                if (porta.contains(x, y)) {
                                    gui.setImageClickListener(null);
                                    if (!VarGlobali.livello2A_Eseguito) {
                                        gui.setImageClickListener(null);
                                        new livello2_a(gui, indovinelli, inventario).start();
                                    } else {
                                        gui.setImageClickListener(null);
                                        VarGlobali.ultimoLivello = 'b';
                                        gui.disablebottoni();
                                        gui.PulisciTextArea();
                                        gui.aggiornaImmagine2("/foto/Livello3/intro.png");
                                        Suoni.audio("/Audio/tromba.wav");
                                        Timer timer = new Timer(2000, __ -> {
                                            Livello_3.main(gui, indovinelli, inventario);
                                        });
                                        timer.setRepeats(false);
                                        timer.start();
                                    }
                                }else {
                                    messaggiErrore.mostraWarning(null,"Punto sbagliato");
                                }
                            }
                        });
                    } else {
                        messaggiErrore.mostraWarning(null, "Hai cliccato nel punto sbagliato");
                    }
                });
            } else {
                messaggiErrore.mostraWarning(null, "\n \nLa risposta è sbagliata");
                tentativi++;
                if (tentativi > 2) {
                    char dec = decisioneUtente();
                    if (dec == 'S') {
                        gui.ScriviTextArea("Hai scelto di continuare!");
                    } else if (dec == 'N') {
                        gui.ScriviTextArea("\n Hai scelto di uscire dal livello.");
                        gui.ScriviTextArea("\n Clicca sulla finestra per fuggire");
                        fuga(gui, 6, 143, 102, 498, "/foto/Livello2/caduta_win_DX.png", "/foto/Livello2/caduta_DX.png", inventario, indovinelli);
                        passato = true;
                    } else {
                        messaggiErrore.mostraWarning(null, "Non hai selezionato nessuna risposta.");
                    }
                }
            }

        }
    }

    //metodo che si occuppa di prendere la decisione di fuggire o meno
    private char decisioneUtente() {
        InputBox siNo;
            siNo = new InputBox(null, true);
            siNo.ScriviBox("Vuoi continuare a rispondere, oppure vuoi scappare via? \n\nRispondi con no se vuoi scappare o con si se vuoi continuare");
            siNo.setVisible(true);
            return siNo.getRisposta();
    }

    //metodo che si occupa della fuga nel caso voglia scappare
    private void fuga(GUI gui, int x, int y, int largh, int alt, String perc, String perc2, Inventario inventario, List < Indovinello > indovinelli) {
        gui.disablebottoni();
        Rectangle contenitore = new Rectangle(x, y, largh, alt);
        //in base alle coordinate del livello, setto l'uscita
        gui.setImageClickListener((x1, y1) -> {
            if (contenitore.contains(x1, y1)) {
                gui.setImageClickListener(null);
                Suoni.audio("/Audio/Livello2/caduta.wav");
                gui.setImageClickListener(null);
                gui.aggiornaImmagine2(perc);

                //mostro 2 foto ed avvio livello principale
                new Timer(1000, p -> {
                    gui.aggiornaImmagine2(perc2);
                    ((Timer) p.getSource()).stop();

                    GUI.PulisciListener.pulisciTuttiListener(gui);
                    new Timer(1000, t -> {
                        gui.PulisciTextArea();
                        Livello_2 livello = new Livello_2();
                        livello.main(gui, inventario, indovinelli);
                        ((Timer) t.getSource()).stop();
                    }).start();
                }).start();
            }
        });
    }


}

