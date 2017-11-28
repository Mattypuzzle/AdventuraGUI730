/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import logika.IHra;
import main.Main;

/**
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PSpodni {
    
    private IHra hra;
    private FlowPane panel;
    private Main main;
    private TextField prikazTextField;
    private PLevy pLevy;
    
    /**
     * Třída nepotřebuje Observer, protože se v ní nic nemění. Obsahuje pouze TextField
     * @param hra aktuální hra
     * @param main třída main
     * @param pLevy levý panel, ve kjterém je TextArea, aby se do ní mohl vypisovat text
     */
    public PSpodni(IHra hra, Main main, PLevy pLevy) {
        this.hra = hra;
        this.main = main;
        this.pLevy = pLevy;
        createPanel();
    }
    
    /**
     * metoda vytvoří panel a celý ho uloží do FlowPanu panel
     */
    private void createPanel() {
       panel = new FlowPane();
       
       Label zadejPrikazLabel = new Label("Zadej příkaz: ");
       zadejPrikazLabel.setFont(Font.font("Arial",FontWeight.BOLD,16));
      
       prikazTextField = new TextField(" ");
       prikazTextField.setPrefWidth(300);
       /*
       po kliknutí enteru (akci) se zpracuje prikaz uzivatelem zadany
       */
       prikazTextField.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event){
                   String prikaz = prikazTextField.getText();
                   
                   String odpoved = hra.zpracujPrikaz(prikaz);
                   
                   pLevy.setText(prikaz, odpoved);
                   
                  
                   prikazTextField.setText("");
                   
           }
       });
       
       /**
        * rozložení
        */
       panel.setAlignment(Pos.CENTER);
      
       panel.getChildren().addAll(zadejPrikazLabel, prikazTextField);
       panel.setMargin(prikazTextField, new Insets(10,10,10,10));
    }
    
    /**
     * panel celé třídy vytvořený v  createPanel
     * @return panel s Tetxovým polem
     */
    public FlowPane getPanel() {
        return panel;
    }
    
    /**
     * nastaví třídě novou hru
     * @param hra nová hra
     */
    public void setHra(IHra hra){
        this.hra = hra;
    }
    
    /**
     * vrací textové pole, aby ve třídě main, mohla být spuštěna
     * metoda RequestFocus(); - uživatel po spuštění hry může rovnou psát a nemusí 
     * klikat na toto pole
     * @return pole, do kterého se píší příkazy
     */
    public TextField getTextField() {
        return prikazTextField;
    }
        
}
