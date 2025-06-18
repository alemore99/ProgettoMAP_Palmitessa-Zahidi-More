package com.mycompany.pro.e.Livelli;

import java.io.*;
import java.util.*;

public class Indovinello {
    public String domanda;
    public String[] opzioni;
    public char rispostaCorretta;
    public String rispostaAperta;

    public Indovinello(String domanda, String[] opzioni, char rispostaCorretta) {
        this.domanda = domanda;
        this.opzioni = opzioni;
        this.rispostaCorretta = rispostaCorretta;
    }

    public Indovinello(String domanda, String rispostaAperta) {
        this.domanda = domanda;
        this.rispostaAperta = rispostaAperta;
    }

    //metodo per caricare indovinelli da un file
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

    public void setDomanda(String domanda) {
        this.domanda = domanda;
    }

    public String[] getOpzioni() {
        return opzioni;
    }

    public void setOpzioni(String[] opzioni) {
        this.opzioni = opzioni;
    }

    public char getRispostaCorretta() {
        return rispostaCorretta;
    }

    public void setRispostaCorretta(char rispostaCorretta) {
        this.rispostaCorretta = rispostaCorretta;
    }

    public String getRispostaAperta() {
        return rispostaAperta;
    }

    public void setRispostaAperta(String rispostaAperta) {
        this.rispostaAperta = rispostaAperta;
    }
}