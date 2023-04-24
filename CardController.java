/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import entities.Pharmacie;
import entities.Facture;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.FactureService;

/**
 * FXML Controller class
 *
 * @author rouai
 */
public class CardController implements Initializable {
    @FXML
    private Circle circleImg;
    @FXML
    private ImageView imageLb;
    @FXML
    private Button confirmBt;
    @FXML
    private Button annuleBt;
    @FXML
    private Label numLb;
    
        @FXML
    private Label ordoLb;
    @FXML
    private Label dateLb;
    @FXML
    private Label montantLb;
    @FXML
    private Label etatLb;
    Facture facture;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        circleImg.setStroke(Color.SEAGREEN);
        Image im = new Image("./utils/img/patient.jpg",false);
        circleImg.setFill(new ImagePattern(im));
        
       
    }  
    
    public void setData(Facture f){
        this.facture = f;
        System.out.println(facture.getIDf());
        int maxLength = 20; // set the maximum length of the text
        circleImg.setStroke(Color.SEAGREEN);
        Image im = new Image("./utils/img/patient.jpg",false);
        etatLb.setText(f.getEtat());
          if(f.getEtat().equals("Non Payé")){
            etatLb.setTextFill(Color.ORANGE); // set the text color to red    
          }else if(f.getEtat().equals("Payé")){
            etatLb.setTextFill(Color.GREEN); // set the text color to red    
          }else{
            etatLb.setTextFill(Color.RED); // set the text color to red    

    }
        circleImg.setFill(new ImagePattern(im));
        dateLb.setText(f.getDate().toString());
       

       // symptomesLb.setText(f.getSymptomes().substring(0, Math.min(f.getSymptomes().length(), maxLength)));
        //if (f.getSymptomes().length() > maxLength) {
        //Tooltip tooltip = new Tooltip(f.getSymptomes()); // create a tooltip to show the full text
        //symptomesLb.setTooltip(tooltip); // set the tooltip on the label
        //System.out.println(facture.getEtat());}
    }
    
    
    @FXML
    void annulerRdv(ActionEvent event) {

    }

    @FXML
    void confirmerfacture(ActionEvent event) {
        FactureService fService = new FactureService(); 
        try{
        fService.confirmerfacture(facture);
        facture.setEtat("Payé");
        setData(facture);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Facture confirmé avec succès");
        alert.showAndWait();
        }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
        
        
    }
    
    
    @FXML
    void archiverf(ActionEvent event) {
                FactureService fService = new FactureService(); 
        try{
        fService.archiverf(facture);
        facture.setEtat("archivé");
        setData(facture);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation AVEC SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Facture vous confirmé avec succès");
        alert.showAndWait();
        }catch(RuntimeException ex){
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR,"erreur",ButtonType.CLOSE);
            alert.showAndWait();
        }
        
    }
    
    @FXML
    void EditFacture(ActionEvent event) {
        
       
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/EditerFacture.fxml"));
            Parent parent = loader.load();

            EditerFacture controller = (EditerFacture) loader.getController();
            controller.inflateUI(this.facture);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Editer Facture");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                try {
                    FactureService factureService = new FactureService();
                    Facture factureModifie = factureService.findFacById(this.facture.getIDf());
                    System.out.println(factureModifie);
                    setData(factureModifie);

                    
                }catch (SQLException ex) {
                    System.out.println(ex);
                   // Logger.getLogger("gg").log(Level.SEVERE, null, ex);
                }
            }
            );
            
            // Hide this current window (if this is what you want)
           // ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 
}
