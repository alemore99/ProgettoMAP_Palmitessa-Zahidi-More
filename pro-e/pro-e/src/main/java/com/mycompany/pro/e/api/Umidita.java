/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pro.e.api;

/**
 *
 *
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author aless
 */
public class Umidita extends JPanel {
    private final JLabel humidityLabel = new JLabel("Umidità: -- %");
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private static final String API_KEY = "aa26b688378bc2e91b3ac193beda77e2";
    private static final double LATITUDE = 41.0056;
    private static final double LONGITUDE = 17.3257;

    /**
     *
     * Classe che gestisce il pannello umidita
     */
    public Umidita() {
        setLayout(new BorderLayout());
         setBackground(Color.WHITE);
        humidityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        humidityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(humidityLabel, BorderLayout.CENTER);

        updateHumidity();
        scheduler.scheduleAtFixedRate(this::updateHumidity, 3, 3, TimeUnit.MINUTES);
    }

    private void updateHumidity() {
        try {
            //carico l'http dell'api
            String urlString = String.format(
                    "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric",
                    LATITUDE, LONGITUDE, API_KEY
            );
            URI uri = URI.create(urlString);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder responseStr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseStr.append(line);
            }
            reader.close();

            String response = responseStr.toString();
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

    //estrae l'umidita dalla stringa
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

    /**
     *
     * @param args
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
}
