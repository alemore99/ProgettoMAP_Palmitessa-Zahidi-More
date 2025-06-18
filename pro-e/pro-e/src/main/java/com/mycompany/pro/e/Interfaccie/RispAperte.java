package com.mycompany.pro.e.Interfaccie;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * Gestisce le domande aperte del gioco
 */
public class RispAperte extends javax.swing.JDialog {

    /**
     *
     * @param parent
     * @param modal
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
     *
     * @param testo
     */
    public void ScriviTextArea(String testo) {
        jTextArea2.append(testo);
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
    }

    /**
     *
     * @return
     */
    public String TestoAreaInput() {
        return jTextArea1.getText();
    }

    private void initComponents() {
        // Inizializza componenti
        PannInt = new JPanel(new BorderLayout());
        PannInt.setBackground(Color.WHITE);

        PannOutput = new JPanel(new BorderLayout());
        PannOutput.setBackground(Color.WHITE);
        PannOutput.setBorder(new EmptyBorder(20, 20, 10, 20));
        PannOutput.setPreferredSize(new Dimension(500, 150));
        setResizable(false);


        jTextArea2 = new JTextArea(5, 25);
        jTextArea2.setEditable(false);
        jTextArea2.setBackground(new Color(198, 198, 198));
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setFont(new Font("Georgia", Font.PLAIN, 16));
        jScrollPane2 = new JScrollPane(jTextArea2);

        PannOutput.add(jScrollPane2, BorderLayout.CENTER);

        PannInput = new JPanel(new BorderLayout());
        PannInput.setBackground(Color.WHITE);
        PannInput.setBorder(new EmptyBorder(20, 50, 30, 50)); // margine inferiore

        jTextArea1 = new JTextArea(4, 35);
        jTextArea1.setBackground(new Color(198, 198, 198));
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFont(new Font("Georgia", Font.PLAIN, 16));

        jScrollPane1 = new JScrollPane(jTextArea1);

        PannInput.add(jScrollPane1, BorderLayout.CENTER);

        //aggiunta pannelli principali
        PannInt.add(PannOutput, BorderLayout.CENTER);
        PannInt.add(PannInput, BorderLayout.SOUTH);

        //imposta il contenuto
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 100));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(PannInt, BorderLayout.CENTER);

        pack();
    }

    private JPanel PannInput;
    private JPanel PannInt;
    private JPanel PannOutput;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea jTextArea1;
    private JTextArea jTextArea2;

    public JPanel getPannInput() {
        return PannInput;
    }

    public void setPannInput(JPanel pannInput) {
        PannInput = pannInput;
    }

    public JPanel getPannInt() {
        return PannInt;
    }

    public void setPannInt(JPanel pannInt) {
        PannInt = pannInt;
    }

    public JPanel getPannOutput() {
        return PannOutput;
    }

    public void setPannOutput(JPanel pannOutput) {
        PannOutput = pannOutput;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public void setjScrollPane1(JScrollPane jScrollPane1) {
        this.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    public JTextArea getjTextArea2() {
        return jTextArea2;
    }

    public void setjTextArea2(JTextArea jTextArea2) {
        this.jTextArea2 = jTextArea2;
    }
}
