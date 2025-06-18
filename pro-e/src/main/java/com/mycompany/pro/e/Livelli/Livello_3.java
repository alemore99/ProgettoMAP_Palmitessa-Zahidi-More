package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.GUI;
import com.mycompany.pro.e.Interfaccie.VarGlobali;

import javax.swing.*;
import java.util.List;

/**
 *
 * Livello 3 dell'avventura
 */
public class Livello_3 {

    /**
     *
     * @param gui
     * @param indovinelli
     */
    public static void main(GUI gui, List<Indovinello> indovinelli, Inventario inventario) {

        final int[] ripetizioni = {0};
        ripetizioni[0] = 0;
        GUI.PulisciListener.pulisciTuttiListener(gui);

        Suoni.fermaAudio();

        gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_1.png");
        Suoni.audio("/Audio/Livello3/salitaScale.wav");

        gui.ScriviTextArea("Narratore: Ora ci si avvia davanti alla scala che ci porterà sul tetto del castello. " +
                "Mentre salirai le scale si hanno diverse statistiche che ti portano " +
                "a capire in anticipo se potrai scivolare e/o cadere nella cantina o ad un livello più basso... \n \n");

        //inizio sequenza immagini scale
        gui.abilitabottoni();
        GUI.PulisciListener.pulisciTuttiListener(gui);
        gui.buttAvanti.addActionListener(e -> {
            ripetizioni[0]++;
            if (ripetizioni[0] == 0) {
                gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_1.png");
                caduta(gui, indovinelli, inventario);
            } else if (ripetizioni[0] == 1) {
                caduta(gui, indovinelli, inventario);
                gui.aggiornaImmagine2("/foto/Livello3/scale_terrazzo_2.png");
            } else if (ripetizioni[0] == 2) {
               gui.disablebottoni();
                gui.ScriviTextArea("Narratore: Sei uno dei pochi eroi che è riuscito a salire... \n \n");
                    GUI.PulisciListener.pulisciTuttiListener(gui);
                gui.aggiornaImmagine2("/foto/Livello4/intro.png");
                    Suoni.audio("/Audio/tromba.wav");
                    Livello_5.main(gui, inventario);
            }
        });
    }

    //metodo che implementa la caduta dalle scale
    private static void caduta(GUI gui, List<Indovinello> indovinelli, Inventario inventario) {
        //se caduto gia 2 volte, vai alla fine
        if (VarGlobali.scivolata == 2) return;


        //vedo se è fortunato il ciccio
        if (VarGlobali.generaRand(100) < VarGlobali.probabilita) {
            VarGlobali.scivolata++;
            VarGlobali.probabilita -= 30;
            Runnable azioneCaduta = () -> {
                Suoni.fermaAudio();
                Suoni.audio("/Audio/Livello3/caduta_scale.wav");
                GUI.PulisciListener.pulisciTuttiListener(gui);

                //prima caduta venendo dal livello A
                if (VarGlobali.ultimoLivello == 'a' && VarGlobali.scivolata == 1) {
                    gui.ScriviTextArea("Narratore: Sei caduto da queste maledette scale. " +
                            "Ora dovrai ritornare dal falegname per rispondere ad alcuni indovinelli...\n \n");
                    SwingUtilities.invokeLater(() -> {
                        gui.aggiornaImmagine2("/foto/Livello2/intro.png");
                    });
                    Suoni.audio("/Audio/tromba.wav");
                        Suoni.fermaAudio();
                        gui.PulisciTextArea();
                        VarGlobali.ultimoLivello = 'a';
                        new javax.swing.Timer(2000, ev -> {
                            Livello_2 livello2 = new Livello_2();
                            Livello_2.livello2_a lvl = livello2.new livello2_a(gui, indovinelli, inventario);
                            lvl.start();
                            ((javax.swing.Timer) ev.getSource()).stop();
                        }).start();
                }
                //prima caduta venendo dal livello B o seconda caduta dal livello A
                else if ((VarGlobali.ultimoLivello == 'b' && VarGlobali.scivolata == 1) ||
                        (VarGlobali.ultimoLivello == 'a' && VarGlobali.scivolata == 2)) {
                    gui.ScriviTextArea("Narratore: Sei caduto nella cantina del castello... \n \n");
                    SwingUtilities.invokeLater(() -> {
                        gui.aggiornaImmagine2("/foto/Livello4/intro.png");
                    });
                    Suoni.audio("/Audio/tromba.wav");
                        Suoni.fermaAudio();
                        gui.PulisciTextArea();
                        VarGlobali.ultimoLivello = 'b';
                        new javax.swing.Timer(2000, ev -> {
                            Livello_4.main(gui, indovinelli, inventario);
                            ((javax.swing.Timer) ev.getSource()).stop();
                        }).start();
                }
                //seconda caduta venendo dal livello B
                else if (VarGlobali.ultimoLivello == 'b' && VarGlobali.scivolata == 2) {
                    gui.ScriviTextArea("Narratore: Nuovamente sei caduto nella trappola... \n \n");
                    SwingUtilities.invokeLater(() -> {
                        gui.aggiornaImmagine2("/foto/Livello2/intro.png");
                    });
                    Suoni.audio("/Audio/tromba.wav");
                    GUI.PulisciListener.pulisciTuttiListener(gui);

                    new javax.swing.Timer(2000, ev -> {
                        Suoni.fermaAudio();
                        VarGlobali.ultimoLivello = 'a';
                        gui.PulisciTextArea();
                        Livello_2 livello2 = new Livello_2();
                        Livello_2.livello2_a lvl = livello2.new livello2_a(gui, indovinelli, inventario);
                        lvl.start();
                        ((javax.swing.Timer) ev.getSource()).stop();
                    }).start();
                }
            };

            if (SwingUtilities.isEventDispatchThread()) {
                azioneCaduta.run();
            } else {
                SwingUtilities.invokeLater(azioneCaduta);
            }
        }
    }


}