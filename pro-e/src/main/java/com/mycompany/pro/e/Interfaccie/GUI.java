package com.mycompany.pro.e.Interfaccie;

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
 *  Mostra e crea l'interfaccia del gioco
 *
 */
public class GUI extends JFrame {

    private static Inventario inventario;
    /**
     *
     */
    public JButton buttAvanti;
    public JButton buttInventario;


    /**
     *
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

    //aggiungo l'icona personalizzata
    public void iconaPersonalizzata() {
        try {
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/icona.png")));
            setIconImage(icon.getImage()); //finetra

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
     *Aggiorna l'immagine per il layout della intro
     * @param percorso
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
     * aggiorna tutto il resto (2° layout)
     * @param percorso
     */
    public void aggiornaImmagine2(String percorso) {
        URL imgURL = getClass().getResource(percorso);
        if (imgURL == null) {
            messaggiErrore.mostraErrore(null, "Errore: immagine non caricata correttamente");
            SfondoLvl.setIcon(null);
        } else {
            SfondoLvl.setIcon(new ImageIcon(imgURL));
        }
    }

    /**
     * Prendo e catturo il click del mouse
     * @param listener
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

    //riceve il click del mouse, restituendo le coordinate corrette rispetto all'immagine
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
     * Pulisci Llistener del click
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
     * Pulisci listener effettivo
     */
    public static class PulisciListener {

        /**
         *
         * @param gui
         */
        public static void pulisciTuttiListener(GUI gui) {
            // Rimuovi tutti gli ActionListener dal bottone avanti
            for (ActionListener al : gui.buttAvanti.getActionListeners()) {
                gui.buttAvanti.removeActionListener(al);
            }
            gui.rimuoviImageClickListener();
        }
    }


    /**
     * Metodo che si occupa di cambiare il layout dal pannello dell'intro a quello del main
     * @return
     */
    public CardLayout getCardLayout() {
        return (CardLayout) Padre.getLayout();
    }

    /**
     *
     * @return
     */
    public JPanel getPadrePanel() {
        return Padre;
    }

    /**
     *
     * @param testo
     */
    public void ScriviTextArea(String testo) {
        jTextArea1.append(testo);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
    }

    /**
     *
     */
    public void PulisciTextArea() {
        jTextArea1.setText("");
    }

    public void disablebottoni() {
        buttInventario.setVisible(false);
        buttAvanti.setVisible(false);
    }

    public void abilitabottoni() {
        buttInventario.setVisible(true);
        buttAvanti.setVisible(true);
    }

    private void initComponents() {
        Padre = new JPanel(new CardLayout());
        Intro = new JPanel(new BorderLayout());
        panel2 = new JPanel(new BorderLayout());
        SfondoIntro = new JLabel();
        mainPanel = new JPanel(new BorderLayout());
        Panel1 = new JPanel(new BorderLayout());
        jTextArea1 = new JTextArea();
        SfondoLvl = new JLabel();
        buttAvanti = new JButton(">");
        buttInventario = new JButton("Inventario");
        API = new JPanel(new GridLayout(1, 4, 10, 0));
        api1 = new JPanel(new BorderLayout());
        Meteo = new JPanel();
        api2 = new JPanel(new BorderLayout());
        Umidita = new JPanel();
        api3 = new JPanel(new BorderLayout());
        Data = new JPanel();
        api4 = new JPanel(new BorderLayout());
        Tempo = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 800));

        setResizable(true);
        getContentPane().setLayout(new BorderLayout());
        pack(); //calcola automaticamente la dimensione ottimale

        SfondoIntro.setHorizontalAlignment(JLabel.CENTER);
        SfondoIntro.setVerticalAlignment(JLabel.CENTER);
        SfondoIntro.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/foto/comuni/nero.png"))));
        panel2.add(SfondoIntro, BorderLayout.CENTER);
        Intro.add(panel2, BorderLayout.CENTER);
        Padre.add(Intro, "card4");

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
        splitPane.setResizeWeight(0.3); //indica la dimensione dell'immagine, il resto va al testo
        splitPane.setDividerSize(0); //dimensione della barra spazziatrice tra testo e immagine

        Panel1.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(Panel1, BorderLayout.CENTER);

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
    }

    /**
     *
     * @param args
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

    private JPanel API;
    private JPanel Data;
    private JPanel Intro;
    private JPanel Meteo;
    private JPanel Padre;
    private JPanel Panel1;
    private JLabel SfondoIntro;
    private JLabel SfondoLvl;
    private JPanel Tempo;
    private JPanel Umidita;
    private JPanel api1;
    private JPanel api2;
    private JPanel api3;
    private JPanel api4;
    private JTextArea jTextArea1;
    private JPanel mainPanel;
    private JPanel panel2;

    //getter e setter
    public static Inventario getInventario() {
        return inventario;
    }

    public static void setInventario(Inventario inventario) {
        GUI.inventario = inventario;
    }

    public JButton getButtAvanti() {
        return buttAvanti;
    }

    public void setButtAvanti(JButton buttAvanti) {
        this.buttAvanti = buttAvanti;
    }

    public JButton getButtInventario() {
        return buttInventario;
    }

    public void setButtInventario(JButton buttInventario) {
        this.buttInventario = buttInventario;
    }

    public JPanel getAPI() {
        return API;
    }

    public void setAPI(JPanel API) {
        this.API = API;
    }

    public JPanel getData() {
        return Data;
    }

    public void setData(JPanel data) {
        Data = data;
    }

    public JPanel getIntro() {
        return Intro;
    }

    public void setIntro(JPanel intro) {
        Intro = intro;
    }

    public JPanel getMeteo() {
        return Meteo;
    }

    public void setMeteo(JPanel meteo) {
        Meteo = meteo;
    }

    public JPanel getPadre() {
        return Padre;
    }

    public void setPadre(JPanel padre) {
        Padre = padre;
    }

    public JPanel getPanel1() {
        return Panel1;
    }

    public void setPanel1(JPanel panel1) {
        Panel1 = panel1;
    }

    public JLabel getSfondoIntro() {
        return SfondoIntro;
    }

    public void setSfondoIntro(JLabel sfondoIntro) {
        SfondoIntro = sfondoIntro;
    }

    public JLabel getSfondoLvl() {
        return SfondoLvl;
    }

    public void setSfondoLvl(JLabel sfondoLvl) {
        SfondoLvl = sfondoLvl;
    }

    public JPanel getTempo() {
        return Tempo;
    }

    public void setTempo(JPanel tempo) {
        Tempo = tempo;
    }

    public JPanel getUmidita() {
        return Umidita;
    }

    public void setUmidita(JPanel umidita) {
        Umidita = umidita;
    }

    public JPanel getApi1() {
        return api1;
    }

    public void setApi1(JPanel api1) {
        this.api1 = api1;
    }

    public JPanel getApi2() {
        return api2;
    }

    public void setApi2(JPanel api2) {
        this.api2 = api2;
    }

    public JPanel getApi3() {
        return api3;
    }

    public void setApi3(JPanel api3) {
        this.api3 = api3;
    }

    public JPanel getApi4() {
        return api4;
    }

    public void setApi4(JPanel api4) {
        this.api4 = api4;
    }

    public JTextArea getjTextArea1() {
        return jTextArea1;
    }

    public void setjTextArea1(JTextArea jTextArea1) {
        this.jTextArea1 = jTextArea1;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public void setPanel2(JPanel panel2) {
        this.panel2 = panel2;
    }

}