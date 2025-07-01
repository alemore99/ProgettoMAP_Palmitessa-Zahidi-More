package com.mycompany.pro.e.Interfacce;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Finestra di dialogo per la scelta binaria (SI/NO) nel gioco.
 * Utilizzata principalmente per gestire la possibilità di fuga dal livello corrente
 * oppure per ricaricare una partita in corso.
 */
public class InputBox extends JDialog {
    private char rispostaSelezionata;
    private JTextArea Testo;
    private JButton SI;
    private JButton NO;
    private JPanel PannInt;

    /**
     * Costruttore della finestra di dialogo.
     *
     * @param owner La finestra padre a cui associare questa finestra
     * @param modal Se true, la finestra sarà modale (blocca l'interazione con altre finestre)
     */
    public InputBox(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        setupListeners();
        setContentPane(PannInt);
        setSize(600, 200);
        setLocationRelativeTo(owner);
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * Imposta il testo della domanda nella finestra di dialogo.
     *
     * @param testo Il messaggio da visualizzare all'utente
     */
    public void ScriviBox(String testo) {
        Testo.setText(testo);
    }

    /**
     * Restituisce la risposta selezionata dall'utente.
     *
     * @return 'S' per SI, 'N' per NO, o '\0' se nessuna risposta è stata selezionata
     */
    public char getRisposta() {
        return rispostaSelezionata;
    }

    /**
     * Configura i listener per i bottoni SI e NO.
     * Quando un bottone viene premuto, memorizza la risposta e chiude la finestra.
     */
    private void setupListeners() {
        ActionListener listener = e -> {
            JButton source = (JButton) e.getSource();
            String testo = source.getText();
            if (testo != null && !testo.isEmpty()) {
                rispostaSelezionata = testo.charAt(0);
            } else {
                rispostaSelezionata = '\0';
            }
            dispose();
        };

        SI.addActionListener(listener);
        NO.addActionListener(listener);
    }

    /**
     * Inizializza i componenti grafici della finestra.
     */
    private void initComponents() {
        PannInt = new JPanel(new BorderLayout());
        JPanel pannInput = new JPanel();
        JPanel pannOutput = new JPanel();

        Testo = new JTextArea(5, 1);
        Testo.setEditable(false);
        Testo.setBackground(new Color(227, 227, 227));
        Font fontPer = new Font("Georgia", Font.ITALIC, 16);
        Testo.setFont(fontPer);

        Testo.setLineWrap(true);
        Testo.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(Testo);
        pannInput.setLayout(new BorderLayout());
        pannInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        pannInput.add(scroll, BorderLayout.CENTER);

        SI = new JButton("SI");
        NO = new JButton("NO");

        pannOutput.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        pannOutput.add(NO);
        pannOutput.add(SI);

        PannInt.add(pannInput, BorderLayout.NORTH);
        PannInt.add(pannOutput, BorderLayout.CENTER);
    }
}
