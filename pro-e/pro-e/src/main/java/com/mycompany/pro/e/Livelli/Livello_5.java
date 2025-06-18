package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Livello 5 dell'avventura
 */
public class Livello_5 {

    /**
     *
     * @param gui
     * @param inventario
     */
    public static void main(GUI gui, Inventario inventario) {
        gui.PulisciTextArea();
        gui.abilitabottoni();
        Suoni.fermaAudio();

        //immagini e testi disposti in sequenza
        String[] immagini = {
                "/foto/Livello5/intro.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello5/arrivo_nave.png",
                "/foto/Livello3/terrazzo_arrivo.png",
                "/foto/Livello5/dialogo_re_turista.png",
                "/foto/Livello5/dialogo_re_turista.png",
                "/foto/Livello5/dialogo_re_turista.png",
                "/foto/Livello5/dialogo_re_turista.png",
                "/foto/Livello5/dialogo_re_turista.png",
                "/foto/Livello5/terrazzo.png"
        };

        String[] testi = {
                "",
                "Narratore: Arrivato in cima, gira i tuoi “orizzonti visivi”! Infondo al mar si intravede l’arrivo del Re Carlo V (sosia) che ti sta portando la bandiera.",
                "\n\nFrancesco: “Abba, Re Carlo!” ",
                "\nFrancesco: Che devo farmene io di una bandiera??",
                "\n\nNarratore: Serve per finire la tua avventura e far vedere al popolo monopolitano che dopo 500 anni finalmente c’è stato un eroe che ha finito decentemente tutto il gioco enfatico del castello! ",
                "\n\nFrancesco: ah, era un gioco!",
                "\n\nNarratore: Eh bene sì, caro Francesco! Con te abbiamo sperimentato il gioco turistico del castello che sarà venduto dal Comune di Monopoli per incentivare le visite dei turisti nel nostro meraviglioso Castello!",
                "\n\nFrancesco: Mi fa onore essere stato involontariamente scelto per questa iniziativa culturale!",
                " ",
                "\n\nRe Carlo: Eccomi qui! Sono felice di accoglierti nella mia dimora e contento che tu sia riuscito finalmente a visitarla… Proprio adesso la portineria mi ha detto che sei un ragazzo eccezionale e non vedevo l’ora di conoscerti. ",
                "\nRe Carlo: Sai bene che viaggiare in mare non è semplice e non è un “lavoro” che tutti sono in grado di operare e apprezzare. Tu hai voglia di fare un giro con me in barca???",
                "\n\nFrancesco: NONO, oggi sono stanco! Un altro giorno magari ti verrò a trovare nuovamente e andiamo a farci questo splendido giro in barca in questa città.",
                "\n\nRe Carlo: OK! Però prima di andare via devi “elevare al cielo” la bandiera della vittoria. ",
                "\n\nNarratore: La bandiera che sventola al vento è simbolo di vittoria: cosicché tutti vissero felici e contenti (Almeno speriamo!)"
        };
        final int[] scena = {0};
        Suoni.fermaAudio();
        gui.aggiornaImmagine2(immagini[0]);
        gui.ScriviTextArea(testi[0]);

        //inserisco l'action listener per switchare tra i vari testi
        ActionListener avanzaScena = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scena[0]++;
                if (scena[0] < testi.length) {
                    gui.aggiornaImmagine2(immagini[scena[0]]);
                    gui.ScriviTextArea(testi[scena[0]]);
                    if (scena[0] == 9 || scena[0] == 0) {
                        Suoni.audio("/Audio/Livello3/salitaScale.wav");
                        Timer timer = new Timer(1000, __ -> {
                            Suoni.fermaAudio();
                        });
                        timer.setRepeats(false);
                        timer.start();
                    } else if (scena[0] == 11) {
                        inventario.aggiungi("Bandiera: Presa nel livello 5");
                    }
                } else {
                    //rimuovi il listener quando la sequenza è finita
                    gui.buttAvanti.removeActionListener(this);
                    gui.disablebottoni();
                    gui.PulisciTextArea();
                    gui.aggiornaImmagine2("/foto/comuni/finalmente.png");
                    Suoni.audio("/Audio/Livello5/Final.wav");
                }
            }
        };
        gui.buttAvanti.addActionListener(avanzaScena);
    }


}
