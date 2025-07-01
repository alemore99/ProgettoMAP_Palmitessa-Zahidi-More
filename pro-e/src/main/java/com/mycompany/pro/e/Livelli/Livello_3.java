package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfacce.GUI;
import com.mycompany.pro.e.Interfacce.VarGlobali;

import javax.swing.*;
import java.util.List;

public class Livello_3 {

    /**
     * Metodo principale che avvia il livello 3 del gioco.
     * Esso si occupa di gestisce la salita delle scale
     * con un eventuale possibilità di caduta.
     *
     * @param gui         L'interfaccia grafica del gioco
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli disponibili
     */
    public static void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {

        VarGlobali.setLivello(3);
        Suoni.fermaAudio();
        gui.aggiornaImmagine2("/foto/Livello3/intro.png");
        VarGlobali.avviaConRitardo(1000, () -> {
            gui.ScriviTextArea("Narratore: Ora ci si avvia davanti alla scala che ci porterà sul tetto del castello. " +
                    "Mentre salirai le scale può andare tutto liscio, oppure no, chi lo sa..... " +
                    "Nel caso peggiore scivolerai in uno dei tanti livelli di questo castello... \n \n");

            gui.abilitabottoni();

            final int[] step = {0};
            gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_1.png");
            Suoni.audio("/Audio/Livello3/salitaScale.wav");

            GUI.PulisciListener.pulisciTuttiListener(gui);
            gui.getButtAvanti().addActionListener(_ -> {
                step[0]++;
                switch (step[0]) {
                    case 1:
                        gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_1.png");
                        caduta(gui, inventario, indovinelli);
                        break;
                    case 2:
                        gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_2.png");
                        caduta(gui, inventario, indovinelli);
                        break;
                    case 3:
                        gui.disablebottoni();
                        gui.ScriviTextArea("Narratore: Sei uno dei pochi eroi che è riuscito a salire... \n \n");
                        GUI.PulisciListener.pulisciTuttiListener(gui);
                        gui.aggiornaImmagine2("/foto/Livello4/intro.png");
                        Suoni.audio("/Audio/tromba.wav");
                        Livello_5.main(gui, inventario, indovinelli);
                        break;
                    default:
                        gui.getButtAvanti().removeActionListener(gui.getButtAvanti().getActionListeners()[0]);
                }
            });
        });
    }

    /**
     * Gestisce la logica di caduta dalle scale con
     * le relative probabilità.
     *
     * @param gui         L'interfaccia grafica
     * @param inventario  L'inventario del giocatore
     * @param indovinelli Lista degli indovinelli
     */
    private static void caduta(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        //limito le cadute a massimo 2 volte
        if (VarGlobali.getScivolata() >= 2) return;

        if (VarGlobali.generaRand(100) < VarGlobali.getProbabilita()) {
            VarGlobali.setScivolata(+1);
            VarGlobali.setProbabilita(-20);

            SwingUtilities.invokeLater(() -> {
                Suoni.fermaAudio();
                Suoni.audio("/Audio/Livello3/caduta_scale.wav");
                GUI.PulisciListener.pulisciTuttiListener(gui);

                switch (cadutaScenario()) {
                    case "primaA":
                        gestisciPrimaCadutaA(gui, inventario, indovinelli);
                        break;
                    case "primaBSecondaA":
                        gestisciCadutaCantina(gui, inventario, indovinelli);
                        break;
                    case "secondaB":
                        gestisciSecondaCadutaB(gui, inventario, indovinelli);
                        break;
                }
            });
        }
    }

    /**
     * Determina lo scenario di caduta in base al livello precedente e al numero di cadute.
     *
     * @return una stringa che rappresenta lo scenario di caduta da gestire
     */
    private static String cadutaScenario() {
        if (VarGlobali.getUltimoLivello() == 'a' && VarGlobali.getScivolata() == 1) {
            return "primaA";
        } else if ((VarGlobali.getUltimoLivello() == 'b' && VarGlobali.getScivolata() == 1)
                || (VarGlobali.getUltimoLivello() == 'a' && VarGlobali.getScivolata() == 2)) {
            return "primaBSecondaA";
        } else if (VarGlobali.getUltimoLivello() == 'b' && VarGlobali.getScivolata() == 2) {
            return "secondaB";
        }
        return "";
    }

    /**
     * Gestisce la prima caduta quando il livello precedente era 'a'.
     *
     * @param gui         l'interfaccia grafica
     * @param inventario  inventario del giocatore
     * @param indovinelli lista di indovinelli
     */
    private static void gestisciPrimaCadutaA(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.disablebottoni();

        if (indovinelli == null || indovinelli.isEmpty()) {
            gui.ScriviTextArea("Errore: Lista indovinelli vuota. Il gioco non può continuare.");
            return;
        }

        gui.ScriviTextArea("Narratore: Sei caduto da queste maledette scale. Ora dovrai ritornare dal falegname per rispondere ad alcuni indovinelli...\n \n");
        gui.aggiornaImmagine2("/foto/Livello2/intro.png");
        Suoni.audio("/Audio/Livello3/caduta_scale.wav");

        VarGlobali.avviaConRitardo(2000, () -> {
            gui.PulisciTextArea();
            VarGlobali.setUltimoLivello('a');
            VarGlobali.setLivello(2);

            VarGlobali.avviaConRitardo(500, () -> {
                avviaLivello2a(gui, inventario, indovinelli);
            });
        });
    }


    /**
     * Gestisce la caduta nella cantina del castello.
     *
     * @param gui         l'interfaccia grafica
     * @param inventario  inventario del giocatore
     * @param indovinelli lista di indovinelli
     */
    private static void gestisciCadutaCantina(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.disablebottoni();

        if (indovinelli == null || indovinelli.isEmpty()) {
            gui.ScriviTextArea("Errore: Lista indovinelli vuota. Il gioco non può continuare.");
            return;
        }

        gui.ScriviTextArea("Narratore: Sei caduto nella cantina del castello... \n \n");
        Suoni.audio("/Audio/Livello3/caduta_scale.wav");

        VarGlobali.avviaConRitardo(2000, () -> {
            gui.PulisciTextArea();

            VarGlobali.avviaConRitardo(500, () -> {
                VarGlobali.setUltimoLivello('b');
                VarGlobali.setLivello(4); // Assicuriamo che il livello sia correttamente impostato
                Livello_4.main(gui, inventario, indovinelli);
            });
        });
    }


    /**
     * Gestisce la seconda caduta nel caso in cui il giocatore provenga dal livello 'b'.
     *
     * @param gui         l'interfaccia grafica
     * @param inventario  inventario del giocatore
     * @param indovinelli lista di indovinelli
     */
    private static void gestisciSecondaCadutaB(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        gui.disablebottoni();

        gui.ScriviTextArea("Narratore: Nuovamente sei caduto nella trappola... \n \n");
        gui.aggiornaImmagine2("/foto/Livello2/intro.png");
        Suoni.audio("/Audio/Livello3/caduta_scale.wav");
        GUI.PulisciListener.pulisciTuttiListener(gui);

        VarGlobali.avviaConRitardo(1500, () -> {
            gui.PulisciTextArea();
            VarGlobali.setUltimoLivello('a');

            VarGlobali.avviaConRitardo(500, () -> {
                avviaLivello2a(gui, inventario, indovinelli);
            });
        });
    }


    /**
     * Avvia il thread "Livello2a", nel caso il giocatore cada e debba tornare indietro.
     *
     * @param gui         l'interfaccia grafica
     * @param indovinelli lista di indovinelli
     * @param inventario  inventario del giocatore
     */
private static void avviaLivello2a(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
    VarGlobali.avviaConRitardo(1500, () -> {
        Suoni.fermaAudio();
        Livello_2 livello2 = new Livello_2();
        Livello_2.livello2_a lvl = livello2.new livello2_a(gui, inventario, indovinelli);
        lvl.start();
    });
}
}
