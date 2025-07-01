package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfacce.GUI;
import com.mycompany.pro.e.Interfacce.RispAperte;
import com.mycompany.pro.e.Interfacce.VarGlobali;
import com.mycompany.pro.e.Interfacce.messaggiErrore;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* Classe che rappresenta il primo livello dell'avventura grafica.
*
* <p>In questo livello, il giocatore incontra la guardiana del castello e ha la possibilità
* di rispondere a degli indovinelli. Se risponde correttamente, ottiene uno sconto sul biglietto
* d'ingresso al castello.</p>
*/
public class Livello_1 {

 private static final String indovinelliAperti = "aperte.txt";
 /**
  * Lista degli indovinelli a risposta aperta
  */
 private List<Indovinello> indovinelliAperte;

 /**
  * Costruttore della classe Livello_1.
  *
  * <p>Carica gli indovinelli dai file di testo specificati. Se il caricamento fallisce,
  * inizializza le liste degli indovinelli come liste vuote e mostra un messaggio di errore.</p>
  */
 public Livello_1() {
     try {
         this.indovinelliAperte = Indovinello.caricaDaFile(indovinelliAperti);
     } catch (IOException e) {
         messaggiErrore.mostraErrore(null, "File degli indovinelli aperti non caricato correttamente");
         this.indovinelliAperte = new ArrayList<>();
     }
 }


 /**
  * Metodo principale che gestisce l'esecuzione del livello 1.
  *
  * <p>Mostra una sequenza di dialoghi e immagini, gestisce gli indovinelli e calcola
  * lo sconto sul biglietto in base alle risposte corrette.</p>
  *
  * @param gui         L'interfaccia grafica del gioco
  * @param inventario  L'inventario del giocatore
  * @param indovinelli Lista degli indovinelli disponibili
  */
 public void main(GUI gui, Inventario inventario, List<Indovinello> indovinelli) {
     VarGlobali.setLivello(1);

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
             "Narratore: Benvenuto in questa avventura, visiterai il nostro meraviglioso castello e ammirerai alcune delle bellezze uniche della città di Monopoli (BA), una delle meravigliose Città della Puglia.\n\n",
             "Narratore: Iniziamo la nostra meravigliosa avventura facendoci un giro nel centro storico, dove nel mezzo del nostro cammino... DAN DAN DAAAANNNNN \n\n",
             "Narratore: Iniziamo bene! Un branco di cani ti stanno seguendo! Nasconditi prima che sia troppo tardi!!!\n\n",
             "Narratore: Sei riuscito a scappare e nasconderti dietro il vaso!! Ti ritrovi alle spalle del meraviglioso Castello Carlo V. Un castello che ha tanto da dirci, costruito del 1552.\n\n",
             "Narratore: Ora ti trovi in Largo Castello! Insieme alle numerose pizzerie, godi il fascino della gastronomia pugliese! E poi, di fronte a te, si trova il maestoso Castello. Prova ad aprire la porta, visitiamolo.\n\n",
             "Narratore: ahia, è sera! La portineria ha già chiuso e spento le luci... Vai via allora, si dice che qui la sera qui esce il fantasma che ti viene a prendere.\n\n",
             "Narratore: Ritornando a casa, rimane tutto incuriosito dalla bellezza del castello e dall'arte che lo affascina; quindi prende la decisione di andarlo a visitare la mattina seguente.\n\n",
             "Narratore: Di buon mattino, con ottima sveglia, Francesco con il pensiero fisso a ieri sera esce di casa e si dirige verso il castello con una breve passeggiata\n\n",
             "Narratore: Arrivato al castello trova ancora chiuso, ma questione di istanti gli appare di soppiatto la portineria che gli chiede cosa stesse facendo lì a quell'ora. erano le 7.00 del mattino!\n\n",
             "Narratore: Lui le dice che voleva entrare nel castello, la signora Maria gli volle offrire una promo chance che serviva per far aprire il portone in largo anticipo rispetto al normale orario di apertura.\n\n",
             "Maria le dice che se risponde ai tre enigmi correttamente entrerà nel castello gratis, sennò avrebbe usufruito solamente di un piccolo forfè dai 12 euro del prezzo iniziale del biglietto.\n\n",
     };

     Suoni.audio("/Audio/tromba.wav");
     SwingUtilities.invokeLater(() -> VarGlobali.mostraDialoghi(gui, testi, immagini, () -> {
         Suoni.fermaAudio();
         gui.disablebottoni();

         AtomicInteger indice = new AtomicInteger(0);
         long corrette = indovinelliAperte.stream()
                 .map(ind -> {
                     String risposta = mostraDomanda(ind);
                     boolean esito = Parser.verificaRisposta(risposta, ind.getRispostaAperta());
                     if (esito) {
                         messaggiErrore.mostraConferma(null, "Risposta corretta, puoi continuare");
                         gui.ScriviTextArea("Risposta corretta! " +
                                 "\n La domanda era: " + ind.getDomanda() + " \n La tua risposta era: "
                                 + risposta + "\n \n");
                     } else {
                         messaggiErrore.mostraWarning(null, "\n \nRisposta sbagliata, passiamo al prossimo");
                         gui.ScriviTextArea("Risposta sbagliata! " +
                                 "\n La domanda era: " + ind.getDomanda() + " \n La tua risposta sbagliata era: "
                                 + risposta + "\n rispetto a quella giusta che sarebbe stata: " + ind.getRispostaAperta() + "\n \n");
                     }
                     indice.incrementAndGet();
                     return esito;
                 })
                 .filter(Boolean::booleanValue)
                 .count();
         gui.abilitabottoni();

         //template per i testi
         String[] immaginiFinali;
         String[] testiFinali;

         String[] immaginiBase = {
                 "/foto/Livello1/managgia.png",
                 "",
                 "/foto/Livello1/fra_paga.png",
                 "/foto/Livello1/prego.png"
         };

         String[] testiBase = {
                 "",
                 "",
                 "Francesco: Non fa niente, ti ringrazio e ti pago il restante rimanente del biglietto. \n\n",
                 ""
         };

         //determina il risultato finale in base alle risposte corrette, aggiungendo le immagini e i testi appropriati
         if (corrette == 3) {
             immaginiFinali = new String[]{
                     "/foto/Livello1/finalmente.png",
                     "/foto/Livello1/maria_gratis.png",
                     "/foto/Livello1/fran_giuste.png",
                     "/foto/Livello1/prego.png"
             };
             testiFinali = new String[]{
                     "",
                     "Maria: Sono contentissima! Hai passato questo livello alla perfezione e con astuzia, ti meriti proprio un bel premio per ciò, quindi ti concedo l'ingresso al castello gratuitamente.\n\n",
                     "Francesco: Grazie dell'opportunità, mi fa sentire fiero di me stesso. Non vedro l'ora di visitarlo allora\n\n",
                     ""
             };
         } else {
             immaginiFinali = immaginiBase.clone();
             testiFinali = testiBase.clone();

             switch ((int)corrette) {
                 case 2:
                     immaginiFinali[1] = "/foto/Livello1/maria_2.png";
                     testiFinali[1] = "Maria: Mannaggia! Non sei riuscito ad indovinare più di due indovinelli! Posso fare per te uno sconto del 50%, cioè di 6 euro solamente, ma non di più \n\n";
                     break;
                 case 1:
                     immaginiFinali[1] = "/foto/Livello1/maria_1.png";
                     testiFinali[1] = "Maria: Mannaggia! Non sei riuscito ad indovinare più di un indovinello! Posso fare per te uno sconto del 25%, cioè di 3 euro solamente, ma non di più \n\n";
                     break;
                 case 0:
                 default:
                     immaginiFinali[1] = "/foto/Livello1/maria_0.png";
                     testiFinali[1] = "Maria: Mannaggia! Non sei riuscito ad indovinare nessuna risposta! Purtroppo devo farti pagare il prezzo ordinario di 12 euro e puoi usarlo solo nella giornata di oggi.\n\n";
                     break;
             }
         }

         VarGlobali.mostraDialoghi(gui, testiFinali, immaginiFinali, () -> {
             gui.disablebottoni();
             gui.PulisciTextArea();
             Suoni.audio("/Audio/tromba.wav");
             gui.aggiornaImmagine2("/foto/Livello2/intro.png");
             Livello_2 livello2 = new Livello_2();
             VarGlobali.avviaConRitardo(2000, () -> livello2.main(gui, inventario, indovinelli));
         });
     }));
 }

 /**
  * Mostra una domanda all'utente e restituisce la risposta inserita.
  * <p>
  * Continua a chiedere una risposta finché non ne viene fornita una non vuota.
  *
  * @param ind L'oggetto Indovinello contenente la domanda da mostrare
  * @return La risposta inserita dall'utente
  */
 private String mostraDomanda(Indovinello ind) {
     String rispostaUtente;
     do {
         RispAperte risp = new RispAperte(null, true);
         risp.ScriviTextArea(ind.getDomanda());
         risp.setVisible(true);
         rispostaUtente = risp.TestoAreaInput();
         if (rispostaUtente == null || rispostaUtente.trim().isEmpty()) {
             messaggiErrore.mostraWarning(null, "Risposta vuota! Riprova.");
         }
     } while (rispostaUtente == null || rispostaUtente.trim().isEmpty());
     return rispostaUtente;
 }
}