/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 *  Třída PrikazKonec implementuje pro hru příkaz seber pro sbírání věcí z prostoru a vkládání jich do batohu.
 *  Tato třída je součástí jednoduché textové hry.
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */

public class PrikazSeber implements IPrikaz {
    
    private static final String NAZEV = "seber";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy
    *  @param plan herní plán, ve kterém se bude ve hře "sbírat" vybraný předmět
    */    
    public PrikazSeber(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  Provádí příkaz "seber". Zkouší sebrat věc do batohu. Pokud je batoh prázdný a věc je možné unést, je vložena. 
     *  Pokud je batoh plný nebo věc nelze unést, naopak vložena není. V obou případech se vypisuje textové hlášení.
     *
     *@param parametry - jako  parametr obsahuje název věci, kterou hráč musí zvolit
     *@return zpráva, kterou vypíše hra hráči
     */ 
    
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return "Co máš sebrat? Musíš zadat název věci.";
        }
        
        String nazevVeci = parametry[0];

        Prostor aktualniProstor = plan.getAktualniProstor();
        
        Vec sbirana = aktualniProstor.odeberVec(nazevVeci);

        Batoh batoh = plan.getBatoh();
        
        if (sbirana == null) {
            return "To tu neni";
        } else { 
            if(sbirana.jePrenositelna()) {
                if (!batoh.jePlny()) {
                    batoh.pridejVec(sbirana);
                    return "Vec " + sbirana.getNazev() + " Byla uložena do batohu.";
                } else { 
                    aktualniProstor.vlozVec(sbirana);
                    return "Batoh je plný.";
                }
            } else { 
                aktualniProstor.vlozVec(sbirana);
                return "To nezvednes";
            }
        }
    }
    
     /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }
}