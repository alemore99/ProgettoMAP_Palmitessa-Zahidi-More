/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pro.e.api;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Classe utile a gestire il pannello dell'umidità e recuperare i dati dall'API OpenWeatherMap.
 * <p>
 * Visualizza l'umidità corrente per una posizione geografica specifica (Monopoli)
 * e si aggiorna automaticamente ogni 3 minuti.
 * </p>
 */
public class Umidita extends JPanel {
    private static final String API_KEY = "f640b79efa4ee4ef655a74662cd0fb5b";
    private static final double LATITUDE = 41.0056;
    private static final double LONGITUDE = 17.3257;
    private final JLabel humidityLabel = new JLabel("Umidità: -- %");

    /**
     * Costruttore della classe Umidita.
     * <p>
     * Inizializza l'interfaccia grafica e avvia il polling automatico
     * per l'aggiornamento dei dati ogni 3 minuti.
     * </p>
     */
    public Umidita() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        humidityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        humidityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(humidityLabel, BorderLayout.CENTER);

        updateHumidity();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::updateHumidity, 3, 3, TimeUnit.MINUTES);
    }

    /**
     * Recupera il contenuto da un URI specificato come Stringa.
     *
     * <p>Questo metodo effettua una richiesta HTTP GET all'URI fornito e legge la risposta
     * come una stringa codificata in UTF-8. La connessione viene stabilita usando {@link HttpURLConnection}
     * e la risposta viene letta riga per riga in un {@link StringBuilder}.</p>
     *
     * <p><b>Nota:</b> Il chiamante è responsabile della gestione delle eventuali eccezioni I/O che possono verificarsi
     * durante la connessione o la lettura della risposta.</p>
     *
     * @param uri l'URI da cui recuperare il contenuto
     * @return una Stringa contenente l'intera risposta dall'URI
     * @throws IOException              se si verifica un errore I/O durante la connessione o la lettura della risposta
     * @throws IllegalArgumentException se l'URI non può essere convertito in un URL
     * @throws SecurityException        se esiste un security manager e il suo metodo checkConnect non permette l'operazione
     * @see URL
     * @see HttpURLConnection
     * @see StandardCharsets#UTF_8
     */
    private static String getString(URI uri) throws IOException {
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseStr = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseStr.append(line);
        }
        reader.close();

        return responseStr.toString();
    }

    /**
     * Metodo main per testing autonomo della classe, creata da NetBeans.
     *
     * @param args Argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Umidità a Monopoli");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 190);
            frame.add(new Umidita());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Recupera e aggiorna i dati dell'umidità dall'API OpenWeatherMap.
     * <p>
     * Effettua una chiamata HTTP all'API e aggiorna l'interfaccia
     * con il valore corrente dell'umidità.
     * </p>
     */
    private void updateHumidity() {
        try {
            //carico l'http dell'api
            String urlString = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
                    LATITUDE, LONGITUDE, API_KEY
            );
            URI uri = URI.create(urlString);
            String response = getString(uri);
            double humidity = extractHumidityFromResponse(response);

            SwingUtilities.invokeLater(() ->
                    humidityLabel.setText(String.format("Umidità: %.0f %%", humidity))
            );

        } catch (Exception e) {
            SwingUtilities.invokeLater(() ->
                    humidityLabel.setText("Errore nel recupero umidità")
            );
        }
    }

    /**
     * Estrae il valore dell'umidità dalla risposta JSON dell'API.
     *
     * @param json La stringa JSON contenente i dati meteo
     * @return Il valore dell'umidità in percentuale
     * @throws RuntimeException Se il campo humidity non è presente nella risposta
     */
    private double extractHumidityFromResponse(String json) {
        String key = "\"humidity\":";
        int index = json.indexOf(key);
        if (index != -1) {
            int start = index + key.length();
            int end = json.indexOf(",", start);
            if (end == -1) end = json.indexOf("}", start);
            String humidityStr = json.substring(start, end).trim();
            return Double.parseDouble(humidityStr);
        }
        throw new RuntimeException("Humidity not found");
    }
}
