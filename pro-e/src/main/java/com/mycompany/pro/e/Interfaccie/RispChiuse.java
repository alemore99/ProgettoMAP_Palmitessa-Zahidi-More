package com.mycompany.pro.e.Interfaccie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Gestisce le rispsote chiuese del livello
 *
 */

public class RispChiuse extends javax.swing.JDialog {

    /**
     *
     * @param testo
     */
    public void TextAreaRispChiu(String testo) {
        Testo.append(testo);
    }

    /**
     *
     * @param parent
     * @param modal
     */
    public RispChiuse(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupListeners();
        setLocationRelativeTo(parent);
    }


    //metodi per settare il testo nei bottoni
    /**
     *
     * @param testo
     */
    public void setBottoneAText(String testo) {
        BottA.setText(testo);
    }

    /**
     *
     * @param testo
     */
    public void setBottoneBText(String testo) {
        BottB.setText(testo);
    }

    /**
     *
     * @param testo
     */
    public void setBottoneCText(String testo) {
        BottC.setText(testo);
    }

    /**
     *
     * @param testo
     */
    public void setBottoneDText(String testo) {
        BottD.setText(testo);
    }

    private String rispostaSelezionata = null;

    //creo il listener per ridarmi il bottone scelto dall'utente
    private void setupListeners() {
        ActionListener listener = e -> {
            JButton source = (JButton) e.getSource();
            rispostaSelezionata = source.getText();
            dispose();
        };

        BottA.addActionListener(listener);
        BottB.addActionListener(listener);
        BottC.addActionListener(listener);
        BottD.addActionListener(listener);
    }

    /**
     *
     * @return rispostaSelezionata
     */
    public String getRisposta() {
        return rispostaSelezionata;
    }

    private void initComponents() {
        PannInt = new javax.swing.JPanel();
        BloccoTesto = new javax.swing.JScrollPane();
        Testo = new javax.swing.JTextArea();
        Bottoni = new javax.swing.JPanel();
        BottA = new javax.swing.JButton();
        BottB = new javax.swing.JButton();
        BottC = new javax.swing.JButton();
        BottD = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMinimumSize(new java.awt.Dimension(600, 400));
        setResizable(false);

        getContentPane().setLayout(new BorderLayout());

        PannInt.setLayout(new BorderLayout(20, 20));
        PannInt.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        Testo.setEditable(false);
        Testo.setColumns(20);
        Testo.setRows(5);
        Testo.setForeground(Color.BLACK);
        Testo.setFont(new Font("Georgia", Font.ITALIC, 16));
        Testo.setCaretColor(new Color(0, 0, 0));
        Testo.setBackground(new Color(220, 220, 220));
        Testo.setLineWrap(true);
        Testo.setWrapStyleWord(true);
        BloccoTesto.setViewportView(Testo);
        PannInt.add(BloccoTesto, BorderLayout.NORTH);

        Bottoni.setLayout(new GridLayout(2, 2, 20, 20));

        JButton[] bottoni = {BottA, BottB, BottC, BottD};
        for (JButton bottone : bottoni) {
            bottone.setPreferredSize(new Dimension(200, 60));
            bottone.setFont(new Font("Arial", Font.BOLD, 16));
            bottone.setFocusPainted(false);
            bottone.setMargin(new Insets(10, 20, 10, 20)); //padding interno
            Bottoni.add(bottone);
        }

        BottA.setText("Risposta A");
        BottB.setText("Risposta B");
        BottC.setText("Risposta C");
        BottD.setText("Risposta D");

        PannInt.add(Bottoni, BorderLayout.CENTER);
        getContentPane().add(PannInt, BorderLayout.CENTER);

        pack(); //addatta in modo automatico gli oggetti
    }

    /*
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        RispChiuse finestra = new RispChiuse(null, true);
        finestra.setVisible(true);

        //aspetta la chiusura della finestra, quindi prende la risposta
        finestra.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                String risposta = finestra.getRisposta();
                System.out.println("Hai scelto: " + risposta);
            }
        });
    }

    private javax.swing.JScrollPane BloccoTesto;
    private javax.swing.JButton BottA;
    private javax.swing.JButton BottB;
    private javax.swing.JButton BottC;
    private javax.swing.JButton BottD;
    private javax.swing.JPanel Bottoni;
    private javax.swing.JPanel PannInt;
    private javax.swing.JTextArea Testo;

    public String getRispostaSelezionata() {
        return rispostaSelezionata;
    }

    public void setRispostaSelezionata(String rispostaSelezionata) {
        this.rispostaSelezionata = rispostaSelezionata;
    }

    public JScrollPane getBloccoTesto() {
        return BloccoTesto;
    }

    public void setBloccoTesto(JScrollPane bloccoTesto) {
        BloccoTesto = bloccoTesto;
    }

    public JButton getBottA() {
        return BottA;
    }

    public void setBottA(JButton bottA) {
        BottA = bottA;
    }

    public JButton getBottB() {
        return BottB;
    }

    public void setBottB(JButton bottB) {
        BottB = bottB;
    }

    public JButton getBottC() {
        return BottC;
    }

    public void setBottC(JButton bottC) {
        BottC = bottC;
    }

    public JButton getBottD() {
        return BottD;
    }

    public void setBottD(JButton bottD) {
        BottD = bottD;
    }

    public JPanel getBottoni() {
        return Bottoni;
    }

    public void setBottoni(JPanel bottoni) {
        Bottoni = bottoni;
    }

    public JPanel getPannInt() {
        return PannInt;
    }

    public void setPannInt(JPanel pannInt) {
        PannInt = pannInt;
    }

    public JTextArea getTesto() {
        return Testo;
    }

    public void setTesto(JTextArea testo) {
        Testo = testo;
    }
}
