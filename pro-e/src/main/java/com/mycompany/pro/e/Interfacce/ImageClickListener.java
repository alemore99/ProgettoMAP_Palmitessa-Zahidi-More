package com.mycompany.pro.e.Interfacce;

/**
 * Interfaccia funzionale per la gestione degli eventi di click su immagini.
 * <p>
 * Fornisce un metodo per catturare le coordinate (x, y) di un click
 * relativo all'immagine, dopo aver calcolato la posizione corretta
 * considerando il centraggio dell'immagine nel componente.
 * </p>
 */
@FunctionalInterface
public interface ImageClickListener {

    /**
     * Metodo invocato quando viene rilevato un click sull'immagine.
     *
     * @param x La coordinata x del click relativa all'immagine (0 = bordo sinistro)
     * @param y La coordinata y del click relativa all'immagine (0 = bordo superiore)
     */
    void onImageClick(int x, int y);
}