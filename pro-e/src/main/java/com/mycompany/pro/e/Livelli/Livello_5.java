package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Database.Database;
import com.mycompany.pro.e.Database.StatoGioco;
import com.mycompany.pro.e.Interfacce.GUI;
import com.mycompany.pro.e.Interfacce.VarGlobali;

import java.awt.event.ActionListener;
import java.util.List;

/**
 * Livello 5 dell'avventura.
 * In questo livello si conclude l'esperienza del giocatore con una sequenza narrativa che celebra il suo successo.
 */
public class Livello_5 {

    /**
     * Metodo principale che avvia il quinto livello del gioco.
     * Gestisce una sequenza di immagini e testi che rappresentano il finale del gioco,
     * inclusi dialoghi con il Re e la consegna simbolica della bandiera della vittoria.
     *
     * @param gui         interfaccia grafica per l'interazione utente
     * @param indovinelli Indovinelli disponibili nel gioco (non utilizzati in questo livello)
     * @param inventario  oggetto contenente gli elementi raccolti dal giocatore durante l'avventura
     */
    public static void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
        VarGlobali.setLivello(5);
        gui.PulisciTextArea();
        gui.abilitabottoni();
        Suoni.fermaAudio();

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
                "Narratore: Arrivato in cima, gira i tuoi “orizzonti visivi”! Infondo al mar si intravede l’arrivo del Re Carlo V (originale) che ti sta portando la bandiera.",
                "Francesco: “Abba, Re Carlo! ",
                "Francesco: Che devo farmene io di una bandiera??",
                "Narratore: Serve per finire la tua avventura e far vedere al popolo monopolitano che dopo 500 anni finalmente c’è stato un eroe che ha finito decentemente tutto il gioco enfatico del castello! ",
                "Francesco: ah, era un gioco!",
                "Narratore: Eh bene sì, caro Francesco! Con te abbiamo sperimentato il gioco turistico del castello che sarà venduto dal Comune di Monopoli per incentivare le visite dei turisti nel nostro meraviglioso Castello!",
                "Francesco: Mi fa onore essere stato involontariamente scelto per questa iniziativa culturale!",
                " ",
                "Re Carlo: Eccomi qui! Sono felice di accoglierti nella mia dimora e contento che tu sia riuscito finalmente a visitarla… Proprio adesso la portineria mi ha detto che sei un ragazzo eccezionale e non vedevo l’ora di conoscerti. ",
                "Re Carlo: Sai bene che viaggiare in mare non è semplice e non è un “lavoro” che tutti sono in grado di operare e apprezzare. Tu hai voglia di fare un giro con me in barca???",
                "Francesco: NONO, oggi sono stanco! Un altro giorno magari ti verrò a trovare nuovamente e andiamo a farci questo splendido giro in barca in questa città.",
                "Re Carlo: OK! Però prima di andare via devi “elevare al cielo” la bandiera della vittoria. ",
                "Narratore: La bandiera che sventola al vento è simbolo di vittoria: cosicché tutti vissero felici e contenti (Almeno speriamo!)"
        };
        final int[] scena = {0};
        gui.aggiornaImmagine2(immagini[0]);
        gui.ScriviTextArea(testi[0]);

        ActionListener avanzaScena = _ -> {
            Suoni.fermaAudio();
            scena[0]++;
            if (scena[0] < testi.length) {
                gui.PulisciTextArea();
                gui.aggiornaImmagine2(immagini[scena[0]]);
                gui.ScriviTextArea(testi[scena[0]]);
                if (scena[0] == 8 || scena[0] == 0) {
                    Suoni.audio("/Audio/Livello3/salitaScale.wav");
                    VarGlobali.avviaConRitardo(1000, Suoni::fermaAudio);
                } else if (scena[0] == 11) {
                    inventario.aggiungi("Bandiera: Presa nel livello 5");
                }
            } else {
                inventario.svuota();
                finale(gui);
            }
        };
        gui.getButtAvanti().addActionListener(avanzaScena);
        VarGlobali.setFinito(true);
        Database.salva(new StatoGioco(inventario, VarGlobali.getLivello(), indovinelli, VarGlobali.getFinito()));
    }

    /**
     * Gestisce la scena finale del livello 5.
     * <p>
     * Disabilita i bottoni dell'interfaccia, pulisce il testo mostrato,
     * aggiorna l'immagine di sfondo con quella conclusiva e riproduce
     * l'audio finale del gioco.
     *
     * @param gui l'interfaccia grafica del gioco
     */
    private static void finale(GUI gui) {
        gui.disablebottoni();
        gui.PulisciTextArea();
        gui.aggiornaImmagine2("/foto/comuni/finalmente.png");
        Suoni.audio("/Audio/Livello5/Final.wav");
    }

}
