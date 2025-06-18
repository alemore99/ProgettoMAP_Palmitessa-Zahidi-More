package com.mycompany.pro.e.Interfaccie;

import java.util.Random;

/**
 *
 * Variabili comuni tra più file, le queli non devono essere modificate ad ogni avvio
 */
public class VarGlobali {


    //metodo usato per generare i numeri random
    private static final Random RANDOM = new Random();

    /**
     *
     * @param max
     * @return
     */
    public static int generaRand(int max) {
        return RANDOM.nextInt(max);
    }

    //attributo usata per memorizzare l'ultimo livello nel livello 2
    public static char ultimoLivello = 'c';

    //attributo usate per vedere se si è entrati o meno nei livelli
    public static boolean livello2A_Eseguito = false;
    public static boolean livello2B_Eseguito = false;

    //attributo usate nel livello 3
    public static int probabilita = 60;
    public static int scivolata = 0;

    public static char getUltimoLivello() {
        return ultimoLivello;
    }

    public static void setUltimoLivello(char ultimoLivello) {
        VarGlobali.ultimoLivello = ultimoLivello;
    }

    public static boolean isLivello2A_Eseguito() {
        return livello2A_Eseguito;
    }

    public static void setLivello2A_Eseguito(boolean livello2A_Eseguito) {
        VarGlobali.livello2A_Eseguito = livello2A_Eseguito;
    }

    public static boolean isLivello2B_Eseguito() {
        return livello2B_Eseguito;
    }

    public static void setLivello2B_Eseguito(boolean livello2B_Eseguito) {
        VarGlobali.livello2B_Eseguito = livello2B_Eseguito;
    }

    public static int getProbabilita() {
        return probabilita;
    }

    public static void setProbabilita(int probabilita) {
        VarGlobali.probabilita = probabilita;
    }

    public static int getScivolata() {
        return scivolata;
    }

    public static void setScivolata(int scivolata) {
        VarGlobali.scivolata = scivolata;
    }
}