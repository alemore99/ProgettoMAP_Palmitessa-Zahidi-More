package com.mycompany.pro.e.Livelli;

import com.mycompany.pro.e.Interfacce.messaggiErrore;
import com.mycompany.pro.e.Database.Database;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;

/**
 * Classe che gestisce l'inventario del giocatore.
 * <p>
 * L'inventario è implementato come una pila (Stack) di stringhe che rappresentano
 * gli oggetti raccolti dal giocatore durante il gioco. Fornisce metodi per:
 * <ul>
 *   <li>Aggiungere oggetti all'inventario</li>
 *   <li>Visualizzare il contenuto corrente</li>
 *   <li>Gestire l'interfaccia grafica dell'inventario</li>
 * </ul>
 * </p>
 */
public class Inventario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    private Stack<String> inventario = new Stack<>();

    /**
     * Ottiene la Stack interna dell'inventario
     *
     * @return La Stack contenente gli oggetti dell'inventario
     */
    public Stack<String> getInventario() {
        return inventario; // Ritorniamo direttamente la Stack
    }

    //transient esclude questi campi dalla serializzazione
    private transient JFrame frameInventario;
    private transient JTextArea areaTesto;


    public Inventario() {
        Inventario dbInv = Database.caricaInventario();
        if (dbInv != null) {
            this.inventario = dbInv.getInventario();
        } else {
            this.inventario = new Stack<>();
        }
        inizializzaFrameInventario();
    }

    /**
     * Inizializza il frame per la visualizzazione dell'inventario.
     * Configura tutti i componenti grafici necessari.
     * Crea la grafica dell'inventario con un titolo, un'area di testo e uno scroll pane.
     */
    public void inizializzaFrameInventario() {
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

    /**
     * Mostra la finestra dell'inventario con il contenuto aggiornato.
     * Se l'inventario è vuoto, mostra un messaggio appropriato.
     */
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

    /**
     * Aggiunge un nuovo oggetto all'inventario.
     *
     * @param elemento L'oggetto da aggiungere (non nullo e non già presente)
     */
    public void aggiungi(String elemento) {
        if (elemento == null) {
            messaggiErrore.mostraConferma(null, "oggetto nullo");
        } else if (inventario.contains(elemento)) {
            messaggiErrore.mostraConferma(null, "oggetto già aggiunto");
        } else {
            inventario.push(elemento);
            messaggiErrore.mostraConferma(null, elemento + " aggiunto all'inventario ");
            if (frameInventario != null && frameInventario.isVisible()) {
                mostraInventario();
            }
        }
    }

    /**
     * Svuota l'inventario, rimuovendo tutti gli oggetti.
     */
    public void svuota() {
        inventario.clear();
        if (frameInventario != null && frameInventario.isVisible()) {
            mostraInventario();
        }
    }
}