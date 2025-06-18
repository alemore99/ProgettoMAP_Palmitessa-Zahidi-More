package com.mycompany.pro.e.Interfaccie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Mostra i messaggi d'errore del gioco o avvertimenti
 */
public class messaggiErrore extends JDialog {
    private int width;
    private int height;
    
    /**
     *
     * @param parent
     * @param titolo
     * @param messaggio
     * @param icona
     * @param esciSuOk
     * @param width
     * @param height
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
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (esciSuOk) {
                    System.exit(0);
                }
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
    
    //ridimensiona l'icona a sinistra dell'immagine
    private static Icon resizeIcon(Icon icon, int width, int height) {
        if (icon instanceof ImageIcon) {
            Image img = ((ImageIcon) icon).getImage();
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return icon;
    }

    /**
     *
     * @param parent
     * @param messaggio
     */
    public static void mostraErrore(JFrame parent, String messaggio) {
        Icon icona = UIManager.getIcon("OptionPane.errorIcon");
        icona = resizeIcon(icona, 40, 40);
        messaggiErrore dialog = new messaggiErrore(parent, "Errore", messaggio, icona, true, 400, 150);
        dialog.setVisible(true);
    }

    /**
     *
     * @param parent
     * @param messaggio
     */
    public static void mostraWarning(JFrame parent, String messaggio) {
        Icon icona = UIManager.getIcon("OptionPane.warningIcon");
        icona = resizeIcon(icona, 40, 40);
        messaggiErrore dialog = new messaggiErrore(parent, "Attenzione", messaggio, icona, false, 400, 150);
        dialog.setVisible(true);
    }

    /**
     *
     * @param parent
     * @param messaggio
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

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
