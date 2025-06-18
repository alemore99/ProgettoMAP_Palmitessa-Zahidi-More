package com.mycompany.pro.e.Livelli;


import com.mycompany.pro.e.Interfaccie.GUI;
import com.mycompany.pro.e.Interfaccie.RispAperte;
import com.mycompany.pro.e.Interfaccie.messaggiErrore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Livello 1 dell'avventura
 * Incontro con la guardina del castello, dove se si risponde correttamente,
 * si riceve uno sconto sul biglietto
 */
public class Livello_1 {

    private List<Indovinello> indovinelli;
    private List<Indovinello> indovinelliExtra;

    public Livello_1() {
        try {
            this.indovinelli = Indovinello.caricaDaFile("indovinelli.txt");
        } catch (IOException e) {
            messaggiErrore.mostraErrore(null, "file non trovato, prova a ricaricarlo");
            this.indovinelli = new ArrayList<>();
        }
        try {
            this.indovinelliExtra = Indovinello.caricaDaFile("aperte.txt");
        } catch (IOException e) {
            messaggiErrore.mostraErrore(null, "2° file non trovato, prova a ricaricarlo");
            this.indovinelliExtra = new ArrayList<>();
        }
    }
    /**
     *
     * @param inventario
     * @param gui
     */
    public void main(GUI gui, Inventario inventario) {

       Suoni.fermaAudio();
       final int[] corretto = {0};

        // Array delle immagini e dei testi
       String[] immagini = {
               "/foto/Livello1/1.png",
               "/foto/Livello1/2.png",
               "/foto/Livello1/3.gif",
               "/foto/Livello1/4.png",
               "/foto/Livello1/5.png",
               "/foto/Livello1/6.png",
               "/foto/Livello1/7.png",
               "/foto/Livello1/8.png",
               "/foto/Livello1/9.png",
               "/foto/Livello1/10.png",

       };

       String[] testi = {
               "Narratore: Benvenuto nella nostra avventura grafica. Ti faremo visitare il nostro meraviglioso castello e ammirare alcune delle bellezze uniche della città di Monopoli (BA), una delle meravigliose Città della Puglia.\n\n",
               "Narratore: Iniziamo la nostra meravigliosa avventura facendoci un giro nel centro storico, dove nel mezzo del nostro cammino... DAN DAN DAAAANNNNN \n\n",
               "Narratore: OH NO, iniziamo bene! Un branco di cani ti stanno seguendo! Nasconditi prima che sia troppo tardi!!!\n\n",
               "Narratore: Ora sei arrivato qui, dietro un vaso!! Sei alle spalle del meraviglioso Castello Carlo V di cui ti parlavo. Un castello che ha tanto da dirci, costruito del 1552. Clicca qui per andare nella piazzetta antistante il Castello.\n\n",
               "Narratore: Ora ti trovi in Largo Castello! Insieme alle numerose pizzerie, godi il fascino della gastronomia pugliese! E poi c’è questo, il maestoso Castello. Prova ad aprire la porta, visitiamolo.\n\n",
               "Narratore: ahia, è sera! La portineria ha già chiuso e spento le luci… Vai via allora, la sera qui esce il fantasma, così dicono le varie profezie.\n\n",
               "Narratore: Ritornando a casa, si vede tutto incuriosito dalla bellezza del castello e dall’arte che lo affascina; quindi prende la decisione di andarlo a visitare la mattina seguente.\n\n",
               "Narratore: Di buon mattino, con ottima sveglia, Francesco con il pensiero fisso a ieri sera esce di casa e si dirige verso il castello con una breve passeggiata\n\n",
               "Narratore: Arrivato al castello trova ancora chiuso, ma questione di istanti gli appare di soppiatto la portineria che gli chiede cosa stesse facendo lì a quell’ora. erano le 7.00 del mattino!\n\n",
               "Narratore: Lui appena le dice che voleva entrare nel castello, la signora Maria gli volle offrire una promo chance cui serviva per far aprire lei il portone in largo anticipo rispetto al normale orario di apertura.\n\n",
               "Maria le dice che se risponde ai tre enigmi correttamente entrerà nel castello gratis, sennò avrebbe usufruito solamente di un piccolo forfè dai 12 euro del prezzo iniziale del biglietto.\n\n"
       };

        //array di una sola cella, usato come contatore
        //poiche non è possibile modificare le variabili nell'actionlistener
        final int[] scena = {0};

       gui.aggiornaImmagine2(immagini[scena[0]]);
       gui.ScriviTextArea(testi[scena[0]]);
       Suoni.audio("/Audio/tromba.wav");

       gui.buttAvanti.addActionListener(_ -> {
           Suoni.fermaAudio(); //nel caso passa alla scena successiva con ancora l'audio dei cani
           gui.PulisciTextArea();
           scena[0]++;
           if (scena[0] < immagini.length) {
               gui.aggiornaImmagine2(immagini[scena[0]]);
               gui.ScriviTextArea(testi[scena[0]]);
               if (scena[0] == 2) {
                   Suoni.audio("/Audio/Livello1/cani.wav");
               };
           } else {
               gui.disablebottoni();

               //inizio parte enigmi
               for (Indovinello ind : indovinelliExtra) {
                   String rispostaUtente;

                   //caso la stringa sia vuota, richiedo all'infinito
                   do {
                       RispAperte risp = new RispAperte(null, true);
                       risp.ScriviTextArea(ind.domanda);
                       risp.setVisible(true);
                       rispostaUtente = risp.TestoAreaInput();
                       if (rispostaUtente == null || rispostaUtente.trim().isEmpty()) {
                           messaggiErrore.mostraWarning(null, "Risposta vuota! Riprova.");
                       }
                   } while (rispostaUtente == null || rispostaUtente.trim().isEmpty());

                   if (Parser.verificaRisposta(rispostaUtente, ind.rispostaAperta)) {
                       corretto[0]++;
                       messaggiErrore.mostraConferma(null, "Risposta corretta, puoi continuare");
                       gui.ScriviTextArea("Risposta corretta! " +
                               "\n La domanda era: " + ind.domanda + " \n La tua risposta era: "
                               + rispostaUtente + "\n \n");
                   } else {
                       messaggiErrore.mostraWarning(null, "\n \nRisposta sbagliata, passiamo al prossimo");
                       gui.ScriviTextArea("Risposta sbagliata! " +
                               "\n La domanda era: " + ind.domanda + " \n La tua risposta sbagliata era: "
                               + rispostaUtente + "\n rispetto a quella giusta che sarebbe stata: " + ind.rispostaAperta + "\n \n");
                   }
               }
               gui.abilitabottoni();


               String[] immagine;
               String[] testo;

               if (corretto[0] == 3) {

                   //array per le immagini e testi
                   immagine = new String[]  {
                           "/foto/Livello1/finalmente.png",
                           "/foto/Livello1/maria_gratis.png",
                           "/foto/Livello1/fran_giuste.png",
                           "/foto/Livello1/prego.png"
                   };
                   testo = new String[] {
                           "",
                           "Maria: Sono contentissima! " +
                                   "Hai passato questo livello alla perfezione e con astuzia, aggiudicandoti l’ingresso al castello gratuitamente.\n\n",
                           "Francesco: Sono molto fiero di me stesso e ti ringrazio dell’opportunità. Non vedo l’ora di visitarlo \n\n",
                           ""
                   };
               } else if (corretto[0] == 2) {

                   immagine = new String[]  {
                           "/foto/Livello1/managgia.png",
                           "/foto/Livello1/maria_2.png",
                           "/foto/Livello1/fra_paga.png",
                           "/foto/Livello1/prego.png"
                   };
                   testo = new String[]  {
                            "",
                            "Maria: Mannaggia! Non sei riuscito ad indovinare più di due indovinelli! " +
                                    "Posso fare per te uno sconto del 50%, cioè di 6 euro solamente… \n\n",
                            "Francesco: Non fa niente, ti ringrazio e ti pago il restante rimanente del biglietto. \n\n",
                            ""
                   };

               } else if (corretto[0] == 1) {

                   immagine = new String[]  {
                           "/foto/Livello1/managgia.png",
                           "/foto/Livello1/maria_1.png",
                           "/foto/Livello1/fra_paga.png",
                           "/foto/Livello1/prego.png"
                   };
                   testo = new String[] {
                           "",
                           "Maria: Mannaggia! Non sei riuscito ad indovinare più di un indovinello! " +
                                   "Posso fare per te uno sconto del 25%, cioè di 3 euro solamente… \n\n",
                           "Francesco: Non fa niente, ti ringrazio e ti pago il restante rimanente del biglietto. \n\n",
                           ""
                   };
               } else {

                   immagine = new String[]{
                           "/foto/Livello1/managgia.png",
                           "/foto/Livello1/maria_0.png",
                           "/foto/Livello1/fra_paga.png",
                           "/foto/Livello1/prego.png"
                   };
                   testo = new String[]{
                           "",
                           "Maria: Mannaggia! Non sei riuscito ad indovinare nessuna risposta! " +
                                   "Purtroppo devo farti pagare il prezzo ordinario di 12 euro e per di più " +
                                   "puoi usarlo solo nella giornata di oggi per punizione nel non aver indovinato le risposte.\n\n",
                           "Francesco: Non fa niente, ti ringrazio e ti pago il restante rimanente del biglietto. \n\n",
                           ""
                   };
               }

               GUI.PulisciListener.pulisciTuttiListener(gui);

               int[] i = {0};
               gui.aggiornaImmagine2(immagine[i[0]]);
               gui.ScriviTextArea(testo[i[0]]);

               //muovo tutto in automatico
               gui.buttAvanti.addActionListener(_ -> {
                   gui.PulisciTextArea();
                   gui.setImageClickListener(null);
                   gui.abilitabottoni();
                   i[0]++;
                   if (i[0] < immagine.length) {
                       gui.aggiornaImmagine2(immagine[i[0]]);
                       gui.ScriviTextArea(testo[i[0]]);
                   } else {
                       gui.disablebottoni();
                       gui.PulisciTextArea();
                       gui.aggiornaImmagine2("/foto/Livello2/intro.png");
                       Suoni.audio("/Audio/tromba.wav");
                       Livello_2 livello2 = new Livello_2();
                       javax.swing.Timer timer = new javax.swing.Timer(2000, _ -> {
                           livello2.main(gui, inventario, indovinelli);
                       });
                       timer.setRepeats(false);
                       timer.start();
                   }
               });
           }
       });
   }

    public List<Indovinello> getIndovinelli() {
        return indovinelli;
    }

    public void setIndovinelli(List<Indovinello> indovinelli) {
        this.indovinelli = indovinelli;
    }

    public List<Indovinello> getIndovinelliExtra() {
        return indovinelliExtra;
    }

    public void setIndovinelliExtra(List<Indovinello> indovinelliExtra) {
        this.indovinelliExtra = indovinelliExtra;
    }
}
