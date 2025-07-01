package com.mycompany.pro.e.api;

import com.mycompany.pro.e.Interfacce.messaggiErrore;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe utile a gestire le API riguardanti data e giorno corrente.
 * <p>
 * Fornisce sia un'interfaccia grafica Swing che un endpoint HTTP RESTful
 * per ottenere informazioni sulla data attuale.
 * </p>
 */
public class Data extends JPanel {
    private final JLabel dataLabel;
    private final JLabel giornoLabel;
    private final SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat giornoFormat = new SimpleDateFormat("EEEE", new Locale.Builder().setLanguage("it").setRegion("IT").build()
    );

    /**
     * Costruttore della classe Data.
     * <p>
     * Inizializza l'interfaccia grafica, avvia il timer per l'aggiornamento automatico
     * e avvia il server HTTP per l'API RESTful.
     * </p>
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
        Timer timer = new Timer(60_000, _ -> aggiornaData());
        timer.start();

        avviaHttpServer();
    }

    /**
     * Metodo main per testing autonomo della classe, creata da NetBeans.
     *
     * @param args Argomenti della riga di comando (non utilizzati)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Data app = new Data();
            app.setVisible(true);
        });
    }

    /**
     * Aggiorna le etichette con la data e il giorno corrente.
     */
    private void aggiornaData() {
        Date now = new Date();
        dataLabel.setText("Data: " + dataFormat.format(now));
        giornoLabel.setText("Giorno: " + giornoFormat.format(now));
    }

    /**
     * Avvia un server HTTP locale sulla porta 8000 che fornisce un endpoint RESTful.
     * <p>
     * L'endpoint "/data" restituisce un JSON con la data e il giorno corrente.
     * </p>
     */
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
                    exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(json.getBytes(StandardCharsets.UTF_8));
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
}

