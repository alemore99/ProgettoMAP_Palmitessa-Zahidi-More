package com.mycompany.pro.e.Livelli;

import java.util.*;

/**
 * Parser per la verifica delle risposte aperte con tolleranza agli errori.
 * <p>
 * Fornisce metodi per confrontare risposte con:
 * <ul>
 *   <li>Tolleranza a errori di battitura (Distanza di Levenshtein)</li>
 *   <li>Riconoscimento di sinonimi</li>
 *   <li>Gestione di forme singolari/plurali</li>
 *   <li>Normalizzazione del testo</li>
 * </ul>
 */
public class Parser {

    private static final Set<String> articoli = Set.of(
            "il", "lo", "la", "i", "gli", "le", "un", "uno", "una",
            "al", "allo", "alla", "ai", "agli", "alle", "dal",
            "dallo", "dalla", "dai", "dagli", "dalle", "nel",
            "nello", "nella", "nei", "negli", "nelle"
    );

    private static final Map<Character, Character> accenti = Map.of(
            'à', 'a', 'è', 'e', 'é', 'e', 'ì', 'i', 'ò', 'o', 'ù', 'u'
    );

    private static final Map<String, Set<String>> SINONIMI = new HashMap<>();

    static {
        SINONIMI.put("quadro", Set.of("quadretti", "opera", "tela", "rappresentazione"));
    }

    /**
     * Verifica se una risposta è corretta con tolleranza agli errori.
     *
     * @param rispostaUtente   La risposta data dall'utente
     * @param rispostaCorretta La risposta corretta attesa
     * @return true se la risposta è considerata corretta, false altrimenti
     */
    public static boolean verificaRisposta(String rispostaUtente, String rispostaCorretta) {
        if (rispostaUtente == null || rispostaUtente.trim().isEmpty()) {
            return false;
        }

        String normalizzataUtente = normalizza(rispostaUtente);
        String normalizzataCorretta = normalizza(rispostaCorretta);

        if (normalizzataUtente.contains(normalizzataCorretta)) {
            return true;
        }

        String keywordUtente = estraiParolaChiave(normalizzataUtente);
        String keywordCorretta = estraiParolaChiave(normalizzataCorretta);

        return levenshteinDistance(keywordCorretta, keywordUtente) <= 1
                || sonoSinonimi(keywordCorretta, keywordUtente)
                || sonoFormeCompatibili(keywordCorretta, keywordUtente);
    }

    /**
     * Estrae la parola chiave più significativa da un testo.
     *
     * @param testo Il testo da analizzare
     * @return La parola chiave più significativa, o stringa vuota se non trovata
     */
    private static String estraiParolaChiave(String testo) {
        return Arrays.stream(testo.split("\\s+"))
                .map(parola -> parola.replaceAll("[.,;:!?']", "").trim())
                .filter(parola -> !parola.isEmpty() && !articoli.contains(parola.toLowerCase()))
                .findFirst()
                .orElse("");
    }

    /**
     * Normalizza un testo rimuovendo accenti, punteggiatura e stopwords.
     *
     * @param testo Il testo da normalizzare
     * @return Il testo normalizzato
     */
    private static String normalizza(String testo) {
        if (testo == null) return "";

        StringBuilder sb = new StringBuilder();
        for (char c : testo.toLowerCase(Locale.ROOT).toCharArray()) {
            sb.append(accenti.getOrDefault(c, c));
        }

        return sb.toString()
                .replaceAll("[.,;:!?']", "")
                .replaceAll("\\b(al|allo|alla|ai|agli|alle|dal|dallo|dalla|dai|dagli|dalle|"
                        + "nel|nello|nella|nei|negli|nelle|il|lo|la|l|un|uno|una|i|gli|le)\\b", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * Calcola la distanza di Levenshtein tra due stringhe.
     *
     * @param s1 Prima stringa
     * @param s2 Seconda stringa
     * @return La distanza tra le due stringhe
     */
    private static int levenshteinDistance(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Le stringhe non possono essere null");
        }

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
     * Verifica se due parole sono sinonimi.
     *
     * @param parola1 Prima parola
     * @param parola2 Seconda parola
     * @return true se le parole sono sinonimi, false altrimenti
     */
    private static boolean sonoSinonimi(String parola1, String parola2) {
        if (parola1 == null || parola2 == null) return false;

        parola1 = parola1.toLowerCase();
        parola2 = parola2.toLowerCase();

        if (parola1.equals(parola2)) return true;

        String finalParola = parola2;
        String finalParola1 = parola1;
        return SINONIMI.entrySet().stream()
                .anyMatch(entry -> {
                    Set<String> gruppo = new HashSet<>(entry.getValue());
                    gruppo.add(entry.getKey());
                    return gruppo.contains(finalParola1) && gruppo.contains(finalParola);
                });
    }

    /**
     * Verifica se due parole sono forme compatibili (singolare/plurale).
     *
     * @param parola1 Prima parola
     * @param parola2 Seconda parola
     * @return true se le parole sono forme compatibili, false altrimenti
     */
    private static boolean sonoFormeCompatibili(String parola1, String parola2) {
        if (parola1 == null || parola2 == null) return false;
        if (parola1.equals(parola2)) return true;

        boolean equals = parola2.equals(parola1.substring(0, parola1.length() - 1) + "i");
        boolean equals1 = parola1.equals(parola2.substring(0, parola2.length() - 1) + "i");
        return (parola1.endsWith("o") && equals) ||
                (parola1.endsWith("a") && parola2.equals(parola1.substring(0, parola1.length() - 1) + "e")) ||
                (parola1.endsWith("e") && equals) ||
                (parola2.endsWith("o") && equals1) ||
                (parola2.endsWith("a") && parola1.equals(parola2.substring(0, parola2.length() - 1) + "e")) ||
                (parola2.endsWith("e") && equals1);
    }
}