/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
 
package logika;

/*******************************************************************************
 *  Třída PrikazKonec implementuje pro hru příkaz pouzij - k použití věci z batohu.
 *  Tato třída je součástí jednoduché textové hry.
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */

public class PrikazPouzij implements IPrikaz {
    
    private static final String NAZEV = "pouzij";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  @param plan herní plán, ve kterém se bude ve hře "využívat" vybraný předmět
    */    
    public PrikazPouzij(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "použij". Zkouší využít věcí z batohu, pokud se v něm nacházejí, je možné je aktivovat, pokud ne, samozřejmě to není možné. 
     *  Zde je i podmínka - výherní kombinace
     *
     *@param parametry - jako  parametr obsahuje jméno věci, kterou chce hráč použít
     *@return zpráva, kterou vypíše hra hráči
     */ 
    
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co máš použít? Musíš zadat název věci.";
        }
        
        String nazevVeci = parametry[0];

        Prostor aktualniProstor = plan.getAktualniProstor();
        
        Batoh batoh = plan.getBatoh();
        
        if (batoh.obsahuje(nazevVeci)) {
            
            String zprava = aktualniProstor.pouzijVec(nazevVeci);
            
            if (aktualniProstor.jePouzita("krevety") && aktualniProstor.jePouzita("bryle") && aktualniProstor.getNazev() == "kamen") {
                Prostor akvarium = plan.getVyherniProstor();
                plan.setAktualniProstor(akvarium);
                return akvarium.dlouhyPopis();
            }
            
            return zprava;
            
        } else {
            return "Tuto věc nemáš v batohu.";
        }
    }
    
    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}
