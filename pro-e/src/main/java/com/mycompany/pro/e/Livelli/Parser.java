package com.mycompany.pro.e.Livelli;

import java.util.*;
/**
 * Parser usato per verificare la correttezzza delle risposte aperte
 *
 */


public class Parser {

    /**
     *  Metodo che verifica se la rispsota è corretta o meno
     * @param rispostaUtente
     * @param rispostaCorretta
     * @return distanza se <= 1 o se sono sinonimi
     */
    public static boolean verificaRisposta(String rispostaUtente, String rispostaCorretta) {
        if (rispostaUtente == null || rispostaUtente.trim().isEmpty()) {
            return false;
        }
        int distanza = 0;

        String attesa = normalizza(rispostaCorretta);
        String utente = normalizza(rispostaUtente);

        //accetta se la parola chiave attesa è contenuta nella risposta utente
        if (utente.contains(attesa))
            return true;
        String parolaChiaveUtente = estraiParolaChiave(utente);
        String parolaChiaveAttesa = estraiParolaChiave(attesa);
        distanza = levenshteinDistance(parolaChiaveAttesa, parolaChiaveUtente);
        return distanza <= 1
                || sonoSinonimi(parolaChiaveAttesa, parolaChiaveUtente)
                || sonoSingolarePluraleCompatibili(parolaChiaveAttesa, parolaChiaveUtente);
    }

    /**
     * Metodo che estrae la parola chiave dalla frase inserita in input
     * @param testo
     * @return null
     */
    public static String estraiParolaChiave(String testo) {
        Set<String> paroleDaIgnorare = Set.of("il", "lo", "la", "i", "gli", "le", "un", "uno", "una",
                                              "al", "allo", "alla", "ai", "agli", "alle", "dal",
                                              "dallo", "dalla", "dai", "dagli", "dalle", "nel",
                                              "nello", "nella", "nei", "negli", "nelle");

        String[] parole = testo.split(" ");
        for (String parola : parole) {
            parola = parola.toLowerCase(Locale.ROOT).replaceAll("[.,;:!?']", "").trim();
            //restituisce la prima parola significativa
            if (!paroleDaIgnorare.contains(parola) && !parola.isEmpty()) {
                return parola;
            }
        }
        return "";
    }

    /**
     * Metodo usato per eliminare i caratteri problematic, tipo: -accenti, -apostrofi, -ecc...
     * @param testo
     * @return testo
     */
    public static String normalizza(String testo) {
        if (testo == null) return "";
        testo = testo.toLowerCase(Locale.ROOT);
        testo = testo.replaceAll("[.,;:!?']", "");
        testo = testo
                .replace("à", "a")
                .replace("è", "e")
                .replace("é", "e")
                .replace("ì", "i")
                .replace("ò", "o")
                .replace("ù", "u");
        testo = testo.replaceAll("\\b(al|allo|alla|ai|agli|alle|dal|dallo|dalla|dai|dagli|dalle|"
                + "nel|nello|nella|nei|negli|nelle|il|lo|la|l|un|uno|una|i|gli|le)\\b", "");
        testo = testo.replaceAll("\\s+", " ").trim();
        return testo;
    }

    /**
     * misura la differenza di spazi tra due stringhe
     * @param s1
     * @param s2
     * @return dp[s1.length()][s2.length()];
     */
    public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= s2.length(); j++) dp[0][j] = j;
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[s1.length()][s2.length()];
    }

    /**
     *  //crea una mappa per associare i vari sinonimi
     */
    public static final Map<String, Set<String>> sinonimi = new HashMap<>();
    static {
        sinonimi.put("quadro", Set.of("quadretti", "opera", "tela", "rappresentazione"));
    }


    /**
     *  Metodo che confronta due parole per vedere se sono sinonimi
     * @param parola1
     * @param parola2
     * @return
     */
    public static boolean sonoSinonimi(String parola1, String parola2) {
        parola1 = parola1.toLowerCase();
        parola2 = parola2.toLowerCase();

        if (parola1.equals(parola2)) return true;

        for (Map.Entry<String, Set<String>> entry : sinonimi.entrySet()) {
            Set<String> gruppo = new HashSet<>(entry.getValue());
            gruppo.add(entry.getKey());
            if (gruppo.contains(parola1) && gruppo.contains(parola2)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Metodo che confronta due parole per vedere se sono al singolare o al plurale
     * @param p1
     * @param p2
     * @return
     */
    public static boolean sonoSingolarePluraleCompatibili(String p1, String p2) {
        if (p1.equals(p2)) return true;
        return (p1.endsWith("o") && p2.equals(p1.substring(0, p1.length() - 1) + "i")) ||
                (p1.endsWith("a") && p2.equals(p1.substring(0, p1.length() - 1) + "e")) ||
                (p1.endsWith("e") && p2.equals(p1.substring(0, p1.length() - 1) + "i")) ||
                (p2.endsWith("o") && p1.equals(p2.substring(0, p2.length() - 1) + "i")) ||
                (p2.endsWith("a") && p1.equals(p2.substring(0, p2.length() - 1) + "e")) ||
                (p2.endsWith("e") && p1.equals(p2.substring(0, p2.length() - 1) + "i"));
    }


}
