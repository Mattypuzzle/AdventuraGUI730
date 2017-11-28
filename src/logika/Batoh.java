/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */

package logika;
import interfaces.ObserverProstory;
import interfaces.ZmenyProstoru;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

/*******************************************************************************
 * Třída batoh je vlastně věc, která může obsahovat další věc, k evidenci využita Map
 * Můžeme kontrolovat konkrétní obsah, jeho velikost, vkládat a zahazovat věci z prostoru, batoh má limitní konstantu 3 
 * @author    Matěj Dusbaba
 * @version   1.01
 */

public class Batoh implements ZmenyProstoru{
    private final Map<String, Vec> obsah;
    private final int omezeni = 3;
    
    private List<ObserverProstory> seznamObserveru;
    
    /***************************************************************************
     * Konstruktor -> definujeme, že "obsah" bude unikátní hash (String)
     */
    public Batoh() {
        obsah = new HashMap<>();
        
        seznamObserveru = new ArrayList<>();
    }
    
    /**
     * pridá věc do batohu a upozorní pozorovatele
     * @param vec vec, která má být přidána
     */
    public void pridejVec(Vec vec) {
        obsah.put(vec.getNazev(), vec);
        upozorniPozorovatele();
    }
    
    /**
     * Set zde jako datový atribut, porovnáváme zde klíč a dle toho se vypisuje obsah batohu
     * @return mapu veci v batohu
     */
    public String getText() {
        Set<String> klice = obsah.keySet();
        String vypisBatohu = "V batohu je: \n";
        for (String klic : klice) {
            vypisBatohu += klic + "\n";
        }
        return vypisBatohu;
    }
    
    /**
     *  Metoda odebírající věc z batohu
     * @param nazev nazev veci
     *  @return obsah.remove(nazev)
     */
    public Vec odeberVec(String nazev) {
        Vec odebirana = obsah.remove(nazev);        
        upozorniPozorovatele();
        return odebirana;
    }
    
     /**
     *  Metoda vracející obsah batohu
     *  @return obsah
     */
    public Map<String, Vec> vratObsah() {
        return obsah;
    }
    
     /**
     *  Boolean udávající plnost batohu
     *  @return obsah.size() == 3
     */
    public boolean jePlny() {
        return obsah.size() == 3;
    }
    
     /**
     *  Klíč z kolekce se páruje s názvem věci v batohu
     * @param nazev nazev veci
     *  @return obsah.containsKey(nazev)
     */
    public boolean obsahuje(String nazev) {
        return obsah.containsKey(nazev);
    }

     /**
     *  Metoda vrací číslo pro zjištění obsazenosti batohu
     *  @return obsah.size()
     */
    public int kolikObsazeno() {
        return obsah.size();
    }
    
    @Override
    public void registraceObserver(ObserverProstory observer) {
        seznamObserveru.add(observer);
    }

    @Override
    public void odebraniObserver(ObserverProstory observer) {
        seznamObserveru.remove(observer);
    }

    @Override
    public void upozorniPozorovatele() {
        for (ObserverProstory seznamObserver : seznamObserveru) {
            seznamObserver.aktualizuj();
        }
    }
}
