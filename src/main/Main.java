/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import GUI.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import uiText.TextoveRozhrani;
import logika.IHra;
import logika.Hra;

/**
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class Main extends Application {
    
    private IHra hra;
    private Stage primaryStage;
    private PLevy pLevy;
    private PPravy pPravy;
    private PSpodni pSpodni;
    private PHorni pHorni;
    private BorderPane mainBorder;
    
    @Override
    public void start(Stage primaryStage) {
       hra = new Hra();
       this.primaryStage = primaryStage;
       mainBorder = new BorderPane();
       Scene scene = new Scene(mainBorder,800, 700);
       
       setmainBorderPane(mainBorder);
    


       primaryStage.setTitle("Finding Nemo!");
       primaryStage.setScene(scene);
       primaryStage.show();
       
       podstataHry(hra);
        
    }
    
    private void setmainBorderPane(BorderPane border){
        this.mainBorder = border;
    }
    
    /**
     * Po spuštění se vytvoří nové instance GUI a vloží se do aktuálůního
     * BorderPanu
     * Znovu se focusne Textové pole na vkládání příkazů
     */
    private void podstataHry(IHra hra){
        pLevy = new PLevy(hra);
        pPravy = new PPravy(hra, pLevy);
        pSpodni = new PSpodni(hra, this, pLevy);
        pHorni = new PHorni(hra, this);

        setpHorni(pHorni);
        setpLevy(pLevy);
        setpPravy(pPravy);
        setpSpodni(pSpodni);
        
        mainBorder.setTop(pHorni.getPanel());
        mainBorder.setLeft(pLevy.getPanel());
        mainBorder.setCenter(pPravy.getPanel());
        mainBorder.setBottom(pSpodni.getPanel());
        Background background = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
        mainBorder.setBackground(background);

        pSpodni.getTextField().requestFocus();
    }

    public void setpLevy(PLevy pLevy) {
        this.pLevy = pLevy;
    }

    public void setpPravy(PPravy pPravy) {
        this.pPravy = pPravy;
    }

    public void setpSpodni(PSpodni pSpodni) {
        this.pSpodni = pSpodni;
    }

    public void setpHorni(PHorni pHorni) {
        this.pHorni = pHorni;
    }
    
    /**
     * nastaví v this novou instanci hry a spustí podstatu hry, aby se 
     * aktualizovaly všechny části GUI
     */
    public void zalozNovouHru(){
        this.hra = new Hra();
        podstataHry(this.hra);
    }
    
    /**
     * varcí aktuální instanci hry
     * @return aktuální instance hry
     */
    public IHra getHra(){
        return hra;
    }
    
    /**
     * horní panel - nástrojová lišta
     * @return horní panel
     */
    public PHorni getPHorni() {
        return pHorni;
    }

    /**
     * levý panel - mapa s textAreou
     * @return levý panel
     */
    public PLevy getPLevy() {
        return pLevy;
    }

    /**
     * proavá strana - východy, batoh a věci v prostoru
     * @return pravý panel
     */
    public PPravy getPPravy() {
        return pPravy;
    }

    /**
     * spodní panel - pole pro textový vstup příkazů
     * @return spodní panel
     */
    public PSpodni getPSpodni() {
        return pSpodni;
    }

    /**
     * primary stage ve které se (okno) zobrazuje GUI
     * @return Primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length == 0) {
            launch(args);
        }
        else{
            if (args[0].equals("-text")) {
                IHra hra = new Hra();
                TextoveRozhrani textoveRozhrani = new TextoveRozhrani(hra);
                textoveRozhrani.hraj();
            }
            else{
                System.out.println("Neplatny parametr");
            }
        }
        
    }
    
}
