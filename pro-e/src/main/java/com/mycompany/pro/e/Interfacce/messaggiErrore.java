package com.mycompany.pro.e.Interfacce;

import javax.swing.*;
import java.awt.*;

/**
 * Classe usata per la visualizzazione di messaggi di errore, avvisi e conferme nel gioco.
 * Fornisce metodi statici per mostrare diversi tipi di dialoghi modali.
 */
public class messaggiErrore extends JDialog {
    private final int width;
    private final int height;

    /**
     * Costruttore principale per la creazione di dialoghi personalizzati.
     *
     * @param parent    La finestra padre a cui associare il dialogo
     * @param titolo    Il titolo della finestra di dialogo
     * @param messaggio Il testo del messaggio da visualizzare
     * @param icona     L'icona da mostrare accanto al messaggio
     * @param esciSuOk  Se true, chiude l'applicazione quando viene premuto OK
     * @param width     Larghezza della finestra di dialogo
     * @param height    Altezza della finestra di dialogo
     */

    public messaggiErrore(JFrame parent, String titolo,
                          String messaggio, Icon icona, boolean esciSuOk, int width, int height) {

        super(parent, titolo, true);
        this.width = width;
        this.height = height;
        setAlwaysOnTop(true);
        setLayout(new BorderLayout());

        JLabel label = new JLabel(messaggio, icona, JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(label, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(_ -> {
            dispose();
            if (esciSuOk) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel();
        panel.add(okButton);
        add(panel, BorderLayout.SOUTH);

        setSize(this.width, this.height);
        setLocationRelativeTo(parent);
        setResizable(false);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if (esciSuOk) {
            addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
        }
    }

    /**
     * Ridimensiona un'icona alle dimensioni specificate.
     *
     * @param icon   L'icona originale da ridimensionare
     * @param width  Larghezza desiderata
     * @param height Altezza desiderata
     * @return L'icona ridimensionata
     */
    private static Icon resizeIcon(Icon icon, int width, int height) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return icon;
    }

    /**
     * Mostra un dialogo di errore critico che termina l'applicazione.
     *
     * @param parent    La finestra padre (può essere null)
     * @param messaggio Il messaggio di errore da visualizzare
     */
    public static void mostraErrore(JFrame parent, String messaggio) {
        Icon icona = UIManager.getIcon("OptionPane.errorIcon");
        icona = resizeIcon(icona, 40, 40);
        messaggiErrore dialog = new messaggiErrore(parent, "Errore", messaggio, icona, true, 500, 150);
        dialog.setVisible(true);
    }

    /**
     * Mostra un dialogo di avviso non bloccante.
     *
     * @param parent    La finestra padre (può essere null)
     * @param messaggio Il messaggio di avviso da visualizzare
     */
    public static void mostraWarning(JFrame parent, String messaggio) {
        Icon icona = UIManager.getIcon("OptionPane.warningIcon");
        icona = resizeIcon(icona, 40, 40);
        messaggiErrore dialog = new messaggiErrore(parent, "Attenzione", messaggio, icona, false, 400, 150);
        dialog.setVisible(true);
    }

    /**
     * Mostra un dialogo di conferma/informazione.
     *
     * @param parent    La finestra padre (può essere null)
     * @param messaggio Il messaggio di conferma da visualizzare
     */
    public static void mostraConferma(JFrame parent, String messaggio) {
        Icon icona = UIManager.getIcon("OptionPane.informationIcon");
        icona = resizeIcon(icona, 40, 40);
        messaggiErrore dialog = new messaggiErrore(parent, "Conferma", messaggio, icona, false, 400, 150);
        dialog.setVisible(true);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
