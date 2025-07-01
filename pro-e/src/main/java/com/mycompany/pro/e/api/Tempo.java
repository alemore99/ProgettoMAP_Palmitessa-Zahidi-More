package com.mycompany.pro.e.api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

/**
 * Classe utile a gestire e visualizza il timer di gioco.
 * <p>
 * Mostra il tempo trascorso in secondi dall'avvio del gioco
 * e fornisce metodi per avviare e fermare il conteggio.
 * </p>
 */
@SuppressWarnings("ALL")
public class Tempo extends JPanel {

    private final JLabel timeLabel;
    private Timer swingTimer;
    private long startTime;

    /**
     * Costruttore della classe Tempo.
     * <p>
     * Inizializza il timer e l'interfaccia grafica al fine di visualizzare
     * il tempo trascorso.
     * </p>
     *
     * @param monopoli Parametro attualmente non utilizzato (mantenuto per compatibilità)
     */
    public Tempo(String monopoli) {
        timeLabel = new JLabel("Time: 0 sec", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 24));

        setLayout(new BorderLayout());
        add(timeLabel, BorderLayout.CENTER);
        setBackground(Color.WHITE);

        setSize(300, 150);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopGame();
            }
        });

        startGame();
    }

    /**
     * Metodo main per testing autonomo della classe, creata da NetBeans.
     *
     * @param args Argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tempo frame = new Tempo("Monopoli");
            frame.setVisible(true);
        });
    }

    /**
     * Avvia il conteggio del tempo.
     * <p>
     * Registra il tempo di inizio e avvia un timer che aggiorna
     * l'interfaccia ogni secondo.
     * </p>
     */
    private void startGame() {
        startTime = System.currentTimeMillis();

        Timer swingTimer = new Timer(1000, _ -> updateTimeLabel());
        swingTimer.start();

    }

    /**
     * Ferma il conteggio del tempo.
     * <p>
     * Interrompe l'aggiornamento periodico del timer.
     * </p>
     */
    private void stopGame() {
        if (swingTimer != null) {
            swingTimer.stop();
        }
    }

    /**
     * Aggiorna l'etichetta con il tempo trascorso.
     * <p>
     * Calcola il tempo in secondi dall'avvio e aggiorna la visualizzazione.
     * </p>
     */
    private void updateTimeLabel() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedMillis / 1000;
        timeLabel.setText("Time: " + elapsedSeconds + " sec");
    }
}

