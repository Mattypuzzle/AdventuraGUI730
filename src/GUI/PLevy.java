/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.ObserverProstory;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logika.IHra;
import main.Main;

/**
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PLevy implements ObserverProstory{
    private IHra hra;
    private FlowPane panel;
    private Circle tecka;
    private ImageView marlyn;
    private TextArea textArea;
    
    /**
     * konstruktor na vytvoření panelu
     * @param hra instance hry na které se ´hraje
     */
    public PLevy(IHra hra) {
        this.hra = hra;
        hra.getHerniPlan().registraceObserver(this);
        createPanel();
    }

    
    /**
     * Metoda vytváří panel levé části herního okna
     */
    private void createPanel() {
       panel = new FlowPane();
     
       /*
       Pane, do kterého se předává mapa a pohybující se tečka
       */
       AnchorPane obrazekPane = new AnchorPane();
        
       ImageView obrazek = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/Map.jpg"), 400, 225, false, false));
       marlyn = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/marlyn.png"), 50, 34, false, false));
        
       tecka = new Circle(10, Paint.valueOf("blue"));
        
       tecka.setCenterX(50);
       tecka.setCenterY(70);
       marlyn.setX(50);
       marlyn.setX(70);
        
       obrazekPane.getChildren().addAll(obrazek, marlyn);
       
       
       panel.getChildren().addAll(obrazekPane);
       
       
       textArea = new TextArea();
       textArea.setEditable(false);
       textArea.setText(hra.vratUvitani());
       textArea.setPrefWidth(400);
       textArea.setPrefHeight(300);
     
       
       panel.setMargin(textArea, new Insets(10,0,10,0));
       
       panel.getChildren().addAll(textArea);
       
       
       
       
       aktualizuj();
    }
    
    
    /**
     * metoda, kterou využívají všichni, kteří chtějí do TextArey něco psát
     * tedy ti, spouští metodu hra.zpracujPrikaz(prikaz);
     * @param prikaz prikaz ke zpracovani
     * @param odpoved To, co vrací hra. zpracujPrikaz(prikaz);
     */
    public void setText(String prikaz, String odpoved){
        
        textArea.appendText("\n>> " + prikaz + "\n");
        textArea.appendText("\n\n" + odpoved + "\n");
    }
    

    /**
     * vrací obrázek Marilyn
     * @return Obrázek Marylin
     */
    public ImageView getMarlyn() {
        return marlyn;
    }

    /**
     * Nastaví obrázek pro Malyin
     * @param marlyn ImageView s Obrázkem Marylin
     */
    public void setMarlyn(ImageView marlyn) {
        this.marlyn = marlyn;
    }
    
    /**
     * vrací Text areu pro možnost do ní zapisovat text
     * @return Text Area, kam se vypisují texty
     */
    public TextArea getTextArea(){
        return textArea;
    }
    
    /**
     * metoda, která aktualizuje GUI dle současnéhjo stavu hry
     * Věci v batiohu, prostoru, ... V tomto případě pozici Marylin na Maapě
     */
    @Override
    public void aktualizuj() {
       marlyn.setY(hra.getHerniPlan().getAktualniProstor().getPosLeft());
       marlyn.setX(hra.getHerniPlan().getAktualniProstor().getPosTop());
       
       if(hra.getHerniPlan().jeVyhra()){
           AnchorPane container = new AnchorPane();
           GridPane grid = new GridPane();
           ImageView finalni = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/finalImage.png"), 400, 225, false, false));
           Text vyhra = new Text(160, 60, "Výhra!");
           Text podnadpis = new Text(40, 120, "Gratuluji, pelikán tě přenesl do akvária \n našel jsi Nema a vítězíš!\n\n"
                                     + "Děkuji, že jste si zahráli, přeji pěkný den!");
           
           
           Font nadpis = Font.font("Arial",FontWeight.BOLD,30);
           Font podtext = Font.font("Arial",FontWeight.BOLD,18);
           vyhra.setFill(Color.WHITESMOKE);
           podnadpis.setFill(Color.WHITESMOKE);
           
           vyhra.setFont(nadpis);
           podnadpis.setFont(podtext);
           
           grid.add(vyhra, 0, 0);
           grid.add(podnadpis, 0, 1);
           grid.setVgap(7);
           grid.setPadding(new Insets(10, 0, 10, 0));
           
           container.getChildren().addAll(finalni, vyhra, podnadpis);
           
           panel.getChildren().clear();
           panel.getChildren().add(container);
       }
    }
    /**
     * varcí panel této části GUI
     * @return Mapa s Marylin a TextArea
     */
    public FlowPane getPanel() {
        return panel;
    }
    
    /**
     * Nastaví novou hruz, ze které má panel čerpat informace
     * @param hra aktuální instance hry
     */
    public void setHra(IHra hra){
        this.hra = hra;
    }
}
