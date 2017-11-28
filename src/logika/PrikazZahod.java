/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
 
package logika;

import interfaces.ObserverProstory;
import interfaces.ZmenyProstoru;
import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 *  Třída PrikazKonec implementuje pro hru příkaz zahoď - využívající se k odebrání věci z batohu.
 *  Tato třída je součástí jednoduché textové hry.
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PrikazZahod implements IPrikaz {
    
    private static final String NAZEV = "zahod";
    private HerniPlan plan;
    
    
    /**
    *  Konstruktor třídy
    *  
    *  @param plan herní plán, ve kterém se bude ve hře "zahazovat" předmět
    */    
   
    public PrikazZahod(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "zahoď". Zkouší vyhodit věc z batohu. Pokud je věc v batohu - vyhodí se. 
     *  Pokud v něm naopak není, vypíše se standartně chybové hlášení.
     *
     *@param parametry - jako  parametr obsahuje název věci, kterou hráč musí zvolit
     *@return zpráva, kterou vypíše hra hráči
     */ 
    
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) { 
            return "Co máš zahodit? Musíš zadat název věci.";
        }
        
        String nazevVeci = parametry[0];

        Prostor aktualniProstor = plan.getAktualniProstor();

        Batoh batoh = plan.getBatoh();
        
        Vec odebirana = batoh.odeberVec(nazevVeci);
        
        if (odebirana == null) {
            return "To v batohu není.";
        } else { 
                aktualniProstor.vlozVec(odebirana);
                return "Vec " + odebirana.getNazev() + " Byla odebrána z batohu.";
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
