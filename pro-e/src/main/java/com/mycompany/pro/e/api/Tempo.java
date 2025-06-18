/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pro.e.api;

/**
 *
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

/**
 *
 * @author aless
 */
public class Tempo extends JPanel {

    private JLabel timeLabel;
    private Timer swingTimer;
    private long startTime;

    /**
     *
     * @param monopoli
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

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    //avvio il tempo appena avvio il gioco
    private void startGame() {
        startTime = System.currentTimeMillis();

        Timer swingTimer = new Timer(1000, e -> updateTimeLabel());
        swingTimer.start();

    }

    //fermo appena finisce
    private void stopGame() {
        if (swingTimer != null) {
            swingTimer.stop();
        }
    }

    //mostro il tempo
    private void updateTimeLabel() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedMillis / 1000;
        timeLabel.setText("Time: " + elapsedSeconds + " sec");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tempo frame = new Tempo("Monopoli");
            frame.setVisible(true);
        });
    }

}

