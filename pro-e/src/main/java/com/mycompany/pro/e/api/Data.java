/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pro.e.api;

/**
 *
 *
 * Per quanto riguarda le API, questo riguardo la programmazione in rete e le restful
 */

import com.mycompany.pro.e.Interfaccie.messaggiErrore;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 *API relative alla data
 * @author aless
 */
public class Data extends JPanel {
    private final JLabel dataLabel;
    private final JLabel giornoLabel;
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat giornoFormat = new SimpleDateFormat("EEEE", new Locale.Builder().setLanguage("it").setRegion("IT").build()
    );

    public JLabel getDataLabel() {
        return dataLabel;
    }

    /**
     *
     */
    public Data() {

        setLayout(new GridLayout(2, 1));

        dataLabel = new JLabel("", SwingConstants.CENTER);
        dataLabel.setFont(new Font("Arial", Font.BOLD, 22));
        giornoLabel = new JLabel("", SwingConstants.CENTER);
        giornoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        setBackground(Color.WHITE);

        add(dataLabel);
        add(giornoLabel);

        aggiornaData();

        //aggiorna ogni minuto
        Timer timer = new Timer(60_000, e -> aggiornaData());
        timer.start();

        avviaHttpServer();
    }

    private void aggiornaData() {
        Date now = new Date();
        dataLabel.setText("Data: " + dataFormat.format(now));
        giornoLabel.setText("Giorno: " + giornoFormat.format(now));
    }

    private void avviaHttpServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8000), 0);

            server.createContext("/data", exchange -> {
                if ("GET".equals(exchange.getRequestMethod())) {
                    Date now = new Date();
                    String data = dataFormat.format(now);
                    String giorno = giornoFormat.format(now);

                    String json = String.format("{\"data\": \"%s\", \"giorno\": \"%s\"}", data, giorno);

                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                    exchange.sendResponseHeaders(200, json.getBytes("UTF-8").length);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(json.getBytes("UTF-8"));
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1);
                }
            });

            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            messaggiErrore.mostraErrore(null, "Errore server HTTP: " + e.getMessage());
        }
    }

    public JLabel getGiornoLabel() {
        return giornoLabel;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Data app = new Data();
            app.setVisible(true);
        });
    }
}

