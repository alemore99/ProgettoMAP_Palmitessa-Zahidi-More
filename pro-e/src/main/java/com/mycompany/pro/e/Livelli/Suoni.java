package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.messaggiErrore;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.Objects;

    /*
        Funzione che inserisce i suoni, eseguendoli su un thread
    */


public class Suoni {
    private static Clip clip;

    /**
     * Istanzio i vari suoni su un thread specifico, altrimenti da errore se non trovo il file
     * Gli eseguo su un thread per poterli stoppare mentre si eseguono
     * @param suono
     */
    public static synchronized void audio(String suono) {
        fermaAudio();
        new Thread(() -> {
            final Object lock = new Object();
            try {
                //carico l'audio
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        Objects.requireNonNull(Suoni.class.getResource(suono))
                );
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        synchronized (lock) { lock.notify(); }
                    }
                });
                clip.start();
                synchronized (lock) { lock.wait(); }
                clip.close();
            } catch (Exception e) {
                messaggiErrore.mostraErrore(null, "Errore durante la riproduzione dell'audio: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Metodo per fermare l'audio del gioco
     */
    public static synchronized void fermaAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
            clip = null;
        }
    }

    public static Clip getClip() {
        return clip;
    }

    public static void setClip(Clip clip) {
        Suoni.clip = clip;
    }
}