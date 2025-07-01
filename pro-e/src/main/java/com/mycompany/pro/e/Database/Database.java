package com.mycompany.pro.e.Database;

import com.mycompany.pro.e.Interfacce.VarGlobali;
import com.mycompany.pro.e.Interfacce.messaggiErrore;
import com.mycompany.pro.e.Livelli.Indovinello;
import com.mycompany.pro.e.Livelli.Inventario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.List;

/**
 * Classe per la gestione del database del gioco.
 * Si occupa di creare la tabella per lo stato del gioco,
 * salvare e caricare lo stato del gioco, e gestire l'inventario.
 */
public class Database {
    private static final String DB_URL = "jdbc:h2:./stato_gioco";

    public Database() {
        creaTabella();
        aggiungiColonnaFinitoSeManca();
    }

    /**
     * Crea la tabella "stato_gioco" se non esiste già.
     * La tabella contiene le colonne per l'id, livello, inventario, indovinelli e finito.
     */
    private void creaTabella() {
        String sql = "CREATE TABLE IF NOT EXISTS stato_gioco (" +
                "id INT PRIMARY KEY, " +
                "livello INT, " +
                "inventario BLOB, " +
                "indovinelli BLOB, " +
                "finito BOOLEAN)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            messaggiErrore.mostraErrore(null, "Errore: " + e.getMessage());
        }
    }

    private boolean colonnaFinitoPresente() {
        String sql = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'STATO_GIOCO' AND COLUMN_NAME = 'FINITO'";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next();
        } catch (SQLException e) {
            messaggiErrore.mostraErrore(null, "Errore controllo colonna finito: " + e.getMessage());
            return false;
        }
    }

    private void aggiungiColonnaFinitoSeManca() {
        if (!colonnaFinitoPresente()) {
            String sql = "ALTER TABLE stato_gioco ADD COLUMN finito BOOLEAN";
            try (Connection conn = DriverManager.getConnection(DB_URL);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                messaggiErrore.mostraErrore(null, "Errore aggiunta colonna finito: " + e.getMessage());
            }
        }
    }

    /**
     * Salva lo stato del gioco nel database.
     * Serializza l'inventario e gli indovinelli in formato BLOB.
     *
     * @param stato Lo stato del gioco da salvare
     */
    public static void salva(StatoGioco stato) {
        String sql = "MERGE INTO stato_gioco (id, livello, inventario, indovinelli, finito) KEY(id) VALUES (1, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stato.getLivello());

            //serializza inventario
            ByteArrayOutputStream bosInv = new ByteArrayOutputStream();
            ObjectOutputStream oosInv = new ObjectOutputStream(bosInv);
            oosInv.writeObject(stato.getInventario());
            oosInv.flush();
            ps.setBytes(2, bosInv.toByteArray());

            //serializza indovinelli
            ByteArrayOutputStream bosInd = new ByteArrayOutputStream();
            ObjectOutputStream oosInd = new ObjectOutputStream(bosInd);
            oosInd.writeObject(stato.getIndovinelli());
            oosInd.flush();
            ps.setBytes(3, bosInd.toByteArray());

            ps.setBoolean(4, VarGlobali.getFinito());
            ps.executeUpdate();
        } catch (Exception e) {
            messaggiErrore.mostraErrore(null, "Errore durante il salvataggio dello stato: " + e.getMessage());
        }
    }

    /**
     * Carica lo stato del gioco dal database.
     * Deserializza l'inventario e gli indovinelli da BLOB.
     *
     * @return Lo stato del gioco caricato, o null se non esiste
     */
    public StatoGioco carica() {
        String sql = "SELECT livello, inventario, indovinelli, finito FROM stato_gioco WHERE id = 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int livello = rs.getInt("livello");
                VarGlobali.setLivello(livello);

                byte[] invBytes = rs.getBytes("inventario");
                Inventario inventario = null;
                try (ObjectInputStream oisInv = new ObjectInputStream(new ByteArrayInputStream(invBytes))) {
                    inventario = (Inventario) oisInv.readObject();
                    if (inventario == null) throw new RuntimeException("Inventario deserializzato è null");
                    inventario.inizializzaFrameInventario();
                } catch (Exception ex) {
                    messaggiErrore.mostraErrore(null, "Errore nella deserializzazione dell'inventario: " + ex.getMessage());
                }

                byte[] indBytes = rs.getBytes("indovinelli");
                List<Indovinello> indovinelli = null;
                if (indBytes != null && indBytes.length > 0) {
                    try (ObjectInputStream oisInd = new ObjectInputStream(new ByteArrayInputStream(indBytes))) {
                        indovinelli = (List<Indovinello>) oisInd.readObject();
                    } catch (Exception ex) {
                        messaggiErrore.mostraErrore(null, "Errore nella deserializzazione degli indovinelli chiusi: " + ex.getMessage());
                    }
                }

                boolean finito = rs.getBoolean("finito");
                VarGlobali.setFinito(finito);
                return new StatoGioco(inventario, livello, indovinelli, finito);
            }
        } catch (Exception e) {
            messaggiErrore.mostraErrore(null, "Errore durante il caricamento: " + e.getMessage());
        }
        return null;
    }

    /**
     * Carica l'inventario dal database.
     * Deserializza l'inventario da BLOB.
     *
     * @return L'inventario caricato, o null se non esiste
     */
    public static Inventario caricaInventario() {
        String sql = "SELECT inventario FROM stato_gioco WHERE id = 1";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                byte[] invBytes = rs.getBytes("inventario");
                if (invBytes != null && invBytes.length > 0) {
                    try (ObjectInputStream oisInv = new ObjectInputStream(new ByteArrayInputStream(invBytes))) {
                        Inventario inventario = (Inventario) oisInv.readObject();
                        inventario.inizializzaFrameInventario();
                        return inventario;
                    }
                }
            }
        } catch (Exception e) {
            messaggiErrore.mostraErrore(null, "Errore caricamento inventario: " + e.getMessage());
        }
        return null;
    }

    /**
     * Resetta il database, eliminando tutti i dati dalla tabella "stato_gioco".
     * Utilizzato per inizializzare il gioco o per ricominciare da capo.
     */
    public static void reset() {
        String sql = "DELETE FROM stato_gioco";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            messaggiErrore.mostraErrore(null, "Errore durante il reset del database: " + e.getMessage());
        }
    }
}