package logika;

import interfaces.ObserverProstory;
import interfaces.ZmenyProstoru;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 *@version    pro školní rok 2016/2017
 *
 *Update by Matěj Dusbaba
 *@version   1.01
 */

public class HerniPlan implements ZmenyProstoru{
    
    private Prostor aktualniProstor;
    private Prostor vyherniProstor;
    private Batoh batoh;  
    private Map<String, Prostor> prostory;
    private Map<String, Vec> veci;
    
    private List<ObserverProstory> seznamObserveru;
    
     /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví Sasanku.
     */
    
    public HerniPlan() {
        seznamObserveru = new ArrayList<>();
        prostory = new HashMap<String, Prostor>();
        veci = new HashMap<String, Vec>();
        zalozProstoryHry();
    }
    
    /**
     *  Vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví sasanku.
     *  
     *  Na začátku vytváříme jednotlivé prostory, dále definujeme sousední prostory/možné průchody, dále definujeme věci, po-té i batoh (důvod viz komentář ve třídě batoh)
     *  Po sléze vytváříme předměty v místnostech a definujeme základní logické prostory pro začátek a konec hry
     */
    
    private void zalozProstoryHry() {
        Prostor sasanka = new Prostor("sasanka", "Sasanka, odsud se Nemo ztratil.", 35, 20);
        Prostor ocean = new Prostor("ocean", "Oceán funguje jako rozcestník.", 180, 55);
        Prostor vrak = new Prostor("vrak","Jsi na dně ve vraku staré lodi. Můžeš tu najít zajímavé věci, ale nebuď chamtivý - může se to vymstít.", 45, 170);
        Prostor kamen = new Prostor("kamen","Dostal jsi se k místu kde bydlí pelikán, promluvte si.", 50, 330);
        Prostor akvarium = new Prostor("akvarium","Zde je Nemo.", 180, 335);
        
        prostory.put("sasanka", sasanka);
        prostory.put("ocean", ocean);
        prostory.put("vrak", vrak);
        prostory.put("kamen", kamen);
        prostory.put("akvarium", akvarium);
        
        sasanka.setVychod(ocean);
        ocean.setVychod(sasanka);
        ocean.setVychod(vrak);
        vrak.setVychod(ocean);
        vrak.setVychod(kamen);
        ocean.setVychod(kamen);
        kamen.setVychod(ocean);
        kamen.setVychod(vrak);
        
        Vec bryle = new Vec("bryle", true);
        Vec sklenice = new Vec("sklenice", true);
        Vec rasy = new Vec("rasy", false);
        Vec musle = new Vec("musle", true);
        Vec delo = new Vec("delo", false);
        Vec krevety = new Vec("krevety", true);
        
        veci.put("bryle", bryle);
        veci.put("sklenice", sklenice);
        veci.put("rasy", rasy);
        veci.put("musle",musle);
        veci.put("delo",delo);
        veci.put("krevety",krevety);
        
        batoh = new Batoh();
        
        sasanka.vlozVec(bryle);
        sasanka.vlozVec(sklenice);
        sasanka.vlozVec(rasy);
        ocean.vlozVec(musle);
        vrak.vlozVec(delo);
        vrak.vlozVec(krevety);
        
        aktualniProstor = sasanka;       
        vyherniProstor = akvarium; 
    }
    
    /**
     * 
     * @return vyherni prostor hry
     */
    public Prostor getVyherniProstor() {
        return vyherniProstor;
    }
    
    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *  @return aktuální prostor
     */
    
    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }
    
    /**
     *  Metoda vrací odkaz na batoh.
     *  @return batoh
     */
    public Batoh getBatoh() {
        return batoh;
    }
    
    /**
     * 
     * @return všechny prostory hry
     */
    public Map<String, Prostor> getProstory(){
        return prostory;
    }
    
    /**
     * 
     * @return všechny věci ve hře
     */
    public Map<String, Vec> getVeci(){
        return veci;
    }
    
    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *  @param  prostor nový aktuální prostor
     */
    
    public void setAktualniProstor(Prostor prostor) {
       aktualniProstor = prostor;
       upozorniPozorovatele();
    }
    
     /**
     *  Boolean porovnávající zda již hráč vyhrál (přidán getNazev, máme v příkazu Použij "zprávu" -> porovnáváme názvy)
     *  @return aktualní prostor == vyherniProstor
     */
    public boolean jeVyhra() {
        return aktualniProstor.getNazev() == vyherniProstor.getNazev();
    }
    /**
     * registrace observerů
     * @param observer Obsertver, který chce být měněn 
     */
    @Override
    public void registraceObserver(ObserverProstory observer) {
        seznamObserveru.add(observer);
    }

    /**
     * odebrání pozorovatelů
     * @param observer ktertý již nechce být měněn
     */
    @Override
    public void odebraniObserver(ObserverProstory observer) {
        seznamObserveru.remove(observer);
    }

    /**
     * metoda, která upozorní všechny Observery, že se něco změnilo
     */
    @Override
    public void upozorniPozorovatele() {
        for (ObserverProstory seznamObserver : seznamObserveru) {
            seznamObserver.aktualizuj();
        }
    }
}
