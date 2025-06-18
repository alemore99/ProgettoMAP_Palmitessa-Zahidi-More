package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfaccie.messaggiErrore;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Stack;

public class Inventario {
    private Stack<String> inventario = new Stack<>();
    private JFrame frameInventario;
    private JTextArea areaTesto;

    public Inventario() {
        inventario = new Stack<>();
        inizializzaFrameInventario();
    }

    //inizializza il JFrame per la visualizzazione
    private void inizializzaFrameInventario() {
        frameInventario = new JFrame("Visualizzazione Inventario");
        frameInventario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameInventario.setLayout(new BorderLayout());

        //pannello principale con bordi e margini
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 240, 240));

        areaTesto = new JTextArea();
        areaTesto.setEditable(false);
        areaTesto.setFont(new Font("Georgia", Font.PLAIN, 14));
        areaTesto.setLineWrap(true);
        areaTesto.setWrapStyleWord(true);
        areaTesto.setBackground(new Color(255, 255, 255));
        areaTesto.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(areaTesto);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(255, 255, 255));

        JLabel titleLabel = new JLabel("Inventario", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Georgia", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        titleLabel.setForeground(new Color(70, 70, 70));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(240, 240, 240));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frameInventario.add(mainPanel);

        //impostazioni finestra
        frameInventario.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/baule.png"))).getImage());
        assert frameInventario != null;
        frameInventario.pack();
        frameInventario.setMinimumSize(new Dimension(350, 250));
        frameInventario.setSize(450, 350);
        frameInventario.setLocationRelativeTo(null);
    }

    //metodo per mostrare l'inventario
    public void mostraInventario() {
        SwingUtilities.invokeLater(() -> {
            //se è vuoto creo la pila
            if (frameInventario == null || areaTesto == null) {
                new Inventario();
            }

            StringBuilder contenuto = new StringBuilder();
            contenuto.append("Oggetti totali: ").append(inventario.size()).append("\n\n");

            if (inventario.empty()) {
                contenuto.append("L'inventario è vuoto\n");
            } else {
                for (int i = inventario.size() - 1; i >= 0; i--) {
                    contenuto.append("• ").append(inventario.get(i)).append("\n");
                }
            }

            areaTesto.setText(contenuto.toString());
            frameInventario.setVisible(true);
            frameInventario.pack();
            if (frameInventario.getHeight() < 150) {
                frameInventario.setSize(frameInventario.getWidth(), 150);
            }
        });
    }

    //aggiungo un oggetto
    public void aggiungi(String elemento) {
        if(elemento == null) {
            messaggiErrore.mostraConferma(null, "oggetto nullo");
        } else if(inventario.contains(elemento)) {
            messaggiErrore.mostraConferma(null, "oggetto già aggiunto");
        } else {
            inventario.push(elemento);
            messaggiErrore.mostraConferma(null, elemento + " aggiunto all'inventario ");
            if (frameInventario != null && frameInventario.isVisible()) {
                mostraInventario();
            }
        }
    }

    public Stack<String> getInventario() {
        return inventario;
    }

    public void setInventario(Stack<String> inventario) {
        this.inventario = inventario;
    }

    public JFrame getFrameInventario() {
        return frameInventario;
    }

    public void setFrameInventario(JFrame frameInventario) {
        this.frameInventario = frameInventario;
    }

    public JTextArea getAreaTesto() {
        return areaTesto;
    }

    public void setAreaTesto(JTextArea areaTesto) {
        this.areaTesto = areaTesto;
    }
}