/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
 
package logika;

/*******************************************************************************
 * Instance třídy {@code PrikazProzkoumej} představují ...
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PrikazProzkoumej implements IPrikaz {
  
    private static final String NAZEV = "prozkoumej";
    private HerniPlan plan;
    
    /**
    *  Konstruktor třídy s parametrem plán
    */    
   
    public PrikazProzkoumej(HerniPlan plan) {
        this.plan = plan;
    }
    
    /**
    *  Metoda vracející aktuální prostor s jeho popisem
    */    
   
    @Override
    public String provedPrikaz(String... parametry) {
        return plan.getAktualniProstor().dlouhyPopis();
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
