/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
 
package logika;

import javafx.scene.image.Image;

/*******************************************************************************
 * Třída věc která slouží primárně k její definici, tzn. názvu a volby přenositelnosti
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class Vec {
    
    private String nazev;
    private boolean prenositelnost;
    private Image image;

    /***************************************************************************
     * Konstruktor pro Věc u které evidujeme název a přenositelnost
     */
    public Vec(String nazev, boolean prenositelnost)
    {
        this.nazev = nazev;
        this.prenositelnost = prenositelnost;
    }
    
   /**
    * 
    * @return nazev veci
    */
    public String getNazev() {
        return nazev;
    }
    
   /**
    * Boolean udávající přenositelnost
     * return prenositelnost
    * @return prenositelnost
    */
    public boolean jePrenositelna() {
        return prenositelnost;
    }
    
    /**
     * přiřadí věci daný obrázek
     * @param image obrázek dané věci
     */
    public void setImage(Image image){
        this.image = image;
    }
    
    /**
     * vrací obrázek dané věci
     * @return Image věci
     */
    public Image getImage(){
        return image;
    }
}