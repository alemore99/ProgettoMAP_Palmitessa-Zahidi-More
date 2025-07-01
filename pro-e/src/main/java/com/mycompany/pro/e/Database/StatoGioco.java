package com.mycompany.pro.e.Database;

import com.mycompany.pro.e.Livelli.Indovinello;
import com.mycompany.pro.e.Livelli.Inventario;

import java.io.Serializable;
import java.util.List;

/**
 * Classe che rappresenta lo stato del gioco, contenente l'inventario,
 * il livello corrente e una lista di indovinelli.
 */
public class StatoGioco implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Inventario inventario;
    private int livello;
    private List<Indovinello> indovinelli;
    private boolean finito;

    /**
     * Costruttore della classe StatoGioco.
     *
     * @param inventario  L'inventario del giocatore
     * @param livello      Il livello corrente del gioco
     * @param indovinelli  Lista degli indovinelli disponibili
     * @param finito       Indica se il gioco è finito o meno
     */
    public StatoGioco(Inventario inventario, int livello, List<Indovinello> indovinelli, boolean finito) {
        this.inventario = inventario;
        this.livello = livello;
        this.indovinelli = indovinelli;
        this.finito = finito;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public int getLivello() {
        return livello;
    }

    public List<Indovinello> getIndovinelli() {
        return indovinelli;
    }

    public boolean getFinito() {
        return finito;
    }

}