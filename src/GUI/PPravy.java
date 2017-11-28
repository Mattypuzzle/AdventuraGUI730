/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;



import interfaces.ObserverProstory;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import logika.IHra;
import logika.Prostor;
import logika.Vec;
import main.Main;

/**
 *
 * @author    Matěj Dusbaba
 * @version   1.01
 */
public class PPravy implements ObserverProstory{
    private IHra hra;
    private FlowPane panel;
    private final Map<String, AnchorPane> seznamObrazkuMap;
    private ObservableList<Prostor> vychody;
    private ObservableList<Vec> veci;
    private ObservableList<Vec> prostorVeci;
    private final Map<String, Prostor> prostory;
    private final Map<String, Vec> seznamVeci;
    private final PLevy pLevy;
    
    private static final double MARGIN_PRVKU = 30;
    private static final double SIRKA_PANELU = 400 - MARGIN_PRVKU;
    private static final double SIRKA_PANELU_2 = SIRKA_PANELU/2;
    
    public PPravy(IHra hra, PLevy pLevy) {
        this.hra = hra;
        this.pLevy = pLevy;
        seznamObrazkuMap = new HashMap<>();
        seznamVeci = hra.getHerniPlan().getVeci();
        prostory = hra.getHerniPlan().getProstory();
        hra.getHerniPlan().registraceObserver(this);
        hra.getHerniPlan().getBatoh().registraceObserver(this);
        
        for (String nazev : prostory.keySet()) {
            Prostor prostor = prostory.get(nazev);
            prostor.registraceObserver(this);
        }
        
        hra.getHerniPlan().getAktualniProstor().registraceObserver(this);
        createPanel();
    }

    
        
        
    /**
     * 
     * @return panel k zobrazení
     */
    public FlowPane getPanel() {
        return panel;
    }
    
    /**
     * nastaví novou hru
     * @param hra aktuální hra
     */
    public void setHra(IHra hra){
        this.hra = hra;
        createPanel();
    }
    
    /**
     * hra, na které běží toto GUI
     * @return aktuální hra pro tento objekt
     */
    public IHra getHra() {
        return hra;
    }
    
    /**
     * vatváří celý panel pro výsledné zobrazení GUI.
     * Nastavuje Globální proměnnou pro FlowPane 
     */
    public void createPanel() {
       panel = new FlowPane();
       
       veci = FXCollections.observableArrayList();
       prostorVeci = FXCollections.observableArrayList();
       vychody = FXCollections.observableArrayList();
       
       ListView<Prostor> seznamVychodu = new ListView<>(vychody);
       ListView<Vec> listVeci = new ListView<>(veci);
       ListView<Vec> listVeciProstor = new ListView<>(prostorVeci);
        
       for (Prostor prostor : hra.getHerniPlan().getAktualniProstor().getVychody()) {
           vychody.add(prostor);
       }
       
       /*
       aby se dalo na kliknutí přecházet mezi prostory
       */
       seznamVychodu.setCellFactory((ListView<Prostor> param) -> new ListCell<Prostor>() {
            @Override
            protected void updateItem(Prostor item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setText(null);
                } else {
                    setText(item.getNazev());
                }
                this.setOnMousePressed(event -> {
                    /*
                    try pro případ, že uživatel klikne na pole ListView kde nic není 
                    -> převedeno na příkaz, že uživatel nezadal prostor, kterým chce projít
                    */
                    try{
                        String odpoved = hra.zpracujPrikaz("jdi "+item.getNazev());
                        pLevy.setText("jdi "+item.getNazev(), odpoved);
                    }
                    catch(NullPointerException e){
                        String odpoved = hra.zpracujPrikaz("jdi ");
                        pLevy.setText("jdi ", odpoved);
                    }
                });
            }
       });
       
       /*
       aby se dalo klikat na veci v batohu
       */
        listVeci.setCellFactory(param -> new ListCell<Vec>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Vec item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getImage());
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                this.setOnMousePressed(event -> {
                    String prikaz;
                    if("kamen".equals(hra.getHerniPlan().getAktualniProstor().getNazev()) && ("krevety".equals(item.getNazev()) || "bryle".equals(item.getNazev())) ){
                        prikaz = "pouzij";
                    }
                    else{
                        prikaz = "zahod";
                    }
                    try{
                        String odpoved = hra.zpracujPrikaz(prikaz + " " + item.getNazev());
                        pLevy.setText(prikaz + " " + item.getNazev(), odpoved);
                    }
                    catch(NullPointerException e){
                        String odpoved = hra.zpracujPrikaz(prikaz + " ");
                        pLevy.setText(prikaz + " ", odpoved);
                    }
                });
            }

        });
       
       /*
        aby se dalo klikat na veci v prostoru
        */
        listVeciProstor.setCellFactory(param -> new ListCell<Vec>() {
            private ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Vec item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getNazev() == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getImage());
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
                this.setOnMousePressed(event -> {
                    try{
                        String odpoved = hra.zpracujPrikaz("seber "+item.getNazev());
                        pLevy.setText("seber " + item.getNazev(), odpoved);
                    }
                    catch(NullPointerException e){
                        String odpoved = hra.zpracujPrikaz("seber ");
                        pLevy.setText("seber ", odpoved);
                    }
                });
            }

        });
        
        
       
       /*
        grafické prvky zarovnání
        */
       seznamVychodu.setPrefWidth(SIRKA_PANELU);
       seznamVychodu.setPrefHeight(100);
       
       Label seznamVychoduLabel = new Label("Seznam východů: ");
       Label seznamObrazkuLabel = new Label("Obsah batohu: ");
       Label seznamObrazkuProstorLabel = new Label("Věci v prostoru: ");
       
       Font fLabel = Font.font("Arial",FontWeight.BOLD,18);
       seznamVychoduLabel.setFont(fLabel);
       seznamObrazkuLabel.setFont(fLabel);
       seznamObrazkuProstorLabel.setFont(fLabel);
       
       seznamVychoduLabel.setPrefWidth(SIRKA_PANELU);
       seznamVychoduLabel.setPrefHeight(20);
       
       seznamObrazkuLabel.setPrefWidth(SIRKA_PANELU_2);
       seznamObrazkuLabel.setPrefHeight(20);
       
       seznamObrazkuProstorLabel.setPrefWidth(SIRKA_PANELU_2);
       seznamObrazkuProstorLabel.setPrefHeight(20);
       
       
       /*
       nahrání obrázků do paměti
       */
       seznamVeci.keySet().stream().forEach((nazev) -> {
           Vec vec = seznamVeci.get(nazev);
           Image image = new Image(Main.class.getResourceAsStream("/zdroje/" + nazev + ".png"), 70, 70, false, false);
           vec.setImage(image);
           seznamObrazkuMap.put(nazev, new AnchorPane(new ImageView(image)));
        });
       
       /*
       další grafické prvky zarovnání
       */
       listVeci.setPrefWidth(SIRKA_PANELU_2);
       listVeci.setPrefHeight(390 - MARGIN_PRVKU);
//       listVeci.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
       listVeci.setCursor(Cursor.HAND);
       
       
       listVeciProstor.setPrefWidth(SIRKA_PANELU_2);
       listVeciProstor.setPrefHeight(390 - MARGIN_PRVKU);
       

       panel.getChildren().addAll(seznamVychoduLabel, seznamVychodu,seznamObrazkuLabel, seznamObrazkuProstorLabel, listVeci, listVeciProstor);
       Insets insets = new Insets(5,5,5,10);
       panel.setMargin(seznamVychoduLabel, new Insets(5,5,0,10));
       panel.setMargin(seznamVychodu, new Insets(5,5,0,10));
       panel.setMargin(seznamObrazkuLabel, insets);
       panel.setMargin(seznamObrazkuProstorLabel, insets);
       panel.setMargin(listVeci, insets);
       panel.setMargin(listVeciProstor, insets);
       panel.setAlignment(Pos.TOP_LEFT);
       
       
       aktualizuj();
       
    }

    /**
     * metoda pro aktualizaci GUI dle aktuálního stavu hry
     * - věci v batohu, prostoru, seznam východů
     */
    @Override
    public void aktualizuj() {
        
        
        
       vychody.clear();
       veci.clear();
       prostorVeci.clear();
        
       Collection<Prostor> vychody_ = hra.getHerniPlan().getAktualniProstor().getVychody();
       Map<String, Vec> obsahBatohu = hra.getHerniPlan().getBatoh().vratObsah();
       Map<String, Vec> obsahProstoru = hra.getHerniPlan().getAktualniProstor().getVeci();
       
       
       
        for (String nazev : obsahBatohu.keySet()) {
           veci.add(obsahBatohu.get(nazev));
        }
       
       for (String vec : obsahProstoru.keySet()) {
           prostorVeci.add(obsahProstoru.get(vec));
        }
       
       vychody_.stream().forEach((prostor) -> {
           vychody.add(prostor);
       });
       
    }
    
}
