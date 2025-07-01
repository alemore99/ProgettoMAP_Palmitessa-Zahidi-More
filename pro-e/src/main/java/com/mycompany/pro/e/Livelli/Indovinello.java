package com.mycompany.pro.e.Livelli;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un indovinello nel gioco.
 * <p>
 * Supporta due tipi di indovinelli:
 * <ul>
 *   <li>A risposta chiusa (scelta multipla con opzioni)</li>
 *   <li>A risposta aperta (risposta testuale libera)</li>
 * </ul>
 * </p>
 * <p>
 * Fornisce metodi per il caricamento degli indovinelli da file.
 * </p>
 */
public class Indovinello implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String domanda;
    private String[] opzioni;
    private char rispostaCorretta;
    private String rispostaAperta;

    /**
     * Costruttore per indovinelli a risposta chiusa (scelta multipla).
     *
     * @param domanda          Il testo della domanda
     * @param opzioni          Array di stringhe con le opzioni di risposta
     * @param rispostaCorretta La lettera corrispondente alla risposta corretta (es. 'a', 'b', ecc.)
     */
    public Indovinello(String domanda, String[] opzioni, char rispostaCorretta) {
        this.domanda = domanda;
        this.opzioni = opzioni;
        this.rispostaCorretta = rispostaCorretta;
    }

    /**
     * Costruttore per indovinelli a risposta aperta.
     *
     * @param domanda        Il testo della domanda
     * @param rispostaAperta La risposta corretta attesa
     */
    public Indovinello(String domanda, String rispostaAperta) {
        this.domanda = domanda;
        this.rispostaAperta = rispostaAperta;
    }

    /**
     * Carica una lista di indovinelli da un file di risorse.
     * <p>
     * Formato del file:
     * <ul>
     *   <li>Per risposte chiuse: domanda|opzione1;opzione2;...|risposta_corretta</li>
     *   <li>Per risposte aperte: domanda||risposta_corretta</li>
     * </ul>
     * </p>
     *
     * @param nomeRisorsa Il percorso del file di risorse
     * @return Lista di oggetti Indovinello
     * @throws IOException           Se si verificano errori di lettura del file
     * @throws FileNotFoundException Se il file specificato non viene trovato
     */
    public static List<Indovinello> caricaDaFile(String nomeRisorsa) throws IOException {
        List<Indovinello> lista = new ArrayList<>();
        try (InputStream in = Indovinello.class.getClassLoader().getResourceAsStream(nomeRisorsa)) {
            if (in == null) {
                throw new FileNotFoundException("Risorsa non trovata: " + nomeRisorsa);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] parti = linea.split("\\|");
                    if (parti.length == 3) {
                        String domanda = parti[0].trim();
                        if (!parti[1].isEmpty()) {
                            String[] opzioni = parti[1].split(";");
                            char rispostaCorretta = parti[2].trim().charAt(0);
                            lista.add(new Indovinello(domanda, opzioni, rispostaCorretta));
                        } else {
                            String rispostaAperta = parti[2].trim();
                            lista.add(new Indovinello(domanda, rispostaAperta));
                        }
                    } else if (parti.length == 2) {
                        String domanda = parti[0].trim();
                        String rispostaAperta = parti[1].trim();
                        lista.add(new Indovinello(domanda, rispostaAperta));
                    }
                }
            }
        }
        return lista;
    }

    public String getDomanda() {
        return domanda;
    }

    public String[] getOpzioni() {
        return opzioni;
    }

    public char getRispostaCorretta() {
        return rispostaCorretta;
    }

    public String getRispostaAperta() {
        return rispostaAperta;
    }
}