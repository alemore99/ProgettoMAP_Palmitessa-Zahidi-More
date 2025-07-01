package com.mycompany.pro.e.api;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Classe utile a gestire le API meteo e la visualizzazione delle condizioni atmosferiche.
 * <p>
 * Utilizza l'API di OpenWeatherMap per ottenere i dati meteo in tempo reale
 * e li fa visualizzare in un pannello grafico con un'icona solare personalizzata.
 * </p>
 */
public class Meteo extends JPanel {

    private final JLabel lblTemperatura;

    /**
     * Costruttore della classe Meteo.
     * <p>
     * Inizializza l'interfaccia grafica e avvia il polling automatico
     * per l'aggiornamento dei dati meteo ogni 3 minuti.
     * </p>
     *
     * @param citta La città per cui visualizzare le informazioni meteo
     */
    public Meteo(String citta) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(new panelloSole(), BorderLayout.WEST);

        //etichetta temperatura
        lblTemperatura = new JLabel("...", JLabel.CENTER);
        lblTemperatura.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblTemperatura.setForeground(Color.DARK_GRAY);
        lblTemperatura.setPreferredSize(new Dimension(200, 190));
        add(lblTemperatura, BorderLayout.CENTER);

        aggiornaMeteo(citta);

        //aggiorno ogni 3 minuti
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> aggiornaMeteo(citta), 3, 3, TimeUnit.MINUTES);
    }

    /**
     * Recupera e aggiorna i dati meteo dalla API OpenWeatherMap.
     * <p>
     * Effettua una chiamata HTTP asincrona e aggiorna l'interfaccia
     * con la temperatura corrente.
     * </p>
     *
     * @param citta La città per cui recuperare i dati meteo
     */
    private void aggiornaMeteo(String citta) {
        String apiKey = "1718f7885cefe9d3008db9c2f4522a74";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + citta + "&appid=" + apiKey + "&units=metric&lang=it";

        new Thread(() -> {
            try {
                //carico la richiesta http
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(urlString))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();

                int tempIndex = json.indexOf("\"temp\":");
                if (tempIndex != -1) {
                    int inizio = tempIndex + 7;
                    int fine = json.indexOf(",", inizio);
                    String temperatura = json.substring(inizio, fine);

                    SwingUtilities.invokeLater(() -> lblTemperatura.setText(temperatura + "°C"));
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> lblTemperatura.setText("--°C"));
            }
        }).start();
    }


    /**
     * Pannello interno per la visualizzazione dell'icona del sole.
     */
    static class panelloSole extends JPanel {

        /**
         * Costruttore del pannello per il sole.
         */
        public panelloSole() {
            setPreferredSize(new Dimension(80, 190));
            setBackground(Color.WHITE);
        }

        /**
         * Disegna l'icona del sole.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawSun((Graphics2D) g);
        }

        /**
         * Disegna il sole con i raggi.
         *
         * @param g2 Il contesto grafico 2D su cui disegnare
         */
        private void drawSun(Graphics2D g2) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int raggio = 25;
            g2.setColor(new Color(255, 204, 0));
            g2.fillOval(centerX - raggio, centerY - raggio, raggio * 2, raggio * 2);

            g2.setStroke(new BasicStroke(2));
            for (int i = 0; i < 360; i += 30) {
                double angolo = Math.toRadians(i);
                int x1 = (int) (centerX + Math.cos(angolo) * (raggio + 5));
                int y1 = (int) (centerY + Math.sin(angolo) * (raggio + 5));
                int x2 = (int) (centerX + Math.cos(angolo) * (raggio + 15));
                int y2 = (int) (centerY + Math.sin(angolo) * (raggio + 15));
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
