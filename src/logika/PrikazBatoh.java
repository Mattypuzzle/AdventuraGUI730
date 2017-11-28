/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
 
package logika;

/*******************************************************************************
 *  Třída PrikazKonec implementuje pro hru příkaz batoh.
 *  Tato třída je součástí jednoduché textové hry.
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PrikazBatoh implements IPrikaz {
 
    private static final String NAZEV = "batoh";
    private HerniPlan plan;
    
    /***************************************************************************
     * Konstruktor
     * 
     * @param plan herní plán, ve kterém se bude ve hře "vypisovat" obsah batohu
     */
    
    public PrikazBatoh(HerniPlan plan) {
        this.plan = plan;
    }

     /***************************************************************************
     * Podmínka pro zadání příkazu
     * @return string
     */
    @Override
    public String provedPrikaz(String... parametry) {
        if (parametry.length == 0) {
            return plan.getBatoh().getText();
        }
            return "Nemůžeš vybírat konkrétní věc, napiš pouze \"batoh\" pokud ho chceš prozkoumat.";
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
