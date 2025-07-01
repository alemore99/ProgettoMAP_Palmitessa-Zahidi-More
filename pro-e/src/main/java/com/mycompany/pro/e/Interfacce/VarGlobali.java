package com.mycompany.pro.e.Interfacce;

import com.mycompany.pro.e.Livelli.Suoni;

import javax.swing.*;
import java.util.Random;

/**
 * Classe che contiene variabili e metodi utilitari globali condivisi tra più componenti dell'applicazione.
 * Fornisce funzionalità per:
 * - Generazione di numeri casuali
 * - Esecuzione di azioni con ritardo
 * - Gestione dello stato globale dell'applicazione
 * - Visualizzazione di sequenze di dialoghi
 */
public class VarGlobali {

    private static int Livello = 1;

    private static final Random RANDOM = new Random();
    /**
     * Carattere che rappresenta l'ultimo livello completato nel livello 2.
     * Valori ammessi: 'a', 'b'
     */
    private static char ultimoLivello = 'a';
    /**
     * Flag che indica se il livello 2A o 2B sono stati eseguiti.
     */
    private static boolean livello2A_Eseguito = false;
    private static boolean livello2B_Eseguito = false;
    /**
     * Flag che indica se il giocatore è passato dal punto A
     * o se è passato dal punto B.
     */
    private static boolean passatoA = false;
    private static boolean passatoB = false;
    /**
     * Probabilità utilizzata nel livello 3 (valore predefinito: 60).
     * Scivolata = contatore usato per indicare le scivolate nel livello 3
     */
    private static int probabilita = 60;
    private static int scivolata = 0;

    public static int getLivello() {
        return Livello;
    }

    public static void setLivello(int livello) {
        Livello = livello;
    }

    /**
     * Genera un numero casuale tra 0 (incluso) e max (escluso)
     *
     * @param max Il limite superiore (escluso) per il numero casuale
     * @return Un numero casuale tra 0 e max-1
     * @throws IllegalArgumentException se max <= 0
     */

    public static int generaRand(int max) {
            if (max <= 0) {
                throw new IllegalArgumentException("Il valore massimo deve essere positivo");
            }
            return RANDOM.nextInt(max) + 1;
        }


    /**
     * Avvia l'esecuzione di un'azione {@link Runnable} dopo un determinato ritardo.
     * <p>
     * Questo metodo utilizza un {@link javax.swing.Timer} per eseguire l'azione specificata
     * una sola volta, dopo un ritardo espresso in millisecondi.
     * </p>
     *
     * @param millis il ritardo in millisecondi prima di eseguire l'azione
     * @param azione l'azione da eseguire dopo il ritardo
     * @throws IllegalArgumentException se {@code millis} è negativo o {@code azione} è {@code null}
     */
    public static void avviaConRitardo(int millis, Runnable azione) {
        Timer timer = new Timer(millis, _ -> azione.run());
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Restituisce l'ultimo livello completato nel livello 2.
     *
     * @return il carattere rappresentante l'ultimo livello completato
     */
    public static char getUltimoLivello() {
        return ultimoLivello;
    }

    /**
     * Imposta l'ultimo livello completato nel livello 2.
     *
     * @param ultimoLivello il carattere rappresentante il livello completato
     */
    public static void setUltimoLivello(char ultimoLivello) {
        VarGlobali.ultimoLivello = ultimoLivello;
    }

    /**
     * Restituisce lo stato di esecuzione del livello 2A.
     *
     * @return true se il livello 2A è stato eseguito, false altrimenti
     */
    public static boolean Livello2A_Eseguito() {
        return livello2A_Eseguito;
    }

    /**
     * Imposta lo stato di esecuzione del livello 2A.
     *
     * @param livello2A_Eseguito lo stato da impostare
     */
    public static void setLivello2A_Eseguito(boolean livello2A_Eseguito) {
        VarGlobali.livello2A_Eseguito = livello2A_Eseguito;
    }

    /**
     * Restituisce lo stato di esecuzione del livello 2B.
     *
     * @return true se il livello 2B è stato eseguito, false altrimenti
     */
    public static boolean getLivello2B_Eseguito() {
        return livello2B_Eseguito;
    }

    /**
     * Imposta lo stato di esecuzione del livello 2B.
     *
     * @param livello2B_Eseguito lo stato da impostare
     */
    public static void setLivello2B_Eseguito(boolean livello2B_Eseguito) {
        VarGlobali.livello2B_Eseguito = livello2B_Eseguito;
    }

    /**
     * Restituisce lo stato di passaggio dal punto A.
     *
     * @return true se il giocatore è passato dal punto A, false altrimenti
     */
    public static boolean getPassatoA() {
        return passatoA;
    }

    /**
     * Imposta lo stato di passaggio dal punto A.
     *
     * @param passatoA lo stato da impostare
     */
    public static void setPassatoA(boolean passatoA) {
        VarGlobali.passatoA = passatoA;
    }

    /**
     * Restituisce lo stato di passaggio dal punto A.
     *
     * @return true se il giocatore è passato dal punto A, false altrimenti
     */
    public static boolean getPassatoB() {
        return passatoB;
    }

    /**
     * Imposta lo stato di passaggio dal punto B.
     *
     * @param passatoB lo stato da impostare
     */
    public static void setPassatoB(boolean passatoB) {
        VarGlobali.passatoB = passatoB;
    }

    /**
     * Restituisce il valore di probabilità utilizzato nel livello 3.
     *
     * @return il valore di probabilità corrente
     */
    public static int getProbabilita() {
        return probabilita;
    }

    /**
     * Imposta il valore di probabilità per il livello 3.
     *
     * @param probabilita il nuovo valore di probabilità
     */
    public static void setProbabilita(int probabilita) {
        VarGlobali.probabilita = probabilita;
    }

    /**
     * Restituisce il contatore di scivolamenti del livello 3.
     *
     * @return il numero corrente di scivolamenti
     */
    public static int getScivolata() {
        return scivolata;
    }

    /**
     * Imposta il contatore di scivolamenti per il livello 3.
     *
     * @param scivolata il nuovo valore del contatore
     */
    public static void setScivolata(int scivolata) {
        VarGlobali.scivolata = scivolata;
    }

    /**
     * Variabile globale che indica se il gioco è finito.
     * Utilizzata per controllare lo stato del gioco in diverse parti dell'applicazione.
     */
    private static boolean Finito = true;

    /**
     * Restituisce lo stato del gioco (finito o in corso).
     *
     * @return true se il gioco è finito, false altrimenti
     */
    public static boolean getFinito() {
        return Finito;
    }

    /**
     * Imposta lo stato del gioco.
     *
     * @param finito true se il gioco è finito, false altrimenti
     */
    public static void setFinito(boolean finito) {
        Finito = finito;
    }

    /**
     * Mostra una sequenza di dialoghi con immagini
     *
     * @param gui      L'interfaccia grafica
     * @param testi    Array di testi da mostrare
     * @param immagini Array di percorsi immagini
     * @param onFinish Azione da eseguire al termine
     */
    public static void mostraDialoghi(GUI gui, String[] testi, String[] immagini, Runnable onFinish) {
        final int[] i = {0};

        gui.aggiornaImmagine2(immagini[i[0]]);
        gui.ScriviTextArea(testi[i[0]]);
        gui.abilitabottoni();

        GUI.PulisciListener.pulisciTuttiListener(gui);

        gui.buttAvanti.addActionListener(_ -> {
            gui.PulisciTextArea();
            i[0]++;
            if (i[0] < testi.length && i[0] < immagini.length) {
                gui.aggiornaImmagine2(immagini[i[0]]);
                gui.ScriviTextArea(testi[i[0]]);
            } else {
                //una volta finiti i dialoghi torno indietro al programma chiamante
                onFinish.run();
            }
        });
    }

    /**
     * Mostra una sequenza di dialoghi con possibilità di riprodurre audio
     *
     * @param gui      L'interfaccia grafica
     * @param testi    Array di testi
     * @param immagini Array di percorsi immagini
     * @param percorso audio che deve essere riprodotto
     * @param indice   indica a quale cella quell'audio deve essere riprodotto
     * @param onFinish Azione da eseguire al termine
     */
    public static void mostraDialoghiConAudio(GUI gui, String[] testi, String[] immagini, int indice, String percorso, Runnable onFinish) {
        final int[] i = {0};

        gui.aggiornaImmagine2(immagini[i[0]]);
        gui.ScriviTextArea(testi[i[0]]);
        gui.abilitabottoni();

        GUI.PulisciListener.pulisciTuttiListener(gui);

        gui.buttAvanti.addActionListener(_ -> {
            gui.PulisciTextArea();
            Suoni.fermaAudio();
            i[0]++;
            if (i[0] < testi.length && i[0] < immagini.length) {
                gui.aggiornaImmagine2(immagini[i[0]]);
                gui.ScriviTextArea(testi[i[0]]);

                //suono della scena
                if (i[0] == indice) {
                    Suoni.audio(percorso);
                    VarGlobali.avviaConRitardo(2000, () -> {
                       Suoni.fermaAudio();
                    });
                }
            } else {
                onFinish.run();
            }
        });
    }

    /**
     * Mostra una finestra di dialogo per chiedere al giocatore se vuole continuare o fuggire.
     *
     * @param testo Testo da mostrare
     * @return 'S' per continuare, 'N' per fuggire
     */
    public static char Decisioni(String testo) {
        InputBox siNo;
        siNo = new InputBox(null, true);
        siNo.ScriviBox(testo);
        siNo.setVisible(true);
        return siNo.getRisposta();
    }

}