package com.mycompany.pro.e.Interfaccie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * Mostra la casella della possibilita di fuggire
 */
public class InputBox extends JDialog {
    private char rispostaSelezionata;
    private JTextArea Testo;
    private JButton SI;
    private JButton NO;

    private JPanel PannInt;
    private JPanel PannInput;
    private JPanel PannOutput;

    /**
     *
     * @param owner
     * @param modal
     */
    public InputBox(Frame owner, boolean modal) {
        super(owner, modal);
        initComponents();
        setupListeners();
        setContentPane(PannInt);
        setSize(500, 200);
        setLocationRelativeTo(owner);
        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param testo
     */
    public void ScriviBox(String testo) {
        Testo.setText(testo);
    }

    /**
     *
     * @return
     */
    public char getRisposta() {
        return rispostaSelezionata;
    }


    //relativo al click del bottone
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

    private void initComponents() {
        PannInt = new JPanel(new BorderLayout());
        PannInput = new JPanel();
        PannOutput = new JPanel();

        Testo = new JTextArea(5, 1);
        Testo.setEditable(false);
        Testo.setBackground(new Color(227, 227, 227));
        Font fontPer = new Font("Georgia", Font.ITALIC, 16);
        Testo.setFont(fontPer);

        Testo.setLineWrap(true);
        Testo.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(Testo);
        PannInput.setLayout(new BorderLayout());
        PannInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        PannInput.add(scroll, BorderLayout.CENTER);

        SI = new JButton("SI");
        NO = new JButton("NO");

        PannOutput.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        PannOutput.add(NO);
        PannOutput.add(SI);

        PannInt.add(PannInput, BorderLayout.NORTH);
        PannInt.add(PannOutput, BorderLayout.CENTER);
    }

    //creo i getter e setter
    public JTextArea getTesto() {
        return Testo;
    }

    public JButton getNO() {
        return NO;
    }

    public JPanel getPannInt() {
        return PannInt;
    }

    public JButton getSI() {
        return SI;
    }

    public JPanel getPannInput() {
        return PannInput;
    }

    public JPanel getPannOutput() {
        return PannOutput;
    }

    public void setNO(JButton NO) {
        this.NO = NO;
    }

    public void setSI(JButton SI) {
        this.SI = SI;
    }

    public void setPannInput(JPanel pannInput) {
        PannInput = pannInput;
    }

    public void setPannOutput(JPanel pannOutput) {
        PannOutput = pannOutput;
    }

    public void setPannInt(JPanel pannInt) {
        PannInt = pannInt;
    }

    public char getRispostaSelezionata() {
        return rispostaSelezionata;
    }

    public void setRispostaSelezionata(char rispostaSelezionata) {
        this.rispostaSelezionata = rispostaSelezionata;
    }

    public void setTesto(JTextArea testo) {
        Testo = testo;
    }

}
