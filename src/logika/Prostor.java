package logika;

import interfaces.ObserverProstory;
import interfaces.ZmenyProstoru;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 * 
 * Update by Matěj Dusbaba
 * @version   1.01
 */
public class Prostor implements ZmenyProstoru{

    private String nazev;          
    private String popis;         
    private Set<Prostor> vychody;   
    private Map<String, Vec> veci;  
    private Collection<String> pouziteVeci; 
    
    private List<ObserverProstory> seznamObserveru;
    
    
    
    private double posLeft;
    private double posTop;

    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "hala", "trávník
     * před domem"
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     * @param posLeft kde je freddy na mape Y
     * @param posTop kde je freddy na mape X
     */
    
    public Prostor(String nazev, String popis, double posLeft, double posTop) {
        this.nazev = nazev;
        this.popis = popis;
        vychody = new HashSet<>();
        veci = new HashMap<>();
        pouziteVeci = new ArrayList<>();
        
        seznamObserveru = new ArrayList<>();
        
        this.posLeft = posLeft;
        this.posTop = posTop;
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
    
      @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Prostor)) {
            return false;   
        }
       
        Prostor druhy = (Prostor) o;

       return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }      

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v mistnosti/prostoru " + popis + ".\n"
                + popisVychodu() + "\n" 
                + popisVeci();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "východy:";
        for (Prostor sousedni : vychody) {
            vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }
    
    /**
     * 
     * @return název všech věcí
     */
    private String popisVeci() {
        
        String vracenyText = "Veci:";
        
        for (String nazevVeci : veci.keySet()) {
            
            vracenyText += " " + nazevVeci;
        }
        
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
                   .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
                   .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }
    
    /**
     * 
     * @param neco Vec která se vkládá
     * @return zda byla věc vložena
     */
    public boolean vlozVec(Vec neco) {
        
        if (!veci.containsValue(neco)){
            veci.put(neco.getNazev(), neco);
            upozorniPozorovatele();
            return true;
        }
        else if (!veci.containsKey(neco.getNazev())) {
            veci.put(neco.getNazev(), neco);
            upozorniPozorovatele();
            return true;
        }
        return false;
    }

    /**
     * Boolean pro věc v prostoru
     * @param nazev věci
     * @return boolean zda je věc v prostoru
     */
    public boolean jeVecVProstoru(String nazev) {
        return veci.containsKey(nazev);
    }
    
   /**
    * Boolean pro odebrání věci
    * upozorněšní pozorovatele je Observer
    * @param nazev název věci
    * @return boolean zda byla věc odebrána
    */
    public Vec odeberVec(String nazev) {
        Vec odebirana = veci.remove(nazev);
        upozorniPozorovatele();
        return odebirana;
    }
    
   /**
    * Boolean pro použití věci
    * @param nazev věci
    * @return boolean zda byla věc použita 
    */
    public boolean jePouzita (String nazev) {
        return pouziteVeci.contains(nazev);
    }
    
    /**
     * Vypisuje informaci dle použití věci
     * @return string - věc byla použita, pokud ne return string o úspěchu, že je věc použita a zapisuje se jako použitá
     * @param nazev věci
     */
    public String pouzijVec (String nazev) {
        if (jePouzita(nazev)) {
            return "Věc jsi již použil - nemůžeš znovu.";
        } else {
            pouziteVeci.add(nazev);
            return "Věc použita.";
        }
    }

    /**
     * vrátí v mapě všechny věci v prostoru
     * @return mapa věci v prostoru
     */
    public Map<String, Vec> getVeci(){
        return veci;
    }
    /**
     * vrací souřadnice prostoru na mapě
     * @return souřadnice od Leva
     */
    public double getPosLeft() {
        return posLeft;
    }

    /**
     * nastavuje souřadnicer prostoru
     * @param posLeft souřadnice od Leva
     */
    public void setPosLeft(double posLeft) {
        this.posLeft = posLeft;
    }

    
    /**
     * vrací souřadnice prostoru na mapě
     * @return souřadnice od Shora
     */
    public double getPosTop() {
        return posTop;
    }

    /**
     * nastavuje souřadnicer prostoru
     * @param posTop souřadnice od Shora
     */
    public void setPosTop(double posTop) {
        this.posTop = posTop;
    }
    
    /**
     * pomocí této metody se observer může registrovat
     * @param observer třída s měnícím se prostředím (např. PravyPanel)
     */
    @Override
    public void registraceObserver(ObserverProstory observer) {
        seznamObserveru.add(observer);
    }

    /**
     * pomocí této metody se observer může odhlásit
     * @param observer třída s měnícím se prostředím (např. PravyPanel)
     */
    @Override
    public void odebraniObserver(ObserverProstory observer) {
        seznamObserveru.remove(observer);
    }

    /**
     * tato metoda upozorní všechny pozorovatele, že se něco změni,
     * tudíš by se měli aktualizovat. Tyto pak slibují, že mají naimplementovanou metodu aktualizuj();
     * tudíž to mohou provést
     */
    @Override
    public void upozorniPozorovatele() {
        for (ObserverProstory seznamObserver : seznamObserveru) {
            seznamObserver.aktualizuj();
        }
    }
}
