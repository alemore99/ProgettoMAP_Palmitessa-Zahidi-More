package com.mycompany.pro.e.Interfacce;

import com.mycompany.pro.e.Livelli.Inventario;
import com.mycompany.pro.e.api.Data;
import com.mycompany.pro.e.api.Meteo;
import com.mycompany.pro.e.api.Tempo;
import com.mycompany.pro.e.api.Umidita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe principale la quale gestisce l'interfaccia grafica del gioco.
 * <p>
 * Fornisce tutti i componenti necessari per la visualizzazione del gioco,
 * inclusi i pannelli per le immagini, il testo, i bottoni di navigazione
 * e le API meteo integrate.
 * </p>
 */
public class GUI extends JFrame {

    private static Inventario inventario;
    JButton buttAvanti;
    private JPanel Data;
    private JPanel Meteo;
    private JPanel Padre;
    private JLabel SfondoIntro;
    private JLabel SfondoLvl;
    private JPanel Tempo;
    private JPanel Umidita;
    private JTextArea jTextArea1;
    private JButton buttInventario;

    /**
     * Costruttore principale dell'interfaccia grafica.
     *
     * @param inventario L'oggetto inventario da associare all'interfaccia
     */
    public GUI(Inventario inventario) {
        GUI.inventario = inventario;
        initComponents();

        //zona API
        Meteo meteo = new Meteo("Monopoli");
        Umidita umidita = new Umidita();
        Data data = new Data();
        Tempo tempo = new Tempo("Monopoli");

        //configurazione pannello Meteo
        Meteo.setLayout(new BorderLayout());
        Meteo.removeAll();
        Meteo.add(meteo, BorderLayout.CENTER);
        Meteo.revalidate();
        Meteo.repaint();

        //configurazione pannello Umidita
        Umidita.setLayout(new BorderLayout());
        Umidita.removeAll();
        Umidita.add(umidita, BorderLayout.CENTER);
        Umidita.revalidate();
        Umidita.repaint();

        //configurazione pannello Data
        Data.setLayout(new BorderLayout());
        Data.removeAll();
        Data.add(data, BorderLayout.CENTER);
        Data.revalidate();
        Data.repaint();

        //configurazione pannello Tempo
        Tempo.setLayout(new BorderLayout());
        Tempo.removeAll();
        Tempo.add(tempo, BorderLayout.CENTER);
        Tempo.revalidate();
        Tempo.repaint();

        iconaPersonalizzata();
    }

    /**
     * Metodo main per l'avvio dell'applicazione, creata da NetBeans.
     *
     * @param args Argomenti della riga di comando
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new GUI(inventario).setVisible(true));
    }

    //aggiungo l'icona personalizzata
    public void iconaPersonalizzata() {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/icona.png")));
            setIconImage(icon.getImage()); //finestra

            //imposta l'icona per la taskbar
            if (Taskbar.isTaskbarSupported()) {
                Taskbar taskbar = Taskbar.getTaskbar();
                if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    taskbar.setIconImage(icon.getImage());
                }
            }
        } catch (Exception e) {
            messaggiErrore.mostraErrore(null, "Errore nel caricamento dell'icona: " + e.getMessage());
        }
    }

    /**
     * Aggiorna l'immagine di sfondo per il layout riguardante l'intro di gioco.
     *
     * @param percorso Il percorso dell'immagine da caricare
     */
    public void aggiornaImmagine(String percorso) {
        URL imgURL = getClass().getResource(percorso);
        if (imgURL == null) {
            messaggiErrore.mostraErrore(null, "Errore: immagine non caricata correttamente");
            SfondoIntro.setIcon(null);
        } else {
            SfondoIntro.setIcon(new ImageIcon(imgURL));
        }
    }

    /**
     * Aggiorna l'immagine di sfondo per il layout principale di gioco.
     *
     * @param percorso Il percorso dell'immagine da caricare
     */
    public void aggiornaImmagine2(String percorso) {
        URL imgURL = getClass().getResource(percorso);
        if (imgURL == null) {
            messaggiErrore.mostraErrore(null, "Errore: immagine non caricata correttamente");
            SfondoLvl.setIcon(null);
        } else {
            ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(percorso)));

            //addatta l'immagine mantenendo le proprorzioni origniali
            SfondoLvl.setIcon(new ImageIcon(originalIcon.getImage()) {
                @Override
                public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                    double ratio = Math.min(
                            (double) c.getWidth() / getIconWidth(),
                            (double) c.getHeight() / getIconHeight()
                    );
                    int w = (int) (getIconWidth() * ratio);
                    int h = (int) (getIconHeight() * ratio);
                    g.drawImage(getImage(), (c.getWidth() - w) / 2, (c.getHeight() - h) / 2, w, h, c);
                }
            });
        }
    }

    /**
     * Imposta un listener per i click sull'immagine di sfondo.
     *
     * @param listener L'oggetto che riceverà gli eventi di click
     */
    public void setImageClickListener(ImageClickListener listener) {
        rimuoviImageClickListener();

        if (listener != null) {
            SfondoLvl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evento) {
                    Rectangle areaImmagine = getImageBounds(SfondoLvl);

                    if (areaImmagine.contains(evento.getPoint())) {
                        int x = evento.getX() - areaImmagine.x;
                        int y = evento.getY() - areaImmagine.y;
                        listener.onImageClick(x, y);
                    }
                }
            });
        }
    }

    /**
     * Calcola l'area effettiva dell'immagine visualizzata nel JLabel.
     *
     * @param etichetta Il JLabel contenente l'immagine
     * @return Un rettangolo che rappresenta l'area dell'immagine
     */
    private Rectangle getImageBounds(JLabel etichetta) {
        Icon icona = etichetta.getIcon();
        if (icona == null) return new Rectangle();

        int larghezzaIcona = icona.getIconWidth();
        int altezzaIcona = icona.getIconHeight();
        int larghezzaEtichetta = etichetta.getWidth();
        int altezzaEtichetta = etichetta.getHeight();

        int x = (larghezzaEtichetta - larghezzaIcona) / 2;
        int y = (altezzaEtichetta - altezzaIcona) / 2;

        return new Rectangle(x, y, larghezzaIcona, altezzaIcona);
    }

    /**
     * Rimuove tutti i listener di click dall'immagine di sfondo.
     */
    public void rimuoviImageClickListener() {
        if (SfondoLvl != null) {
            MouseListener[] listeners = SfondoLvl.getMouseListeners();
            for (MouseListener ml : listeners) {
                SfondoLvl.removeMouseListener(ml);
            }
        }
    }

    /**
     * Restituisce il layout iniziale (ossia la intro).
     *
     * @return Il CardLayout utilizzato per cambiare pannelli
     */
    public CardLayout getCardLayout() {
        return (CardLayout) Padre.getLayout();
    }

    /**
     * Restituisce il pannello padre principale.
     *
     * @return Il JPanel contenitore principale
     */
    public JPanel getPadrePanel() {
        return Padre;
    }

    /**
     * Aggiunge testo all'area di testo principale.
     *
     * @param testo Il testo da aggiungere
     */
    public void ScriviTextArea(String testo) {
        jTextArea1.append(testo);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
    }

    /**
     * Pulisce il contenuto dell'area di testo principale.
     */
    public void PulisciTextArea() {
        jTextArea1.setText("");
    }

    /**
     * Disabilita i bottoni principali dell'interfaccia.
     */
    public void disablebottoni() {
        buttInventario.setVisible(false);
        buttAvanti.setVisible(false);
    }

    /**
     * Abilita i bottoni principali dell'interfaccia.
     */
    public void abilitabottoni() {
        buttInventario.setVisible(true);
        buttAvanti.setVisible(true);
    }

    private void initComponents() {
        Padre = new JPanel(new CardLayout());
        JPanel intro = new JPanel(new BorderLayout());
        JPanel panel2 = new JPanel(new BorderLayout());
        SfondoIntro = new JLabel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel panel1 = new JPanel(new BorderLayout());
        jTextArea1 = new JTextArea();
        buttAvanti = new JButton(">");
        buttInventario = new JButton("Inventario");
        JPanel API = new JPanel(new GridLayout(1, 4, 10, 0));
        JPanel api1 = new JPanel(new BorderLayout());
        Meteo = new JPanel();
        JPanel api2 = new JPanel(new BorderLayout());
        Umidita = new JPanel();
        JPanel api3 = new JPanel(new BorderLayout());
        Data = new JPanel();
        JPanel api4 = new JPanel(new BorderLayout());
        Tempo = new JPanel();
        SfondoLvl = new JLabel();
        SfondoLvl.setLayout(new BorderLayout());

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 800));

        setResizable(true);
        getContentPane().setLayout(new BorderLayout());
        pack(); //calcola automaticamente la dimensione ottimale

        SfondoIntro.setHorizontalAlignment(JLabel.CENTER);
        SfondoIntro.setVerticalAlignment(JLabel.CENTER);
        SfondoIntro.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/nero.png"))));
        panel2.add(SfondoIntro, BorderLayout.CENTER);
        intro.add(panel2, BorderLayout.CENTER);
        Padre.add(intro, "card4");

        //main
        SfondoLvl.setHorizontalAlignment(JLabel.CENTER);
        SfondoLvl.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/nero.png"))));

        JScrollPane scrollPane = new JScrollPane(jTextArea1);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFont(new Font("Georgia", Font.PLAIN, 20));
        jTextArea1.setBackground(new Color(241, 241, 241));
        scrollPane.setPreferredSize(new Dimension(400, 500));
        scrollPane.setMinimumSize(new Dimension(250, 300));

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(scrollPane, BorderLayout.CENTER);

        //pannello bottoni
        JPanel bottoniPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        bottoniPanel.add(buttInventario);
        bottoniPanel.add(buttAvanti);
        textPanel.add(bottoniPanel, BorderLayout.SOUTH);

        //actionlistener per il bottone dell'inventario
        buttInventario.addActionListener(_ -> inventario.mostraInventario());


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, SfondoLvl, textPanel);
        splitPane.setEnabled(false);
        splitPane.setResizeWeight(0.2);//spazio dell'immagine
        splitPane.setContinuousLayout(true);//ridimensiona in modo fluido
        splitPane.setDividerSize(5);

        panel1.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(panel1, BorderLayout.CENTER);

        api1.setBackground(Color.YELLOW);
        api1.add(Meteo, BorderLayout.CENTER);
        API.add(api1);

        api2.setBackground(Color.YELLOW);
        api2.add(Umidita, BorderLayout.CENTER);
        API.add(api2);

        api3.setBackground(Color.YELLOW);
        api3.add(Data, BorderLayout.CENTER);
        API.add(api3);

        api4.setBackground(Color.YELLOW);
        api4.add(Tempo, BorderLayout.CENTER);
        API.add(api4);

        API.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(API, BorderLayout.SOUTH);
        Padre.add(mainPanel, "card2");

        getContentPane().add(Padre, BorderLayout.CENTER);
        pack();

        //modifica dinamicamento l'immagine
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //forza il ridisegno dell'immagine
                SfondoLvl.revalidate();
                SfondoLvl.repaint();
            }
        });
    }



    public JButton getButtAvanti() {
        return buttAvanti;
    }

    /**
     * Classe interna per la gestione della pulizia dei listener.
     */
    public static class PulisciListener {

        /**
         * Rimuove tutti i listener dai componenti principali dell'interfaccia.
         *
         * @param gui L'istanza dell'interfaccia grafica da pulire
         */
        public static void pulisciTuttiListener(GUI gui) {
            // Rimuovi tutti gli ActionListener dal bottone avanti
            for (ActionListener al : gui.buttAvanti.getActionListeners()) {
                gui.buttAvanti.removeActionListener(al);
            }
            gui.rimuoviImageClickListener();
        }
    }
}