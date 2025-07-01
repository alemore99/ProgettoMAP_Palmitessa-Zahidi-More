package com.mycompany.pro.e.Interfacce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Finestra di dialogo per la gestione delle risposte a scelta multipla.
 * Mostra una domanda e quattro opzioni di risposta tramite bottoni.
 */
@SuppressWarnings("ALL")
public class RispChiuse extends javax.swing.JDialog {

    private String rispostaSelezionata = null;
    private javax.swing.JScrollPane BloccoTesto;
    private javax.swing.JButton BottA;
    private javax.swing.JButton BottB;
    private javax.swing.JButton BottC;
    private javax.swing.JButton BottD;
    private javax.swing.JPanel Bottoni;
    private javax.swing.JPanel PannInt;
    private javax.swing.JTextArea Testo;

    /**
     * Costruttore della finestra di dialogo.
     *
     * @param parent La finestra padre (può essere null)
     * @param modal  Se true, la finestra sarà modale
     */
    public RispChiuse(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupListeners();
        setLocationRelativeTo(parent);
    }

    /**
     * Metodo main per testing autonomo, creata da NetBeans.
     *
     * @param args Argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        RispChiuse finestra = new RispChiuse(null, true);
        finestra.setVisible(true);

        finestra.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                String risposta = finestra.getRisposta();
                System.out.println("Hai scelto: " + risposta);
            }
        });
    }

    /**
     * Imposta il testo della domanda nell'area di testo.
     *
     * @param testo Il testo della domanda da visualizzare
     */
    public void TextAreaRispChiu(String testo) {
        Testo.append(testo);
    }

    /**
     * Imposta il testo per il bottone A.
     *
     * @param testo Il testo da visualizzare
     */
    public void setBottoneAText(String testo) {
        BottA.setText(testo);
    }

    /**
     * Imposta il testo per il bottone B.
     *
     * @param testo Il testo da visualizzare
     */
    public void setBottoneBText(String testo) {
        BottB.setText(testo);
    }

    /**
     * Imposta il testo per il bottone C.
     *
     * @param testo Il testo da visualizzare
     */
    public void setBottoneCText(String testo) {
        BottC.setText(testo);
    }

    /**
     * Imposta il testo per il bottone D.
     *
     * @param testo Il testo da visualizzare
     */
    public void setBottoneDText(String testo) {
        BottD.setText(testo);
    }

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
     * Restituisce la risposta selezionata.
     *
     * @return La risposta selezionata o null se nessuna scelta è stata fatta
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

    public JTextArea getTesto() {
        return Testo;
    }

    public void setTesto(JTextArea testo) {
        Testo = testo;
    }
}
