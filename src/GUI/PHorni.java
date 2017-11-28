/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.IHra;
import logika.Prostor;
import main.Main;

/**
 * Třída vytváří horní nstrojovou lištu
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PHorni {
    
    

    private IHra hra;
    private MenuBar menuBar;
    private static Main main;

    /**
     * konstruktor inicializuje přiřazení hry a třídy main
     * @param hra současná hra
     * @param main hlavní třída aplikace
     */
    public PHorni(IHra hra, Main main) {
        this.hra = hra;
        this.main = main;
        createPanel();
    }
    /**
     * 
     * @return výslednou nástrojovou lištu
     */
    public MenuBar getPanel() {
        return menuBar;
    }

    /**
     * nastaví novou hru
     * @param hra nová hra
     */
    public void setHra(IHra hra) {
        this.hra = hra;
    }
    
    /**
     * metoda vytváří panel nástrojové lišty ve kterém je zahrnuto:
     * -> SOubor, Nová hra
     * -> O hře, Nápověda
     */
    private void createPanel() {
       menuBar = new MenuBar();
        
       
       Menu soubor = new Menu("Soubor");
       
       MenuItem novaHra = new MenuItem("Nová hra");
        
       /*
       je potřeba nastavit nové observery, vamazat TextArea, vytvořit novou hru a předat ji všem částem GUI
       */
        novaHra.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                main.zalozNovouHru();
                
                IHra novaHra = main.getHra();
                
            }
        });
       
        
        /*
        ovládací prvky menu ...
        */
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        
        MenuItem konec = new MenuItem("Konec hry");
        konec.setMnemonicParsing(true);
        
        konec.setOnAction((ActionEvent event) -> {
            System.exit(0);
       });
        
        MenuItem napoveda = new MenuItem("Napoveda");
        
        napoveda.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                Stage stage = new Stage();
                stage.setTitle("Napoveda");
                WebView webView = new WebView();
                
                webView.getEngine().load(Main.class.getResource("/zdroje/napoveda.html").toExternalForm());
                
                stage.setScene(new Scene(webView, 1024,400));
                stage.show();
            
            }
        });

        
        Menu help = new Menu("Pomoc");
        
        MenuItem oProgramu = new MenuItem("O programu");
        
        oProgramu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                
                alert.setTitle("O programu");
                alert.setHeaderText("Nemo!");
                alert.setContentText("Autor: Matěj Dusbaba; Tato hra byla vytvořena v rámci předmětu "
                        + "Softwarové inženýrství při Vysoké škole Ekonomické v Praze. Dotazy směřujte "
                        + "na email autora: dusm01@vse.cz");
                alert.initOwner(main.getPrimaryStage());
                alert.showAndWait();
                
            }
        });
        soubor.getItems().addAll(novaHra,konec);
        help.getItems().addAll(oProgramu, napoveda);
        
        /*
        nakonec vše strčíme do menuBar
        */
        menuBar.getMenus().addAll(soubor, help);
        
    }
    
}
