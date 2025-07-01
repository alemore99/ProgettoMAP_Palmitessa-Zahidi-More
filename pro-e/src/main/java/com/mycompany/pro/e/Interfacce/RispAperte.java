package com.mycompany.pro.e.Interfacce;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * Finestra di dialogo per la gestione delle risposte aperte nel gioco.
 * Permette all'utente di inserire una risposta testuale libera.
 */

@SuppressWarnings("ALL")
public class RispAperte extends javax.swing.JDialog {

    private JTextArea jTextArea1;
    private JTextArea jTextArea2;
    /**
     * Costruttore della finestra di dialogo.
     *
     * @param parent La finestra padre (può essere null)
     * @param modal  Se true, la finestra sarà modale (blocca l'interazione con altre finestre)
     */
    public RispAperte(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);

        jTextArea1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    e.consume();
                    setVisible(false);
                    dispose();
                }
            }
        });
    }

    /**
     * Imposta il testo della domanda nell'area di visualizzazione.
     *
     * @param testo Il testo della domanda da mostrare
     */
    public void ScriviTextArea(String testo) {
        jTextArea2.append(testo);
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
    }

    /**
     * Recupera il testo inserito dall'utente.
     *
     * @return La risposta testuale inserita dall'utente
     */
    public String TestoAreaInput() {
        return jTextArea1.getText();
    }

    private void initComponents() {
        JPanel pannInt = new JPanel(new BorderLayout());
        pannInt.setBackground(Color.WHITE);

        JPanel pannOutput = new JPanel(new BorderLayout());
        pannOutput.setBackground(Color.WHITE);
        pannOutput.setBorder(new EmptyBorder(20, 20, 10, 20));
        pannOutput.setPreferredSize(new Dimension(500, 150));
        setResizable(false);


        jTextArea2 = new JTextArea(5, 25);
        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new Color(198, 198, 198));
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setFont(new Font("Georgia", Font.PLAIN, 16));
        JScrollPane jScrollPane2 = new JScrollPane(jTextArea2);

        pannOutput.add(jScrollPane2, BorderLayout.CENTER);

        JPanel pannInput = new JPanel(new BorderLayout());
        pannInput.setBackground(Color.WHITE);
        pannInput.setBorder(new EmptyBorder(20, 50, 30, 50)); // margine inferiore

        jTextArea1 = new JTextArea(4, 35);
        jTextArea1.setBackground(new Color(198, 198, 198));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFont(new Font("Georgia", Font.PLAIN, 16));

        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);

        pannInput.add(jScrollPane1, BorderLayout.CENTER);

        pannInt.add(pannOutput, BorderLayout.CENTER);
        pannInt.add(pannInput, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 100));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pannInt, BorderLayout.CENTER);

        pack();
    }

}
